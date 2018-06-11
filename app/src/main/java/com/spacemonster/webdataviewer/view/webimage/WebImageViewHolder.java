package com.spacemonster.webdataviewer.view.webimage;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.spacemonster.webdataviewer.R;
import com.spacemonster.webdataviewer.view.AbstractDataViewHolder;
import com.spacemonster.webdataviewer.content.data.WebImage;
import com.squareup.picasso.Picasso;

/**
 * Created by GE62 on 2017-12-21.
 */

public class WebImageViewHolder extends AbstractDataViewHolder<WebImage> {
    private static final boolean USE_GLIDE = true;

    private ImageView urlView;
    private RequestOptions requestOptions;

    public WebImageViewHolder(View itemView) {
        super(itemView);
        urlView = itemView.findViewById(R.id.iv_url_img);
        //Use
        if(USE_GLIDE == true){
            requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.wait).centerInside().fitCenter();
        }
    }

    @Override
    public void initView(WebImage data) {
        if(USE_GLIDE == true){
            Glide.with(itemView.getContext()).asDrawable().load(WebImage.MAIN_URL + data.getImageUrl()).apply(requestOptions).into(urlView);
        }else{
            Picasso.with(itemView.getContext()).load(WebImage.MAIN_URL + data.getImageUrl()).fit().centerInside().placeholder(R.drawable.wait).into(urlView);
        }
    }
}
