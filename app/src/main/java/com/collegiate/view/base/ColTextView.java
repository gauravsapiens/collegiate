package com.collegiate.view.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.collegiate.R;

/**
 * Created by gauravarora on 09/07/15.
 */
public class ColTextView extends TextView {

    public static final String DEFAULT_FONT = "regular.ttf";

    public ColTextView(Context context) {
        super(context);
    }

    public ColTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ColTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ColTextView);
        String fontName = a.getString(R.styleable.ColTextView_font);
        if (fontName != null) {
            Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
            setTypeface(myTypeface);
        } else {
            Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + DEFAULT_FONT);
            setTypeface(myTypeface);
        }
        a.recycle();
    }

}
