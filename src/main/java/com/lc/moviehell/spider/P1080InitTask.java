package com.lc.moviehell.spider;

import com.lc.moviehell.service.IP1080Service;
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
public class P1080InitTask {

    private static final Logger logger = LoggerFactory.getLogger(
        P1080InitTask.class);

    @Resource
    private IP1080Service p1080Service;

    @Scheduled(fixedDelay = 5000)
    public void run() {
        logger.info("start P1080InitTask ...");

        for (int i=121; i>0; i--) {
            Spider.create(new DocumentarySpider())
                .addUrl("http://www.mp4ba.com/index.php?page=" + i)
                .addPipeline(new P1080Pipeline(p1080Service))
                .thread(1)
                .run();
        }
    }

}
