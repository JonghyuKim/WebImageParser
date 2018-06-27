package com.spacemonster.webdataviewer.content.parser

import com.spacemonster.webdataviewer.content.data.WebImage
import io.reactivex.Observable

/**
 * Created by GE62 on 2017-12-26.
 */

class JsoupWebImageParser : IDataParser<String, WebImage> {
    override fun parse(dataList: List<String>): Observable<WebImage> {
        return Observable.fromIterable(dataList).map { WebImage(it) }
    }

    override fun release() {}
}
