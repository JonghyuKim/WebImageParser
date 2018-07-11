package com.spacemonster.webdataviewer.content.requester

import android.content.Context
import io.reactivex.Observable

import org.jsoup.Jsoup

/**
 * Created by GE62 on 2017-12-26.
 */

class JsoupWebImageRequester(val context: Context) : IDataRequester<String> {
    internal var isReleased = false

    @Throws(Exception::class)
    override fun dataRequest(path: String): Observable<String> {
        return Observable.just(path)
                .map { Jsoup.connect(path).timeout(5000).get() }
                .flatMapIterable {
                    it.select("img")
                }
                .map { it.attr("src") }
    }

    override fun release() {
        isReleased = true
    }
}
