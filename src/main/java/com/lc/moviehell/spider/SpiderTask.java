package com.lc.moviehell.spider;

import com.lc.moviehell.service.impl.MovieServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;

/**
 * Created by lc on 15/8/24.
 */
@Service
public class SpiderTask {
    private static final Logger logger = LoggerFactory.getLogger(
        SpiderTask.class);

    @Resource
    private MovieServiceImpl movieService;

    @Scheduled(cron = "0 0 */1 * * ?")
    public void run() {
        logger.info("start spider movieService:{}", movieService == null);
        Spider.create(new MovieSpider(movieService))
            .addUrl("http://dytt8.net/")
            .addPipeline(new MoviePipeline(movieService))
            .thread(1)
            .run();
    }
}