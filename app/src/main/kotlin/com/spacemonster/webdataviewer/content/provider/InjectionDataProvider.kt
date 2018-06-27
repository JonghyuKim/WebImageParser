package com.spacemonster.webdataviewer.content.provider

import android.content.Context

import com.spacemonster.webdataviewer.content.data.WebImage
import com.spacemonster.webdataviewer.content.parser.JsoupWebImageParser
import com.spacemonster.webdataviewer.content.parser.SimpleTagWebImageParser
import com.spacemonster.webdataviewer.content.requester.FileDataRequester
import com.spacemonster.webdataviewer.content.requester.WebDataRequester

object InjectionDataProvider {

    fun fileToSimpleTag(context: Context, tag: String, dataPath: String): IDataProvider<String, WebImage> {
        return DataProviderImpl(FileDataRequester(context), SimpleTagWebImageParser(tag), dataPath)
    }

    fun webToSimpleTag(context: Context, tag: String, dataPath: String): IDataProvider<String, WebImage> {
        return DataProviderImpl(WebDataRequester(context), SimpleTagWebImageParser(tag), dataPath)
    }

    fun webToJsoup(context: Context, dataPath: String): IDataProvider<String, WebImage> {
        return DataProviderImpl(WebDataRequester(context), JsoupWebImageParser(), dataPath)
    }
}
