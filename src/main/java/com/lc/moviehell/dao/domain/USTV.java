package com.lc.moviehell.dao.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lc.moviehell.bean.SerializableBean;

/**
 * 美剧
 *
 * Created by lc on 16/4/25.
 */
public class Ustv extends SerializableBean {
    private String id;

    private String name;

    private String img;

    private String intro;

    private String hrefs;

    private int size;

    private String time;

    private long createtime;

    private long updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getHrefs() {
        return hrefs;
    }

    public void setHrefs(String hrefs) {
        this.hrefs = hrefs;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    @Override public String serialize() {
        return JSON.toJSONString(this);
    }

    public String brief() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("name", name);
        obj.put("time", time);
        return obj.toJSONString();
    }
}
