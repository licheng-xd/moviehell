package com.lc.moviehell.spider;

import com.lc.moviehell.service.IDocumentaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;

/**
 * Created by lc on 16/5/6.
 */
//@Service
public class DocumentaryInitTask {
    private static final Logger logger = LoggerFactory.getLogger(
        DocumentaryInitTask.class);

    @Resource
    private IDocumentaryService documentaryService;

    @Scheduled(fixedDelay = 5000)
    public void run() {
        logger.info("start DocumentaryInitTask ...");
        Spider.create(new DocumentarySpider())
            .addUrl("http://www.6vhao.net/jlp/index.html")
            .addPipeline(new DocumentaryPipeline(documentaryService))
            .thread(1)
            .run();
        for (int i=2; i<23; i++) {
            Spider.create(new DocumentarySpider())
                .addUrl("http://www.6vhao.net/jlp/index_" + i + ".html")
                .addPipeline(new DocumentaryPipeline(documentaryService))
                .thread(1)
                .run();
        }
    }

    public void test() {
        logger.info("start DocumentaryInitTask test ...");
        Spider.create(new DocumentarySpider())
            .addUrl("http://www.6vhao.net/jlp/2015-10-18/28402.html")
            .addPipeline(new DocumentaryPipeline(documentaryService))
            .thread(1)
            .run();
    }

    public static void main(String[] args) {
        new DocumentaryInitTask().test();
    }
}
