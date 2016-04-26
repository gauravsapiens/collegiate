package com.collegiate.core;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface TableItem {

    View getView(int position, View convertView, ViewGroup parent, Context context);

    Object getUserInfo();
}
