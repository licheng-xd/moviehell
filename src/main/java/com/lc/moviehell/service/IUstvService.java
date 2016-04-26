package com.lc.moviehell.service;

import com.lc.moviehell.dao.domain.Ustv;

import java.util.List;

/**
 * Created by lc on 16/4/25.
 */
public interface IUstvService {

    public int insertUstv(Ustv ustv);

    public Ustv getUstvById(long id);

    public List<Ustv> getUstvByOffset(int offset);

    public boolean hasUstv(long id);

    public int getUstvCount();

    public int updateUstv(Ustv ustv);
}
