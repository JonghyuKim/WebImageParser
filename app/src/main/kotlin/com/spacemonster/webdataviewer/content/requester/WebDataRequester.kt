package com.spacemonster.webdataviewer.content.requester

import android.content.Context
import io.reactivex.Observable

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.ProtocolException
import java.net.URL
import java.util.ArrayList

/**
 * Created by GE62 on 2017-12-26.
 */

class WebDataRequester(context: Context) : IDataRequester<String> {

    private var isReleased = false

    @Throws(ProtocolException::class, MalformedURLException::class, IOException::class)
    override fun dataRequest(path: String): Observable<String> {
        return Observable.create{
            var rd : BufferedReader? = null
            try {
                val conn = URL(path).openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                rd = BufferedReader(InputStreamReader(conn.inputStream))
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
