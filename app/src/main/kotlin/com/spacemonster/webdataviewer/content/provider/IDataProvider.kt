package com.spacemonster.webdataviewer.content.provider

import com.spacemonster.webdataviewer.content.parser.IDataParser
import com.spacemonster.webdataviewer.content.requester.IDataRequester
import io.reactivex.Observable
import java.util.ArrayList

/**
 * Created by GE62 on 2017-12-21.
 */

interface IDataProvider<T,R> {

    val requester: IDataRequester<T>

    val parser: IDataParser<T, R>

    val dataPath: String

    /**
     * 데이터 생성을 요청한다
     */
    fun createData() : Observable<R>

    fun release()
}
