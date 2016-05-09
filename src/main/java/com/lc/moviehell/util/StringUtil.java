package com.lc.moviehell.util;

/**
 * Created by lc on 16/5/9.
 */
public class StringUtil {

    public static String getSearchKey(String key) {
        char[] chs = key.toCharArray();
        StringBuilder sb = new StringBuilder("%");
        for (char ch : chs) {
            sb.append(ch).append("%");
        }
        return sb.toString();
    }
}
