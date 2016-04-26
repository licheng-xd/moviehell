package com.lc.moviehell.spider;

import com.lc.moviehell.service.IUstvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;

/**
 * 修复宕机时段丢失数据 或 初始化数据
 *
 * Created by lc on 15/8/24.
 */
//@Service
public class OnceTask {
    private static final Logger logger = LoggerFactory.getLogger(
        OnceTask.class);

    @Resource
    private IUstvService ustvService;

//    @Scheduled(fixedDelay = 5000)
    public void run() {
        logger.info("start once spider ...");
        for (int i=1; i<23; i++) {
            Spider.create(new OnceSpider())
                .addUrl("http://www.ygdy8.net/html/tv/oumeitv/list_9_" + i + ".html")
                .addPipeline(new UstvPipeline(ustvService))
                .thread(1)
                .run();
        }
    }

    public void test() {
        logger.info("start test spider ...");
        Spider.create(new UstvSpider())
            .addUrl("http://dytt8.net/html/tv/oumeitv/20150922/49088.html")
            .addPipeline(new UstvPipeline(ustvService))
            .thread(1)
            .run();
    }

    public static void main(String[] args) {
        new OnceTask().test();
    }
}
