package com.lc.moviehell.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lc on 15/8/20.
 */
public class MovieListCache {

    private MovieListCache() {
        queue = new LimitQueue<String>(200);
    }

    private static final MovieListCache _instance = new MovieListCache();

    public static MovieListCache getInstance() {
        return _instance;
    }

    private LimitQueue<String> queue;

    public boolean put(String entry) {
        return queue.contains(entry) || queue.offer(entry);
    }

    public List<String> get(int start, int end) {
        if (start >= queue.size()) {
            return new ArrayList<String>();
        }
        if (end >= queue.size() - 1)
            end = queue.size() - 1;
        if (start < 0)
            start = 0;
        return queue.subList(start, end);
    }

}
