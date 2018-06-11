package com.spacemonster.webdataviewer.view.webimage

import android.view.View
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.spacemonster.webdataviewer.R
import com.spacemonster.webdataviewer.view.AbstractDataViewHolder
import com.spacemonster.webdataviewer.content.data.WebImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*

/**
 * Created by GE62 on 2017-12-21.
 */

class WebImageViewHolder(itemView: View) : AbstractDataViewHolder<WebImage>(itemView) {

    private var requestOptions: RequestOptions = RequestOptions()

    init {
        //Use
        if (USE_GLIDE) {
            requestOptions.placeholder(R.drawable.wait).centerInside().fitCenter()
        }
    }

    override fun initView(data: WebImage) {
        if (USE_GLIDE) {
            Glide.with(itemView.context).asDrawable().load(WebImage.MAIN_URL + data.imageUrl).apply(requestOptions!!).into(itemView.iv_url_img)
        } else {
            Picasso.with(itemView.context).load(WebImage.MAIN_URL + data.imageUrl).fit().centerInside().placeholder(R.drawable.wait).into(itemView.iv_url_img)
        }
    }

    companion object {
        private val USE_GLIDE = true
    }
}
