package com.lc.moviehell.cache;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lc on 11/14/14.
 */
public class LimitQueue<E> {

    private int limit;

    public LinkedList<E> queue;

    public LimitQueue(int limit) {
        this.limit = limit;
        queue = new LinkedList<E>();
    }

    public boolean offer(E obj) {
        if (queue.size() >= limit) {
            queue.poll();
        }
        return queue.offer(obj);
    }

    public boolean contains(E obj) {
        return queue.contains(obj);
    }

    public E poll() {
        return queue.poll();
    }

    public int getLimit() {
        return limit;
    }

    public List<E> subList(int start, int end) {
        return queue.subList(start, end);
    }

    public int size() {
        return queue.size();
    }

    public Iterator<E> iterator() {
        return queue.iterator();
    }
}
