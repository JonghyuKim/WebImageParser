package com.spacemonster.webdataviewer.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by GE62 on 2017-12-21.
 */

public abstract class AbstractDataViewHolder<T> extends RecyclerView.ViewHolder {

    public AbstractDataViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * recycle 되는 뷰에 대한 처리를 함
     * @param data
     */
    public abstract void initView(T data);
}
