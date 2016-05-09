package com.lc.moviehell.dao;

import com.lc.moviehell.dao.domain.Documentary;

import java.util.List;

/**
 * Created by lc on 16/5/6.
 */
public interface DocumentaryMapper {
    List<Documentary> getDocumentaryByOffset(int offset);

    int getDocumentaryCount();

    Documentary getDocumentaryById(String id);

    int insertDocumentary(Documentary doc);

    int updateDocumentary(Documentary doc);

    List<Documentary> searchDocumentary(String key);
}
