package com.lc.moviehell.controller;

import com.alibaba.fastjson.JSONArray;
import com.lc.moviehell.bean.RespBody;
import com.lc.moviehell.bean.RespCode;
import com.lc.moviehell.dao.domain.Documentary;
import com.lc.moviehell.service.IDocumentaryService;
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
 * Created by lc on 16/5/6.
 */
@Controller
@RequestMapping(value = {"/documentary"})
public class DocumentaryController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentaryController.class);

    @Resource
    private IDocumentaryService documentaryService;

    @ResponseBody
    @RequestMapping(value = "/page/{offset}", method = RequestMethod.GET)
    public RespBody getDocumentaryPage(@PathVariable int offset) {
        List<Documentary> docs = documentaryService.getByOffset(offset);
        if (docs == null || docs.isEmpty()) {
            return RespBody.builder(RespCode.NOT_FOUND);
        }
        JSONArray arr = new JSONArray();
        for (Documentary doc : docs) {
            arr.add(doc.brief());
        }
        return RespBody.success().setObj(arr);
    }

    @ResponseBody
    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    public RespBody getPageCount() {
        int count = documentaryService.getCount();
        int pageCount = count / 20;
        if (count % 20 > 0) pageCount++;
        return RespBody.success().setObj(pageCount);
    }

    @RequestMapping(value = "/view/{id}")
    public String viewDocumentary(HttpServletRequest request, @PathVariable String id) {
        logger.debug("viewDocumentary id:{}", id);
        Documentary doc = documentaryService.getById(id);
        if (doc == null) {
            return "404";
        } else {
            String name = doc.getName();
            String realName = name.substring(name.indexOf('【') + 1, name.indexOf('】'));
            request.setAttribute("title", name);
            request.setAttribute("name", realName);
            request.setAttribute("img", doc.getImg());
            request.setAttribute("hrefs", doc.getHrefs());
            request.setAttribute("intro", doc.getIntro());
            request.setAttribute("id", doc.getId());
            return "documentary";
        }
    }
}
