package com.klinker.android.twitter.ui.tweet_viewer.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.klinker.android.twitter.R;
import com.klinker.android.twitter.settings.AppSettings;
import com.klinker.android.twitter.ui.widgets.HoloTextView;


public class TweetYouTubeFragment extends YouTubePlayerFragment implements
        YouTubePlayer.OnInitializedListener {

    private AppSettings settings;
    private Context context;
    private View layout;
    private String url;

    private YouTubePlayerView player;
    private HoloTextView error;

    public TweetYouTubeFragment(AppSettings settings, String url) {
        this.settings = settings;
        this.url = url;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        layout = inflater.inflate(R.layout.youtube_fragment, null);
        player = (YouTubePlayerView) layout.findViewById(R.id.youtube_view);
        error = (HoloTextView) layout.findViewById(R.id.error);

        player.initialize(AppSettings.YOUTUBE_API_KEY, this);

        return layout;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        String video;

        if (url.contains("youtube")) { // normal youtube link
            // first get the youtube video code
            int start = url.indexOf("v=") + 2;
            int end = url.length();
            if (url.substring(start).contains("&")) {
                end = url.indexOf("&");
            }
            video = url.substring(start, end);
        } else { // shortened youtube link
            // first get the youtube video code
            int start = url.indexOf(".be/") + 4;
            int end = url.length();
            if (url.substring(start).contains("&")) {
                end = url.indexOf("&");
            }
            video = url.substring(start, end);
        }

        youTubePlayer.loadVideo(video);
        youTubePlayer.setShowFullscreenButton(false);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        player.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
    }
}
