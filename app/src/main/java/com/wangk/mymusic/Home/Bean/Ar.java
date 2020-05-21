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
public class Ar {

    private int id;
    private String name;
    private List<String> tns;
    private List<String> alias;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
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