package com.lc.moviehell.spider;

import com.lc.moviehell.service.impl.UstvServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;

/**
 * Created by lc on 16/4/25.
 */
@Service
public class UstvTask {
    private static final Logger logger = LoggerFactory.getLogger(
        UstvTask.class);

    @Resource
    private UstvServiceImpl ustvService;

    @Scheduled(cron = "0 0/20 * * * ?")
    public void run() {
        logger.info("start ustv spider ...");
        Spider.create(new UstvSpider())
            .addUrl("http://www.ygdy8.net/")
            .addPipeline(new UstvPipeline(ustvService))
            .thread(1)
            .run();
    }

    public static void main(String[] args) {
        new UstvTask().run();
    }
}
