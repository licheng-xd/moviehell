package com.lc.moviehell.spider;

import com.lc.moviehell.dao.domain.P1080;
import com.lc.moviehell.service.IP1080Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by lc on 16/9/6.
 */
public class P1080Pipeline implements Pipeline {
    private static final Logger logger = LoggerFactory.getLogger(P1080Pipeline.class);

    private IP1080Service p1080Service;

    public P1080Pipeline(IP1080Service p1080Service) {
        this.p1080Service = p1080Service;
    }

    @Override
    public void process(ResultItems result, Task task) {
        String name = result.get("name");
        String img = result.get("img");
        String href = result.get("href");
        String introduce = result.get("introduce");
        String url = result.get("url");
        String time = result.get("time");
        if (img == null) {
            img = "http://nos-yx.netease.com/yixinpublic/moviehell-404.jpg";
        }
        if (name == null || href == null || introduce == null || url == null) {
            logger.warn("invalid result: {}", result);
        } else {
            if (!name.contains("1080P")) {
                logger.debug("非1080P的不要: {}", name);
                return;
            }
            String[] tmp = url.split("\\?");
            String id = tmp[1].split("=")[1];
            P1080 p1080 = p1080Service.getP1080(id);
            long now = System.currentTimeMillis();
            if (p1080 == null) {
                // 创建
                p1080 = new P1080();
                p1080.setId(id);
                p1080.setName(name);
                p1080.setImg(img);
                p1080.setHref(href);
                p1080.setIntro(introduce);
                p1080.setTime(time);
                p1080.setUpdatetime(now);
                p1080.setCreatetime(now);
                p1080Service.insertP1080(p1080);
                logger.info("create new p1080: {} {}", id, name);
            } else {
                logger.info("p1080 exist: {} {} ", id, name);
            }
        }
    }
}
