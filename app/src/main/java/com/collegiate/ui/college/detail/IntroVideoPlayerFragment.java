package com.collegiate.ui.college.detail;

import com.collegiate.ui.common.BaseYoutubeFragment;
import com.google.android.youtube.player.YouTubePlayer;

/**
 * Created by gauravarora on 10/09/15.
 */
public class IntroVideoPlayerFragment extends BaseYoutubeFragment {

    protected void configurePlayer(YouTubePlayer player) {
        player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
    }

}
