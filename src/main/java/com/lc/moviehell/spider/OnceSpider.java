package com.lc.moviehell.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by lc on 15/7/22.
 */
public class OnceSpider implements PageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(
        OnceSpider.class);

    private Site site = Site.me()
        .setRetryTimes(3).setSleepTime(1000).setUseGzip(true);

    @Override
    public void process(Page page) {
        try {
            String url = page.getUrl().toString();
            if (url.contains("list_")) {
                page.setSkip(true);
                List<String> urls = page.getHtml().css("a.ulink").links().all();
                page.addTargetRequests(urls);
                return;
            }
            page.putField("name", page.getHtml()
                .xpath("//div[@class='title_all']/h1/font/text()").toString());
            page.putField("href", page.getHtml()
                .xpath("//td[@style='WORD-WRAP: break-word']/a/@href").toString());
            page.putField("img", page.getHtml()
                .xpath("//span[@style='FONT-SIZE: 12px']/p/img/@src")
                .toString());
            String introduce = page.getHtml()
                .xpath("//span[@style='FONT-SIZE: 12px']/p").replace("<p>", "")
                .replace("</p>", "")
                .replace("<br />", "\r\n").replace("<.*>", " ").toString()
                .trim();
            page.putField("introduce", introduce);

            page.putField("url", url);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
