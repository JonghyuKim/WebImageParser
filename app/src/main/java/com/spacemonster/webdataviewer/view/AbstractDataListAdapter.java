package com.spacemonster.webdataviewer.view;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by GE62 on 2017-12-21.
 */

public abstract class AbstractDataListAdapter<T> extends RecyclerView.Adapter<AbstractDataViewHolder>{
    protected List<T> dataList = null;

    public abstract void setDataList(List<T> list);

    @Override
    public void onBindViewHolder(AbstractDataViewHolder holder, int position) {
        holder.initView(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        if(dataList == null){
            return 0;
        }
        return dataList.size();
    }
}
