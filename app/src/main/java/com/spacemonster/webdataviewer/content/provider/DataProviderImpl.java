package com.spacemonster.webdataviewer.content.provider;

import android.content.Context;
import android.os.AsyncTask;

import com.spacemonster.webdataviewer.content.parser.IDataParser;
import com.spacemonster.webdataviewer.content.parser.JsoupWebImageParser;
import com.spacemonster.webdataviewer.content.requester.IDataRequester;
import com.spacemonster.webdataviewer.content.requester.WebDataRequester;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GE62 on 2017-12-21.
 */

public class DataProviderImpl<T, R> implements IDataProvider<R> {
    private Context context;

    private AsyncTask dataLoadTask = null;

    private List<R> parseDatas = null;

    private IDataRequester dataRequester = null;
    private IDataParser dataParser = null;
    private ParserProcessListner userProcessListner = null;

    private boolean isReleased = false;

    protected DataProviderImpl(Context context){
        this.context = context;
        parseDatas = new ArrayList<>();
        isReleased = false;
    }

    @Override
    public void init(IDataRequester requester, IDataParser parser) {
        dataRequester = requester;
        dataParser = parser;
        isReleased = false;
    }

    @Override
    public IDataRequester getRequester(){
        if(dataRequester == null){
            return new WebDataRequester(context);
        }
        return dataRequester;
    }

    @Override
    public IDataParser getParser() {
        if(dataParser == null){
            return new JsoupWebImageParser();
        }
        return dataParser;
    }

    @Override
    public void createData(final String dataPath, ParserProcessListner<R> listner) {
        userProcessListner = listner;
        dataLoadTask = new AsyncTask<Void, R, List<R>>() {

            @Override
            protected List<R> doInBackground(Void... voids) {

                dataRequester = getRequester();
                dataParser = getParser();

                try {

                    List<T> dataList = (List) dataRequester.dataRequest(dataPath);

                    R parseData;
                    for(T data : dataList){
                        if((parseData = (R) dataParser.parse(data)) != null){
                            publishProgress(parseData);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return parseDatas;
                }
                finally {
                    dataParser.release();
                    dataParser = null;

                    dataRequester.release();
                    dataRequester = null;
                }

                return parseDatas;
            }

            @Override
            protected void onPostExecute(List<R> rs) {
                super.onPostExecute(rs);
                if(isReleased == false && userProcessListner != null){
                    if(rs == null){
                        userProcessListner.onError();
                    }
                    else{
                        userProcessListner.onFinish(parseDatas);
                    }
                }
            }

            @Override
            protected void onProgressUpdate(R[] values) {
                super.onProgressUpdate(values);
                parseDatas.add(values[0]);
                userProcessListner.addData(values[0]);
            }
        };
        dataLoadTask.execute();
    }

    @Override
    public void setRequester(IDataRequester requester) {
        dataRequester = requester;
    }

    @Override
    public void setParser(IDataParser parser) {
        dataParser = parser;
    }

    @Override
    public List<R> getDatas() {
        return parseDatas;
    }

    @Override
    public int getDataLength() {
        return parseDatas.size();
    }

    @Override
    public void release() {

        isReleased = true;

        parseDatas.clear();

        if(dataRequester != null){
            dataRequester.release();
        }

        if(dataParser != null){
            dataParser.release();
        }
        if(dataLoadTask != null){
            dataLoadTask.cancel(false);
        }
    }
}
