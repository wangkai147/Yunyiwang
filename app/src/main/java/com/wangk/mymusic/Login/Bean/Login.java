/**
 * Copyright 2020 bejson.com
 */
package com.wangk.mymusic.Login.Bean;
import java.util.List;

/**
 * Auto-generated: 2020-05-04 11:7:12
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Login {

    private int loginType;
    private int code;
    private Account account;
    private String token;
    private Profile profile;
    private List<Bindings> bindings;
    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }
    public int getLoginType() {
        return loginType;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    public Account getAccount() {
        return account;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    public Profile getProfile() {
        return profile;
    }

    public void setBindings(List<Bindings> bindings) {
        this.bindings = bindings;
    }
    public List<Bindings> getBindings() {
        return bindings;
    }

}