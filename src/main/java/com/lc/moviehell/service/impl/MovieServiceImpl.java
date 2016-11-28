package com.lc.moviehell.service.impl;

import com.lc.moviehell.dao.MovieMapper;
import com.lc.moviehell.dao.domain.Movie;
import com.lc.moviehell.service.IMovieService;
import com.lc.moviehell.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lc on 15/8/21.
 */
@Service("movieService")
public class MovieServiceImpl implements IMovieService {
    private static final Logger logger = LoggerFactory.getLogger(
        MovieServiceImpl.class);

    @Resource
    private MovieMapper mapper;

//    @Resource
//    private RedisService redisService; // TODO 缓存

    public int insertMovie(Movie movie) {
        try {
            return mapper.insertMovie(movie);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }

    public Movie getMovieById(String id) {
        try {
            return mapper.getMovieById(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<Movie> getMoviesByOffset(int offset) {
        try {
            return mapper.getMoviesByOffset(offset);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public int getMovieCount() {
        try {
            return mapper.getMovieCount();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }
    }

    public boolean hasMovie(String id) {
        return getMovieById(id) != null;
    }

    public List<Movie> search(String key) {
        try {
            return mapper.searchMovie(StringUtil.getSearchKey(key));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
