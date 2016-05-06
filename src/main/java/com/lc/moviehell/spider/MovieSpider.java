package com.lc.moviehell.spider;

import com.lc.moviehell.service.impl.MovieServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * 电影爬虫
 *
 * Created by lc on 15/7/22.
 */
public class MovieSpider implements PageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(
        MovieSpider.class);

    private MovieServiceImpl movieService;

    public MovieSpider(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    private Site site = Site.me()
        .setRetryTimes(3).setSleepTime(1000).setUseGzip(true);

    @Override
    public void process(Page page) {
        try {
            String name = page.getHtml()
                .xpath("//div[@class='title_all']/h1/font/text()").toString();
            String img = page.getHtml()
                .xpath("//span[@style='FONT-SIZE: 12px']/p/img/@src")
                .toString();
            if (name == null || img == null) {
                page.setSkip(true);
                List<String> urls = page.getHtml().css("div.co_content2")
                    .links().all();
                if (movieService.getMovieCount() > 20 && urls.size() > 20) {
                    page.addTargetRequests(urls.subList(0, 20));
                } else {
                    page.addTargetRequests(urls);
                }
                return;
            }
            page.putField("name", name);
            page.putField("href", page.getHtml()
                .xpath("//td[@style='WORD-WRAP: break-word']/a/@href")
                .toString());
            page.putField("img", img);
            String introduce = page.getHtml()
                .xpath("//span[@style='FONT-SIZE: 12px']/p").replace("<p>", "")
                .replace("</p>", "")
                .replace("<br />", "\r\n").replace("<.*>", " ").toString()
                .trim();
            page.putField("introduce", introduce);
            String url = page.getUrl().toString();
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
