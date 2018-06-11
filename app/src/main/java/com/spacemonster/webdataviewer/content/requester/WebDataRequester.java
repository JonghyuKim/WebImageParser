package com.spacemonster.webdataviewer.content.requester;

import android.content.Context;

import com.spacemonster.webdataviewer.content.parser.IDataParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GE62 on 2017-12-26.
 */

public class WebDataRequester implements IDataRequester {

    private boolean isReleased = false;

    private Context context = null;
    List<String> dataList = new ArrayList<>();

    public WebDataRequester(Context context){
        this.context = context;
    }

    @Override
    public List<String> dataRequest(String path) throws ProtocolException, MalformedURLException, IOException {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd = null;
        String line;

        dataList.clear();

        isReleased = false;

        try {
            url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while (isReleased == false && (line = rd.readLine()) != null) {
                dataList.add(line);
            }
        }  finally {
            if (rd != null) {
                rd.close();
            }
        }
        return dataList;
    }

    @Override
    public void release() {
        dataList.clear();
        dataList = null;
        isReleased = true;
    }

    private InputStream openFile(Context context, String filename) throws IOException {
        return context.getResources().getAssets().open(filename);
    }
}
