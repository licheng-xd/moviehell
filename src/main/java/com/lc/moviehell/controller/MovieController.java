package com.lc.moviehell.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.util.Base64;
import com.lc.moviehell.bean.RespCode;
import com.lc.moviehell.bean.ResponseEntry;
import com.lc.moviehell.dao.domain.Movie;
import com.lc.moviehell.service.impl.MovieServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lc on 15/8/19.
 */
@Controller
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Resource
    private MovieServiceImpl movieService;

    @ResponseBody
    @RequestMapping(value = "/page/{offset}", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public ResponseEntry getMoviePage(HttpServletRequest request, @PathVariable int offset) {
        List<Movie> movies = movieService.getMoviesByOffset(offset);
        if (movies == null || movies.isEmpty()) {
            return ResponseEntry.builder(RespCode.NOT_FOUND);
        }
        JSONArray arr = new JSONArray();
        for (Movie movie : movies) {
            arr.add(movie.brief());
        }
        return ResponseEntry.builder().setObj(arr);
    }

    @ResponseBody
    @RequestMapping(value = "/pages", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public ResponseEntry getPageCount(HttpServletRequest request) {
        int count = movieService.getMovieCount();
        int pageCount = count / 20;
        if (count % 20 > 0) pageCount++;
        return ResponseEntry.builder().setObj(pageCount);
    }

    @RequestMapping(value = "/movie/{id}")
    public String viewMoive(HttpServletRequest request, @PathVariable int id) {
        logger.debug("viewMovie id:{}", id);
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            return "404";
        } else {
            String name = movie.getName();
            String realName = name.substring(name.indexOf('《') + 1, name.indexOf('》'));
            request.setAttribute("title", name);
            request.setAttribute("name", realName);
            request.setAttribute("img", movie.getImg());
            request.setAttribute("href", movie.getHref());
            request.setAttribute("intro", movie.getIntro());
            request.setAttribute("id", movie.getId());
            return "movie";
        }
    }

    @RequestMapping(value = "search/{key}")
    public String search(HttpServletRequest request, @PathVariable String key) {
        List<Movie> movies = new ArrayList<Movie>();
        if (key.length() > 0) {
            try {
                key = new String(Base64.decodeFast(key), "utf-8");
                logger.debug("search movie key:{}", key);
                movies = movieService.search(key);
                if (movies == null) {
                    movies = new ArrayList<Movie>();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        request.setAttribute("result", movies);
        return "search";
    }

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public ResponseEntry getTest(HttpServletRequest request) {
        return ResponseEntry.builder().setObj("test");
    }
}
