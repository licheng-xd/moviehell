package com.lc.moviehell.controller;

import com.lc.moviehell.dao.P1080Mapper;
import com.lc.moviehell.dao.domain.P1080;
import com.lc.moviehell.service.IP1080Service;
import com.lc.moviehell.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lc on 16/9/6.
 */
public class P1080ServiceImplMock implements IP1080Service {
    private static final Logger logger = LoggerFactory.getLogger(P1080ServiceImplMock.class);

    @Resource
    private P1080Mapper mapper;

    @Override
    public P1080 getP1080(String id) {
        try {
            return mapper.getP1080ById(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public int insertP1080(P1080 p1080) {
        try {
            return mapper.insertP1080(p1080);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }

    @Override
    public List<P1080> getP1080ByOffset(int offset) {
        try {
            return mapper.getP1080sByOffset(offset);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<P1080> search(String key) {
        try {
            return mapper.searchP1080(StringUtil.getSearchKey(key));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public int getP1080Count() {
        try {
            return mapper.getP1080Count();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }
}
