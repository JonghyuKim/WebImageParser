package com.spacemonster.webdataviewer.view.webimage

import android.view.LayoutInflater
import android.view.ViewGroup

import com.spacemonster.webdataviewer.R
import com.spacemonster.webdataviewer.view.AbstractDataListAdapter
import com.spacemonster.webdataviewer.view.AbstractDataViewHolder
import com.spacemonster.webdataviewer.content.data.WebImage

/**
 * Created by GE62 on 2017-12-21.
 */

class WebImageListAdapter : AbstractDataListAdapter<WebImage>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractDataViewHolder<WebImage> {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image, parent, false)

        return WebImageViewHolder(itemView)
    }
}
