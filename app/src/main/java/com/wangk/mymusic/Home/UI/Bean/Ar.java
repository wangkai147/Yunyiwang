/**
 * Copyright 2020 bejson.com
 */
package com.wangk.mymusic.Home.UI.Bean;
import java.util.List;

/**
 * Auto-generated: 2020-06-06 9:44:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Ar {

    private long id;
    private String name;
    private List<String> tns;
    private List<String> alias;
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setTns(List<String> tns) {
        this.tns = tns;
    }
    public List<String> getTns() {
        return tns;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }
    public List<String> getAlias() {
        return alias;
    }

}