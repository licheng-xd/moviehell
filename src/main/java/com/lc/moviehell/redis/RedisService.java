package com.lc.moviehell.redis;

import com.lc.moviehell.bean.SerializableBean;

/**
 * Created by lc on 15/8/20.
 */
public interface RedisService {

    public boolean put(String k, SerializableBean v);

    public boolean put(String k, String v);

    public <T> T get(String k, Class<T> clazz);

    public String get(String k);

    public boolean hasKey(String k);

}
