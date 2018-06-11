package com.spacemonster.webdataviewer.content.parser

import com.spacemonster.webdataviewer.content.data.WebImage

/**
 * Created by GE62 on 2017-12-26.
 */

class JsoupWebImageParser : IDataParser<String, WebImage> {

    override fun parse(data: String): WebImage? {
        return WebImage(data)
    }

    override fun release() {}
}
