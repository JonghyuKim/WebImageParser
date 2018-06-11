package com.spacemonster.webdataviewer.content.requester;

import android.content.Context;

import com.spacemonster.webdataviewer.content.parser.IDataParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GE62 on 2017-12-26.
 */

public class FileDataRequester implements IDataRequester {

    private boolean isReleased = false;

    private Context context = null;

    private List<String> dataList = null;

    public FileDataRequester(Context context){
        this.context = context;
        dataList = new ArrayList<>();
    }

    @Override
    public List<String> dataRequest(String path) throws FileNotFoundException, IOException {
        BufferedReader reader = null;

        dataList.clear();

        try {
            InputStream fis = openFile(context, path);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while (isReleased == false && (line = reader.readLine()) != null) {
                dataList.add(line);
            }
        } finally {
            if(reader != null){
                reader.close();
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
