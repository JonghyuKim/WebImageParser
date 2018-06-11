package com.spacemonster.webdataviewer.view

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by GE62 on 2017-12-21.
 */

abstract class AbstractDataViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /**
     * recycle 되는 뷰에 대한 처리를 함
     * @param data
     */
    abstract fun initView(data: T)
}
