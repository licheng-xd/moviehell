package com.lc.moviehell.spider;

import com.lc.moviehell.service.IDocumentaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;

/**
 * Created by lc on 16/5/6.
 */
@Service
public class DocumentaryTask {
    private static final Logger logger = LoggerFactory.getLogger(DocumentaryTask.class);

    @Resource
    private IDocumentaryService documentaryService;

    @Scheduled(cron = "0 */30 * * * ?")
    public void run() {
        logger.info("start DocumentaryInitTask ...");
        Spider.create(new DocumentarySpider())
            .addUrl("http://www.6vhao.net/jlp/index.html")
            .addPipeline(new DocumentaryPipeline(documentaryService))
            .thread(1)
            .run();
    }
}
