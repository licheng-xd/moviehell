package com.lc.moviehell.service.impl;

import com.lc.moviehell.dao.DocumentaryMapper;
import com.lc.moviehell.dao.domain.Documentary;
import com.lc.moviehell.service.IDocumentaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lc on 16/5/6.
 */
@Service("documentaryService")
public class DocumentaryServiceImpl implements IDocumentaryService {
    private static final Logger logger = LoggerFactory.getLogger(
        DocumentaryServiceImpl.class);

    @Resource
    private DocumentaryMapper mapper;

    @Override public Documentary getById(String id) {
        try {
            return mapper.getById(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override public int insert(Documentary doc) {
        try {
            return mapper.insertDocumentary(doc);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }

    @Override public int update(Documentary doc) {
        try {
            return mapper.updateDocumentary(doc);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }
}
