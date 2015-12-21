package com.lc.moviehell.spider;

import com.lc.moviehell.service.impl.MovieServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;

/**
 * Created by lc on 15/8/24.
 */
//@Service
public class OnceTask {
    private static final Logger logger = LoggerFactory.getLogger(
        OnceTask.class);

    @Resource
    private MovieServiceImpl movieService;

    //@Scheduled(fixedDelay = 5000)
    public void runOnce() {
        logger.info("start once spider ...");
        for (int i=1; i<272; i++) {
            Spider.create(new OnceSpider())
                .addUrl("http://www.ygdy8.net/html/gndy/dyzz/list_23_" + i + ".html")
                .addPipeline(new MoviePipeline(movieService))
                .thread(1)
                .run();
        }
    }
}
