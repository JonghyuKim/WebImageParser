package com.spacemonster.webdataviewer.content.parser;

import java.util.List;

/**
 * Created by GE62 on 2017-12-23.
 */

public interface IDataParser<T,R> {
    /**
     * 파싱할 데이터를 입력받는다.
     * @param dataList
     */
    public R parse(T dataList);

    public void release();
}
