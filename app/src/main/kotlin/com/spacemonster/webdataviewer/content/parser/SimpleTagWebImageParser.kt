package com.spacemonster.webdataviewer.content.parser

import com.spacemonster.webdataviewer.content.data.WebImage
import io.reactivex.Observable

/**
 * Created by GE62 on 2017-12-23.
 */

class SimpleTagWebImageParser(private val targetTag: String) : IDataParser<String, WebImage> {

    private var startLine = 0
    private val targetTagLength: Int by lazy{ targetTag.length }

    private var isCommentContinue = false

    override fun parse(dataList: List<String>): Observable<WebImage> {
        return Observable.create{emitter ->
            for(line in dataList){
                parse(line)?.let {
                    emitter.onNext(it)
                }
            }
            emitter.onComplete()
        }

    }

    private fun parse(line: String): WebImage? {

        if (checkComment(line)) {
            return null
        }

        startLine = line.indexOf(targetTag)

        if (startLine != -1) {
            startLine += targetTagLength
            return WebImage(line.substring(startLine, line.indexOf("\"", startLine)))
        }

        return null
    }

    private fun checkComment(line: String): Boolean {
        if (!isCommentContinue && line.indexOf("<!--") != -1) {
            isCommentContinue = line.lastIndexOf("--!>") == -1
            return true
        }

        if (isCommentContinue && line.lastIndexOf("-->") != -1) {
            isCommentContinue = false
            return true
        }

        return false
    }

    override fun release() {}
}
