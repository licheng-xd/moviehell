package com.lc.moviehell.service.impl;

import com.lc.moviehell.dao.UstvMapper;
import com.lc.moviehell.dao.domain.Ustv;
import com.lc.moviehell.service.IUstvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lc on 16/4/25.
 */
@Service("ustvService")
public class UstvServiceImpl implements IUstvService {
    private static final Logger logger = LoggerFactory.getLogger(UstvServiceImpl.class);

    @Resource
    private UstvMapper mapper;

    @Override public int insertUstv(Ustv ustv) {
        try {
            return mapper.insertUstv(ustv);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }

    @Override public Ustv getUstvById(long id) {
        try {
            return mapper.getUstvById(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override public List<Ustv> getUstvByOffset(int offset) {
        try {
            return mapper.getUstvsByOffset(offset);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override public List<Ustv> search(String key) {
        try {
            return mapper.searchUstv("%" + key + "%");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override public int getUstvCount() {
        try {
            return mapper.getUstvCount();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }

    @Override public int updateUstv(Ustv ustv) {
        try {
            return mapper.updateUstv(ustv);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }

}
