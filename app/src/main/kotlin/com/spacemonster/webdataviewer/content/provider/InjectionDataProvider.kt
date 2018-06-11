package com.spacemonster.webdataviewer.content.provider

import android.content.Context

import com.spacemonster.webdataviewer.content.data.WebImage
import com.spacemonster.webdataviewer.content.parser.JsoupWebImageParser
import com.spacemonster.webdataviewer.content.parser.SimpleTagWebImageParser
import com.spacemonster.webdataviewer.content.requester.FileDataRequester
import com.spacemonster.webdataviewer.content.requester.WebDataRequester

object InjectionDataProvider {

    fun fileToSimpleTag(context: Context, tag: String): IDataProvider<String, WebImage> {
        return DataProviderImpl<String, WebImage>(context).apply {
            requester = FileDataRequester(context)
            parser = SimpleTagWebImageParser(tag)
        }
    }

    fun webToSimpleTag(context: Context, tag: String): IDataProvider<String, WebImage> {
        return DataProviderImpl<String, WebImage>(context).apply {
            requester = WebDataRequester(context)
            parser = SimpleTagWebImageParser(tag)
        }
    }

    fun webToJsoup(context: Context): IDataProvider<String, WebImage> {
        return DataProviderImpl<String, WebImage>(context).apply {
            requester = WebDataRequester(context)
            parser = JsoupWebImageParser()
        }
    }
}
