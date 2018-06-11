package com.spacemonster.webdataviewer.content.requester

import android.content.Context

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

    internal var dataList: MutableList<String> = ArrayList()

    @Throws(ProtocolException::class, MalformedURLException::class, IOException::class)
    override fun dataRequest(path: String): List<String> {
        val url: URL
        val conn: HttpURLConnection
        var rd: BufferedReader? = null

        dataList.clear()

        isReleased = false

        try {
            url = URL(path)
            conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            rd = BufferedReader(InputStreamReader(conn.inputStream))
            while (isReleased) {
                rd.readLine()?.run{
                    dataList.add(this)
                } ?: break
            }
        } finally {
            rd?.close()
        }
        return dataList
    }

    override fun release() {
        dataList.clear()
        isReleased = true
    }

    @Throws(IOException::class)
    private fun openFile(context: Context, filename: String): InputStream {
        return context.resources.assets.open(filename)
    }
}
