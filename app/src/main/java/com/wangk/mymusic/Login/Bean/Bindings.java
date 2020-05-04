/**
 * Copyright 2020 bejson.com
 */
package com.wangk.mymusic.Login.Bean;

/**
 * Auto-generated: 2020-05-04 11:7:12
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Bindings {

    private long userId;
    private boolean expired;
    private long refreshTime;
    private String tokenJsonStr;
    private String url;
    private long bindingTime;
    private long expiresIn;
    private long id;
    private int type;
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getUserId() {
        return userId;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    public boolean getExpired() {
        return expired;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }
    public long getRefreshTime() {
        return refreshTime;
    }

    public void setTokenJsonStr(String tokenJsonStr) {
        this.tokenJsonStr = tokenJsonStr;
    }
    public String getTokenJsonStr() {
        return tokenJsonStr;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setBindingTime(long bindingTime) {
        this.bindingTime = bindingTime;
    }
    public long getBindingTime() {
        return bindingTime;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
    public long getExpiresIn() {
        return expiresIn;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

}