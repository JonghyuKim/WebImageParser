package com.spacemonster.webdataviewer.view

import android.support.v7.widget.RecyclerView

/**
 * Created by GE62 on 2017-12-21.
 */

abstract class AbstractDataListAdapter<T> : RecyclerView.Adapter<AbstractDataViewHolder<T>>() {
    var dataList: List<T>? = null

    override fun onBindViewHolder(holder: AbstractDataViewHolder<T>, position: Int) {
        holder.initView(dataList!![position])
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }
}
