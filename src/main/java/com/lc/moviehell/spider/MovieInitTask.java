package com.lc.moviehell.spider;

import com.lc.moviehell.service.IMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;

/**
 * Created by lc on 16/9/6.
 */
@Service
public class MovieInitTask {

    private static final Logger logger = LoggerFactory.getLogger(
        MovieInitTask.class);

    @Resource
    private IMovieService movieService;

    @Scheduled(fixedDelay = 5000)
    public void run() {
        logger.info("start MovieInitTask ...");

        for (int i=160; i>1; i--) {
            Spider.create(new MovieSpider())
                .addUrl("http://www.6vhao.com/dy/index_" + i + ".html")
                .addPipeline(new MoviePipeline(movieService))
                .thread(1)
                .run();
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }


    }

}
