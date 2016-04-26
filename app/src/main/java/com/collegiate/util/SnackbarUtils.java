package com.collegiate.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by gauravarora on 14/06/15.
 */
public class SnackbarUtils {

    public static void show(View rootView, String text) {
        Snackbar.make(rootView, text, Snackbar.LENGTH_LONG).show();
    }

}
