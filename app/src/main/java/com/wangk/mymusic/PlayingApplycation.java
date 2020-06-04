package com.wangk.mymusic;

import android.app.Application;

public class PlayingApplycation extends Application {

    private String playingUrl;

    public void setPlayingUrl(String playingUrl) {
        this.playingUrl = playingUrl;
    }
    public String getPlayingUrl() {
        return playingUrl;
    }

}