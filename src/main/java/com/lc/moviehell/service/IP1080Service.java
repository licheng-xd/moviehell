package com.lc.moviehell.service;

import com.lc.moviehell.dao.domain.P1080;

import java.util.List;

/**
 * Created by lc on 16/9/6.
 */
public interface IP1080Service {

    P1080 getP1080(String id);

    int insertP1080(P1080 p1080);

    List<P1080> getP1080ByOffset(int offset);

    List<P1080> search(String key);

    int getP1080Count();

//    int updateP1080(P1080 p080);
}
