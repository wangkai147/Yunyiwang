/**
 * Copyright 2020 bejson.com
 */
package com.wangk.mymusic.Home.Fragment.CoverBean;
import java.util.List;

/**
 * Auto-generated: 2020-06-05 11:44:8
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CoverRootBean {

    private boolean more;
    private List<Playlist> playlist;
    private int code;
    public void setMore(boolean more) {
        this.more = more;
    }
    public boolean getMore() {
        return more;
    }

    public void setPlaylist(List<Playlist> playlist) {
        this.playlist = playlist;
    }
    public List<Playlist> getPlaylist() {
        return playlist;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

}