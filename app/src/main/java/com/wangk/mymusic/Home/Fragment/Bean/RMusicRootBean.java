/**
 * Copyright 2020 bejson.com
 */
package com.wangk.mymusic.Home.Fragment.Bean;;
import java.util.List;

/**
 * Auto-generated: 2020-06-06 8:22:7
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RMusicRootBean {

    private boolean hasTaste;
    private int code;
    private int category;
    private List<Result> result;
    public void setHasTaste(boolean hasTaste) {
        this.hasTaste = hasTaste;
    }
    public boolean getHasTaste() {
        return hasTaste;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setCategory(int category) {
        this.category = category;
    }
    public int getCategory() {
        return category;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
    public List<Result> getResult() {
        return result;
    }

}