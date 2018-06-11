package com.spacemonster.webdataviewer.content.provider;

import android.content.Context;

import com.spacemonster.webdataviewer.content.data.WebImage;
import com.spacemonster.webdataviewer.content.parser.JsoupWebImageParser;
import com.spacemonster.webdataviewer.content.parser.SimpleTagWebImageParser;
import com.spacemonster.webdataviewer.content.requester.FileDataRequester;
import com.spacemonster.webdataviewer.content.requester.WebDataRequester;

public class InjectionDataProvider {

    public static IDataProvider<WebImage> fileToSimpleTag(Context context, String tag){
        DataProviderImpl<String, WebImage> webImageDataProvider = new DataProviderImpl<>(context);
        webImageDataProvider.setRequester(new FileDataRequester(context));
        webImageDataProvider.setParser(new SimpleTagWebImageParser(tag));

        return webImageDataProvider;
    }

    public static IDataProvider<WebImage> webToSimpleTag(Context context, String tag){
        DataProviderImpl<String, WebImage> webImageDataProvider = new DataProviderImpl<>(context);
        webImageDataProvider.setRequester(new WebDataRequester(context));
        webImageDataProvider.setParser(new SimpleTagWebImageParser(tag));

        return webImageDataProvider;
    }

    public static IDataProvider<WebImage> webToJsoup(Context context){
        DataProviderImpl<String, WebImage> webImageDataProvider = new DataProviderImpl<>(context);
        webImageDataProvider.setRequester(new WebDataRequester(context));
        webImageDataProvider.setParser(new JsoupWebImageParser());

        return webImageDataProvider;
    }
}
