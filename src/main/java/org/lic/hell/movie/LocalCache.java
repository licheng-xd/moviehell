package org.lic.hell.movie;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lc on 15/7/24.
 */
public class LocalCache {
    private LinkedList<Movie> list = new LinkedList<Movie>();

    private static LocalCache _instance = new LocalCache();

    public static LocalCache getInstance() {
        return _instance;
    }

    private LocalCache() {}

    public void addLast(Movie movie) {
        list.addLast(movie);
    }

    public List<Movie> getAll() {
        return list;
    }

    public Movie get(int index) {
        return list.get(index);
    }

    public void clear() {
        list.clear();
    }

    public int size() {
        return list.size();
    }
}
