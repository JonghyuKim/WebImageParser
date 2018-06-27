package com.spacemonster.webdataviewer.content.parser

import io.reactivex.Observable

/**
 * Created by GE62 on 2017-12-23.
 */

interface IDataParser<T, R> {
    /**
     * 파싱할 데이터를 입력받는다.
     * @param dataList
     */
    fun parse(dataList: List<T>): Observable<R>

    fun release()
}
