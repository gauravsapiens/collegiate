package com.collegiate.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.collegiate.R;
import com.collegiate.view.base.ColImageView;

public class NavigationHeaderView extends RelativeLayout {

    private ColImageView imageView;
    private TextView nameTextView;
    private TextView emailTextView;

    public NavigationHeaderView(Context context) {
        super(context);
        init();
    }

    public NavigationHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.view_navigation_header, this, true);
        imageView = (ColImageView) rootView.findViewById(R.id.user_image);
        nameTextView = (TextView) rootView.findViewById(R.id.user_name);
        emailTextView = (TextView) rootView.findViewById(R.id.user_email);
    }

    public void setImageUrl(String imageUrl) {
    }

    public void setUserName(String userName) {
        nameTextView.setText(userName);
    }

    public void setUserEmail(String userEmail) {
        emailTextView.setText(userEmail);
    }


}
