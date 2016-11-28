package com.lc.moviehell.dao;

import com.lc.moviehell.dao.domain.Ustv;

import java.util.List;

/**
 * Created by lc on 16/4/25.
 */
public interface UstvMapper {

    int insertUstv(Ustv ustv);

    int updateUstv(Ustv ustv);

    Ustv getUstvById(String id);

    List<Ustv> getUstvsByOffset(int offset);

    int getUstvCount();

    List<Ustv> searchUstv(String key);
}
