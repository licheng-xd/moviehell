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
public class MovieTask {
    private static final Logger logger = LoggerFactory.getLogger(
        MovieTask.class);

    @Resource
    private MovieServiceImpl movieService;

    @Scheduled(cron = "0 */20 * * * ?")
    public void run() {
        logger.info("start movie spider ...");
        Spider.create(new MovieSpider(movieService))
            .addUrl("http://dytt8.net/")
            .addPipeline(new MoviePipeline(movieService))
            .thread(1)
            .run();
    }

    public static void main(String[] args) {
        new MovieTask().run();
    }

}
