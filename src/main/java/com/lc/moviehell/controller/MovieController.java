package com.lc.moviehell.controller;

import com.alibaba.fastjson.JSONArray;
import com.lc.moviehell.bean.RespBody;
import com.lc.moviehell.bean.RespCode;
import com.lc.moviehell.dao.domain.Documentary;
import com.lc.moviehell.dao.domain.Movie;
import com.lc.moviehell.dao.domain.P1080;
import com.lc.moviehell.dao.domain.Ustv;
import com.lc.moviehell.service.IDocumentaryService;
import com.lc.moviehell.service.IP1080Service;
import com.lc.moviehell.service.IUstvService;
import com.lc.moviehell.service.impl.MovieServiceImpl;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * moviehell接口
 *
 * Created by lc on 15/8/19.
 */
@Controller
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Resource
    private MovieServiceImpl movieService;

    @Resource
    private IUstvService ustvService;

    @Resource
    private IDocumentaryService documentaryService;

    @Resource
    private IP1080Service p1080Service;

    @ResponseBody
    @RequestMapping(value = "/page/{offset}", method = RequestMethod.GET)
    public RespBody getMoviePage(@PathVariable int offset) {
        List<Movie> movies = movieService.getMoviesByOffset(offset);
        if (movies == null || movies.isEmpty()) {
            return RespBody.builder(RespCode.NOT_FOUND);
        }
        JSONArray arr = new JSONArray();
        for (Movie movie : movies) {
            arr.add(movie.brief());
        }
        return RespBody.success().setObj(arr);
    }

    @ResponseBody
    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    public RespBody getPageCount() {
        int count = movieService.getMovieCount();
        int pageCount = count / 20;
        if (count % 20 > 0) pageCount++;
        return RespBody.success().setObj(pageCount);
    }

    @RequestMapping(value = "/movie/{id}")
    public String viewMoive(HttpServletRequest request, @PathVariable String id) {
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
        try {
            key = new String(Base64.decodeBase64(key), "utf-8");
            logger.debug("search movie key:{}", key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        List<Movie> movies = new ArrayList<Movie>();
        List<Ustv> ustvs = new ArrayList<Ustv>();
        List<Documentary> docs = new ArrayList<Documentary>();
        List<P1080> p1080s = new ArrayList<P1080>();
        if (key.length() > 0) {
            movies = movieService.search(key);
            if (movies == null) {
                movies = new ArrayList<Movie>();
            }
            ustvs = ustvService.search(key);
            if (ustvs == null) {
                ustvs = new ArrayList<Ustv>();
            }
            docs = documentaryService.search(key);
            if (docs == null) {
                docs = new ArrayList<Documentary>();
            }
            p1080s = p1080Service.search(key);
            if (p1080s == null) {
                p1080s = new ArrayList<P1080>();
            }
        }
        request.setAttribute("result", movies);
        request.setAttribute("p1080result", p1080s);
        request.setAttribute("ustvresult", ustvs);
        request.setAttribute("docresult", docs);
        return "search";
    }

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public RespBody getTest() {
        return RespBody.success().setObj("test");
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(new String(Base64.decodeBase64("6Y605Z2X5qO_5ai05ayt55iv"), "utf-8"));
    }
}
