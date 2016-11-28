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

    @Scheduled(cron = "0 0/30 * * * ?")
    public void run() {
        logger.info("start UstvTask ...");
        Spider.create(new UstvSpider())
            .addUrl("http://www.6vhao.com/mj/index.html")
            .addPipeline(new UstvPipeline(ustvService))
            .thread(1)
            .run();
    }

}
