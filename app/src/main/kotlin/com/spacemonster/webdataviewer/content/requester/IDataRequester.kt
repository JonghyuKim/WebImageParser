package com.spacemonster.webdataviewer.content.requester

import io.reactivex.Observable
import java.util.*


/**
 * Created by GE62 on 2017-12-26.
 */

interface IDataRequester<T> {

    /**
     * 실제 path로 부터 데이터를 가져온다
     * @param path
     * @throws Exception
     */
    @Throws(Exception::class)
    fun dataRequest(path: String): Observable<T>

    fun release()
}
