package com.lc.moviehell.dao;

import com.lc.moviehell.dao.domain.P1080;

import java.util.List;

/**
 * Created by lc on 16/4/25.
 */
public interface P1080Mapper {

    int insertP1080(P1080 p1080);

    P1080 getP1080ById(String id);

    List<P1080> getP1080sByOffset(int offset);

    int getP1080Count();

    List<P1080> searchP1080(String key);
}
