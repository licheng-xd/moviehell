package com.lc.moviehell.service;

import com.lc.moviehell.dao.domain.Documentary;

/**
 * Created by lc on 16/5/6.
 */
public interface IDocumentaryService {

    public Documentary getById(String id);

    public int insert(Documentary doc);

    public int update(Documentary doc);
}
