package com.collegiate.ui.common;

import android.content.Intent;
import android.widget.Toast;

import com.collegiate.IntentConstants;
import com.collegiate.R;
import com.collegiate.core.activity.BaseActivity;
import com.collegiate.util.ResourceUtils;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * Created by gauravarora on 10/09/15.
 */
public class BaseYoutubeFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private YouTubePlayer mYoutubePlayer;
    private String mVideoId;

    public BaseYoutubeFragment() {
        initialize(getDeveloperKey(), this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        mYoutubePlayer = player;

        if (!wasRestored) {
            player.loadVideo(getVideoId(), getStartTime());
        } else {
            player.play();
        }

        player.setOnFullscreenListener(this);
        configurePlayer(player);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(getBaseActivity(), RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFullscreen(boolean b) {

    }

    public String getVideoId() {
        if (mVideoId == null) {
            mVideoId = getArguments().getString(IntentConstants.YOUTUBE_VIDEO_ID);
        }
        return mVideoId;
    }

    public int getStartTime(){
        return getArguments().getInt(IntentConstants.YOUTUBE_PLAYER_START_TIME, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            getYouTubePlayerProvider().initialize(getDeveloperKey(), this);
        }
    }

    public int getTimeElapsed(){
        if(mYoutubePlayer == null){
            return 0;
        }
        return mYoutubePlayer.getCurrentTimeMillis();
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected void configurePlayer(YouTubePlayer youTubePlayer) {

    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return this;
    }

    protected String getDeveloperKey() {
        return ResourceUtils.getString(R.string.youtube_api_key);
    }

}
