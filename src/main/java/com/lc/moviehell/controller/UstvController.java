package com.lc.moviehell.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lc.moviehell.bean.RespBody;
import com.lc.moviehell.bean.RespCode;
import com.lc.moviehell.dao.domain.Ustv;
import com.lc.moviehell.service.IUstvService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lc on 16/4/26.
 */
@Controller
@RequestMapping(value = {"/ustv"})
public class UstvController {
    private static final Logger logger = LoggerFactory.getLogger(UstvController.class);

    @Resource
    private IUstvService ustvService;

    @ResponseBody
    @RequestMapping(value = "/page/{offset}", method = RequestMethod.GET)
    public RespBody getUstvPage(@PathVariable int offset) {
        List<Ustv> tvs = ustvService.getUstvByOffset(offset);
        if (tvs == null || tvs.isEmpty()) {
            return RespBody.builder(RespCode.NOT_FOUND);
        }
        JSONArray arr = new JSONArray();
        for (Ustv tv : tvs) {
            arr.add(tv.brief());
        }
        return RespBody.success().setObj(arr);
    }

    @ResponseBody
    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    public RespBody getPageCount() {
        int count = ustvService.getUstvCount();
        int pageCount = count / 20;
        if (count % 20 > 0) pageCount++;
        return RespBody.success().setObj(pageCount);
    }

    @RequestMapping(value = "/view/{id}")
    public String viewUstv(HttpServletRequest request, @PathVariable int id) {
        logger.debug("viewUstv id:{}", id);
        Ustv tv = ustvService.getUstvById(id);
        if (tv == null) {
            return "404";
        } else {
            String name = tv.getName();
            String realName = name.substring(name.indexOf('《') + 1, name.indexOf('》'));
            request.setAttribute("title", name);
            request.setAttribute("name", realName);
            request.setAttribute("img", tv.getImg());
            request.setAttribute("hrefs", JSON.parseArray(tv.getHrefs()));
            request.setAttribute("intro", tv.getIntro());
            request.setAttribute("id", tv.getId());
            return "ustv";
        }
    }

    @RequestMapping(value = "search/{key}")
    public String search(HttpServletRequest request, @PathVariable String key) {
        List<Ustv> tvs = new ArrayList<Ustv>();
        if (key.length() > 0) {
            try {
                key = new String(Base64.decodeBase64(key), "utf-8");
                logger.debug("search movie key:{}", key);
                tvs = ustvService.search(key);
                if (tvs == null) {
                    tvs = new ArrayList<Ustv>();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        request.setAttribute("result", tvs);
        return "search";
    }
}
