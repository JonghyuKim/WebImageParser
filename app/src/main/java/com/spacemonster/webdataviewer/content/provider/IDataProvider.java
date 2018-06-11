package com.spacemonster.webdataviewer.content.provider;

import com.spacemonster.webdataviewer.content.parser.IDataParser;
import com.spacemonster.webdataviewer.content.requester.IDataRequester;

import java.util.List;

/**
 * Created by GE62 on 2017-12-21.
 */

public interface IDataProvider<T> {
    /**
     * 데이터 제공을 위한 request와 parser 를 세팅하고 초기화함
     * @param requester
     * @param parser
     */
    public void init(IDataRequester requester, IDataParser parser);

    /**
     * *
     * 데이터 생성을 요청한다
     * @param path
     * @param listner
     */
    public void createData(String path, ParserProcessListner<T> listner);

    public void setRequester(IDataRequester requester);

    public IDataRequester getRequester();

    public void setParser(IDataParser parser);

    public IDataParser getParser();

    public List<T> getDatas();

    public int getDataLength();

    public void release();

    /**
     * 파서의 행동에 대한 피드백을 받는 리스너
     * @param <T>
     */
    public interface ParserProcessListner<T>{
        /**
         * 실제 데이터가 파싱되어 개체로 나올 때 마다 호출된다
         * @param data
         */
        public void addData(T data);

        /**
         * 모든 작업이 완료 될 경우 호출된다
         * @param list
         */
        public void onFinish(List<T> list);

        public void onError();
    }
}
