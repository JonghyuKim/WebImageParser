package com.spacemonster.webdataviewer.content.requester;

import android.content.Context;

import com.spacemonster.webdataviewer.content.parser.IDataParser;

import java.util.List;


/**
 * Created by GE62 on 2017-12-26.
 */

public interface IDataRequester<T> {

    /**
     * 실제 path로 부터 데이터를 가져온다
     * @param path
     * @throws Exception
     */
    public List<T> dataRequest(String path) throws Exception;

    public void release();
}
