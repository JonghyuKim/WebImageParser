package com.spacemonster.webdataviewer.content.provider

import android.content.Context
import android.os.AsyncTask
import android.util.Log

import com.spacemonster.webdataviewer.content.parser.IDataParser
import com.spacemonster.webdataviewer.content.requester.IDataRequester

import java.util.ArrayList

/**
 * Created by GE62 on 2017-12-21.
 */

class DataProviderImpl<T, R>(private val context: Context) : IDataProvider<T, R> {

    override var requester: IDataRequester<T>? = null
    override var parser: IDataParser<T, R>? = null

    private var dataLoadTask: AsyncTask<Void, R, List<R>>? = null

    private var userProcessListner: IDataProvider.ParserProcessListner<R>? = null

    private var isReleased = false

    override val parseDatas = ArrayList<R>()

    override fun createData(dataPath: String, listner: IDataProvider.ParserProcessListner<R>) {
        userProcessListner = listner
        dataLoadTask = object : AsyncTask<Void, R, List<R>>() {

            override fun doInBackground(vararg voids: Void): List<R>? {

                try {

                    val dataList = requester?.dataRequest(dataPath)
                    Log.d("hyuhyu", "read : ${dataList?.size}");
                    for (data in dataList!!) {
                        parser?.parse(data)?.run{
                            publishProgress(this)
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                } finally {
                    parser?.release()
                    parser?.release()
                }

                return this@DataProviderImpl.parseDatas
            }

            override fun onPostExecute(rs: List<R>?) {
                super.onPostExecute(rs)
                if (isReleased == false) {
                    if (rs == null) {
                        userProcessListner?.onError()
                    } else {
                        userProcessListner?.onFinish(rs)
                    }
                }
            }

            override fun onProgressUpdate(values: Array<R>) {
                super.onProgressUpdate(*values)
                this@DataProviderImpl.parseDatas.add(values[0])
                userProcessListner?.addData(values[0])
            }
        }
        dataLoadTask!!.execute()
    }

    override fun release() {

        isReleased = true

        parseDatas.clear()

        requester?.release()

        parser?.release()

        dataLoadTask?.cancel(false)
    }
}
