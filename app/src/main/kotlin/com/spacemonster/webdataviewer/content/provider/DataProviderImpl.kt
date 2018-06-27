package com.spacemonster.webdataviewer.content.provider

import android.os.AsyncTask
import android.util.Log

import com.spacemonster.webdataviewer.content.parser.IDataParser
import com.spacemonster.webdataviewer.content.requester.IDataRequester
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

import java.util.ArrayList

/**
 * Created by GE62 on 2017-12-21.
 */

class DataProviderImpl<T, R>(override val requester: IDataRequester<T>
                             , override val parser: IDataParser<T, R>
                             , override val dataPath: String ) : IDataProvider<T, R> {


    override fun createData() = Observable.fromCallable { requester.dataRequest(dataPath) }
            .subscribeOn(Schedulers.io())
            .flatMap{
                parser.parse(it)
            }

    override fun release() {
        requester?.release()
        parser?.release()
    }
}
