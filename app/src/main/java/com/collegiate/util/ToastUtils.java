package com.collegiate.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by gauravarora on 08/06/15.
 */
public class ToastUtils {

    private ToastUtils() {
    }

    public static void showError(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showError(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
