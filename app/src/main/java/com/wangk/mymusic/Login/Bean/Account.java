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
public class Account {

    private long id;
    private String userName;
    private int type;
    private int status;
    private int whitelistAuthority;
    private long createTime;
    private String salt;
    private int tokenVersion;
    private int ban;
    private int baoyueVersion;
    private int donateVersion;
    private int vipType;
    private int viptypeVersion;
    private boolean anonimousUser;
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setWhitelistAuthority(int whitelistAuthority) {
        this.whitelistAuthority = whitelistAuthority;
    }
    public int getWhitelistAuthority() {
        return whitelistAuthority;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public long getCreateTime() {
        return createTime;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    public String getSalt() {
        return salt;
    }

    public void setTokenVersion(int tokenVersion) {
        this.tokenVersion = tokenVersion;
    }
    public int getTokenVersion() {
        return tokenVersion;
    }

    public void setBan(int ban) {
        this.ban = ban;
    }
    public int getBan() {
        return ban;
    }

    public void setBaoyueVersion(int baoyueVersion) {
        this.baoyueVersion = baoyueVersion;
    }
    public int getBaoyueVersion() {
        return baoyueVersion;
    }

    public void setDonateVersion(int donateVersion) {
        this.donateVersion = donateVersion;
    }
    public int getDonateVersion() {
        return donateVersion;
    }

    public void setVipType(int vipType) {
        this.vipType = vipType;
    }
    public int getVipType() {
        return vipType;
    }

    public void setViptypeVersion(int viptypeVersion) {
        this.viptypeVersion = viptypeVersion;
    }
    public int getViptypeVersion() {
        return viptypeVersion;
    }

    public void setAnonimousUser(boolean anonimousUser) {
        this.anonimousUser = anonimousUser;
    }
    public boolean getAnonimousUser() {
        return anonimousUser;
    }

}