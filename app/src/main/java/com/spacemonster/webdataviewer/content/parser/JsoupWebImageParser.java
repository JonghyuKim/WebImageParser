package com.spacemonster.webdataviewer.content.parser;

import com.spacemonster.webdataviewer.content.data.WebImage;

/**
 * Created by GE62 on 2017-12-26.
 */

public class JsoupWebImageParser implements IDataParser<String, WebImage> {

    @Override
    public WebImage parse(String data) {
        return new WebImage(data);
    }

    @Override
    public void release() {
    }
}
