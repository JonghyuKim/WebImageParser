package com.spacemonster.webdataviewer.view.webimage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spacemonster.webdataviewer.R;
import com.spacemonster.webdataviewer.view.AbstractDataListAdapter;
import com.spacemonster.webdataviewer.view.AbstractDataViewHolder;
import com.spacemonster.webdataviewer.content.data.WebImage;

import java.util.List;

/**
 * Created by GE62 on 2017-12-21.
 */

public class WebImageListAdapter extends AbstractDataListAdapter<WebImage> {

    @Override
    public void setDataList(List<WebImage> list) {
        dataList = list;
    }

    @Override
    public AbstractDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);

        return new WebImageViewHolder(itemView);
    }
}
