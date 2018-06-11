package com.spacemonster.webdataviewer.content.requester

import android.content.Context

import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList

/**
 * Created by GE62 on 2017-12-26.
 */

class FileDataRequester(val context: Context) : IDataRequester<String> {

    private var isReleased = false

    private val dataList = ArrayList<String>()

    @Throws(FileNotFoundException::class, IOException::class)
    override fun dataRequest(path: String): List<String> {
        var reader: BufferedReader? = null

        dataList.clear()

        try {
            val fis = openFile(context, path)
            reader = BufferedReader(InputStreamReader(fis))

            while (!isReleased) {
                reader.readLine()?.run{
                    dataList.add(this)
                } ?: break
            }
        } finally {
            reader?.close()
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
