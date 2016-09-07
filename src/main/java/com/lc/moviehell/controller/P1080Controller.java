package com.lc.moviehell.controller;

import com.alibaba.fastjson.JSONArray;
import com.lc.moviehell.bean.RespBody;
import com.lc.moviehell.bean.RespCode;
import com.lc.moviehell.dao.domain.P1080;
import com.lc.moviehell.service.IP1080Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lc on 16/9/7.
 */
@Controller
@RequestMapping(value = {"/p1080"})
public class P1080Controller {
    private static final Logger logger = LoggerFactory.getLogger(P1080Controller.class);

    @Resource
    private IP1080Service p1080Service;

    @ResponseBody
    @RequestMapping(value = "/page/{offset}", method = RequestMethod.GET)
    public RespBody getP1080Page(@PathVariable int offset) {
        List<P1080> p1080s = p1080Service.getP1080ByOffset(offset);
        if (p1080s == null || p1080s.isEmpty()) {
            return RespBody.builder(RespCode.NOT_FOUND);
        }
        JSONArray arr = new JSONArray();
        for (P1080 p1080 : p1080s) {
            arr.add(p1080.brief());
        }
        return RespBody.success().setObj(arr);
    }

    @ResponseBody
    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    public RespBody getPageCount() {
        int count = p1080Service.getP1080Count();
        int pageCount = count / 20;
        if (count % 20 > 0) pageCount++;
        return RespBody.success().setObj(pageCount);
    }

    @RequestMapping(value = "/view/{id}")
    public String viewUstv(HttpServletRequest request, @PathVariable String id) {
        logger.debug("viewP1080 id:{}", id);
        P1080 p1080 = p1080Service.getP1080(id);
        if (p1080 == null) {
            return "404";
        } else {
            String name = p1080.getName();
//            String realName = name.substring(name.indexOf('《') + 1, name.indexOf('》'));
            request.setAttribute("title", name);
            request.setAttribute("name", name);
            request.setAttribute("img", p1080.getImg());
            request.setAttribute("href", p1080.getHref());
            request.setAttribute("intro", p1080.getIntro());
            request.setAttribute("id", p1080.getId());
            return "p1080";
        }
    }
}
