package com.spacemonster.webdataviewer.content.provider

import com.spacemonster.webdataviewer.content.parser.IDataParser
import com.spacemonster.webdataviewer.content.requester.IDataRequester
import java.util.ArrayList

/**
 * Created by GE62 on 2017-12-21.
 */

interface IDataProvider<T,R> {

    var requester: IDataRequester<T>?

    var parser: IDataParser<T, R>?

    val parseDatas: ArrayList<R>
    /**
     * *
     * 데이터 생성을 요청한다
     * @param path
     * @param listner
     */
    fun createData(path: String, listner: ParserProcessListner<R>)

    fun release()

    /**
     * 파서의 행동에 대한 피드백을 받는 리스너
     * @param <T>
    </T> */
    interface ParserProcessListner<R> {
        /**
         * 실제 데이터가 파싱되어 개체로 나올 때 마다 호출된다
         * @param data
         */
        fun addData(data: R)

        /**
         * 모든 작업이 완료 될 경우 호출된다
         * @param list
         */
        fun onFinish(list: List<R>)

        fun onError()
    }
}
