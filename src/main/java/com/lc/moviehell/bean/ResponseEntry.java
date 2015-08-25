package com.lc.moviehell.bean;

import com.alibaba.fastjson.JSON;

/**
 * Created by lc on 15/4/23.
 */
public class ResponseEntry {
    private int code;

    private String msg;

    private Object obj;

    @Deprecated
    public ResponseEntry(int code) {
        this.code = code;
    }

    @Deprecated
    public ResponseEntry(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    private ResponseEntry() {

    }

    public int getCode() {
        return code;
    }

    public ResponseEntry setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseEntry setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObj() {
        return obj;
    }

    public ResponseEntry setObj(Object obj) {
        this.obj = obj;
        return this;
    }

    public static ResponseEntry builder() {
        return new ResponseEntry(RespCode.OK);
    }

    public static ResponseEntry builder(int code) {
        return new ResponseEntry(code);
    }

    @Override public String toString() {
        return JSON.toJSONString(this);
    }
}
