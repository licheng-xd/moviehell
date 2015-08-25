package com.lc.moviehell.bean;

import com.alibaba.fastjson.JSON;

/**
 * Created by lc on 15/8/20.
 */
public abstract class SerializableBean {

    public abstract String serialize();

    public static <T> T unserialize(String s, Class<T> clazz) {
        return JSON.parseObject(s, clazz);
    }
}
