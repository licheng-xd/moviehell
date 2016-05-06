package com.lc.moviehell.dao;

import com.lc.moviehell.dao.domain.Documentary;

/**
 * Created by lc on 16/5/6.
 */
public interface DocumentaryMapper {
    Documentary getById(String id);

    int insertDocumentary(Documentary doc);

    int updateDocumentary(Documentary doc);
}
