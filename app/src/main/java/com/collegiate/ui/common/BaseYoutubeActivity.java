package com.collegiate.ui.common;

import android.content.Intent;
import android.widget.Toast;

import com.collegiate.R;
import com.collegiate.util.ResourceUtils;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by gauravarora on 27/08/15.
 */
public abstract class BaseYoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private YouTubePlayerView mYoutubePlayerView;

    protected void configureYoutubePlayer(YouTubePlayerView youTubePlayerView) {
        mYoutubePlayerView = youTubePlayerView;
        mYoutubePlayerView.initialize(getDeveloperKey(), this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.loadVideo(getVideoUrl());
        } else {
            player.play();
        }

        player.setOnFullscreenListener(this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            getYouTubePlayerProvider().initialize(getDeveloperKey(), this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return mYoutubePlayerView;
    }

    protected abstract String getVideoUrl();

    protected String getDeveloperKey() {
        return ResourceUtils.getString(R.string.youtube_api_key);
    }

}
