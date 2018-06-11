package com.spacemonster.webdataviewer.content.requester;

import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GE62 on 2017-12-26.
 */

public class JsoupWebImageRequester implements IDataRequester {
    boolean isReleased = false;

    private List<String> dataList = null;

    private Context context = null;

    public JsoupWebImageRequester(Context context){
        this.context = context;
        dataList = new ArrayList<>();
    }
    @Override
    public List<String> dataRequest(String path) throws Exception {

        dataList.clear();

        Document document = Jsoup.connect(path).timeout(5000).get();
        Elements imgs = document.select("img");

        for(Element img : imgs) {
            if(isReleased == false){
                dataList.add(img.attr("src"));
            }
        }

        return dataList;
    }

    @Override
    public void release() {
        isReleased = true;
    }
}
