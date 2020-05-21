/**
 * Copyright 2020 bejson.com
 */
package com.wangk.mymusic.Home.Bean;
import java.util.List;

/**
 * Auto-generated: 2020-05-20 20:10:1
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BannerRoot {

    private List<Banners> banners;
    private int code;
    public void setBanners(List<Banners> banners) {
        this.banners = banners;
    }
    public List<Banners> getBanners() {
        return banners;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

}