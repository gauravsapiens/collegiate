package com.collegiate.core;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface RecyclableItem {

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Context context);

    Object getUserInfo();

}
