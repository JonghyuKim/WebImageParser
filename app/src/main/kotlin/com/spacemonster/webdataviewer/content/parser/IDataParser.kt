package com.spacemonster.webdataviewer.content.parser

/**
 * Created by GE62 on 2017-12-23.
 */

interface IDataParser<T, R> {
    /**
     * 파싱할 데이터를 입력받는다.
     * @param dataList
     */
    fun parse(dataList: T): R?

    fun release()
}
