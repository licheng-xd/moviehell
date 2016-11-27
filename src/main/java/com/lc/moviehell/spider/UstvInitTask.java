package com.lc.moviehell.spider;

import com.lc.moviehell.service.IUstvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;

/**
 * 修复宕机时段丢失数据 或 初始化数据
 *
 * Created by lc on 15/8/24.
 */
@Service
public class UstvInitTask {
    private static final Logger logger = LoggerFactory.getLogger(
        UstvInitTask.class);

    @Resource
    private IUstvService ustvService;

    @Scheduled(fixedDelay = 5000)
    public void run() {
        logger.info("start UstvInitTask ...");
        for (int i=44; i>1; i--) {
            Spider.create(new UstvSpider())
                .addUrl("http://www.6vhao.com/mj/index_" + i + ".html")
                .addPipeline(new UstvPipeline(ustvService))
                .thread(1)
                .run();
        }
    }

}
