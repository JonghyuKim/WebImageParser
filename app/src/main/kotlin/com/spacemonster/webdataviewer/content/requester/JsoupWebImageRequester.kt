package com.spacemonster.webdataviewer.content.requester

import android.content.Context

import org.jsoup.Jsoup

import java.util.ArrayList

/**
 * Created by GE62 on 2017-12-26.
 */

class JsoupWebImageRequester(val context: Context) : IDataRequester<String> {
    internal var isReleased = false

    private var dataList: MutableList<String> = ArrayList()

    @Throws(Exception::class)
    override fun dataRequest(path: String): List<String> {

        dataList.clear()

        val document = Jsoup.connect(path).timeout(5000).get()
        val imgs = document.select("img")

        for (img in imgs) {
            if (!isReleased) {
                dataList.add(img.attr("src"))
            }
        }

        return dataList
    }

    override fun release() {
        isReleased = true
    }
}
