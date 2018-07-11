package com.spacemonster.webdataviewer.content.requester

import android.content.Context
import io.reactivex.Observable

import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

/**
 * Created by GE62 on 2017-12-26.
 */

class FileDataRequester(val context: Context) : IDataRequester<String> {

    private var isReleased = false

    @Throws(FileNotFoundException::class, IOException::class)
    override fun dataRequest(path: String): Observable<String> {

        return Observable.create{
            var rd : BufferedReader? = null
            try {
                val fis = openFile(context, path)
                val rd = BufferedReader(InputStreamReader(fis))
                while (!isReleased) {
                    rd.readLine()?.run{
                        it.onNext(this)
                    } ?: break
                    it.onComplete()
                }
            } finally {
                rd?.close()
            }
        }
    }

    override fun release() {
        isReleased = true
    }

    @Throws(IOException::class)
    private fun openFile(context: Context, filename: String): InputStream {
        return context.resources.assets.open(filename)
    }
}
