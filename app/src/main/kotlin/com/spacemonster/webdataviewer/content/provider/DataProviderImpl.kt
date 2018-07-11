package com.spacemonster.webdataviewer.content.provider

import com.spacemonster.webdataviewer.content.parser.IDataParser
import com.spacemonster.webdataviewer.content.requester.IDataRequester
import io.reactivex.Observable

/**
 * Created by GE62 on 2017-12-21.
 */

class DataProviderImpl<T, R>(override val requester: IDataRequester<T>
                             , override val parser: IDataParser<T, R>
                             , override val dataPath: String ) : IDataProvider<T, R> {


    override fun createData(): Observable<R> = parser.parse(requester.dataRequest(dataPath))

    override fun release() {
        requester.release()
        parser.release()
    }
}
