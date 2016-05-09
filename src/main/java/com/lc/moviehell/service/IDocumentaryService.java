package com.lc.moviehell.service;

import com.lc.moviehell.dao.domain.Documentary;

import java.util.List;

/**
 * Created by lc on 16/5/6.
 */
public interface IDocumentaryService {

    public List<Documentary> getByOffset(int offset);

    public int getCount();

    public Documentary getById(String id);

    public int insert(Documentary doc);

    public int update(Documentary doc);

    public List<Documentary> search(String key);
}
