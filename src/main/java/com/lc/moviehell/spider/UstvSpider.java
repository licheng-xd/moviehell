package com.lc.moviehell.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 美剧爬虫
 *
 * Created by lc on 16/4/25.
 */
public class UstvSpider implements PageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(
        UstvSpider.class);

    private Site site = Site.me()
        .setRetryTimes(3).setSleepTime(1000).setUseGzip(true);

    @Override public void process(Page page) {
        try {
            String url = page.getUrl().toString();
            Html html = page.getHtml();
            String name = html.xpath("//div[@class='title_all']/h1/font/text()").toString();
            String image = html.xpath("//span[@style='FONT-SIZE: 12px']/p/img/@src").toString();
            if (name == null || image == null) {
                page.setSkip(true);
                List<String> urls = html.xpath(
                    "//div[@class='co_content3']//td//a/@href").regex(".*/tv/oumeitv.*[1-9]\\d*.html$").all();
//                logger.info(urls.toString());
                page.addTargetRequests(urls);
                return;
            }
            page.putField("name", name);
            page.putField("img", image);
            String introduce = html.xpath("//span[@style='FONT-SIZE: 12px']/p").replace("<p>", "")
                .replace("</p>", "")
                .replace("<br />", "\r\n").replace("<.*>", " ").toString()
                .trim();
            page.putField("introduce", introduce);
            page.putField("url", url);
            page.putField("hrefs", html.xpath("//td[@style='WORD-WRAP: break-word']/a/@href").all());
            String time = html.xpath("//div[@class='co_content8']/ul/text()").toString()
                .substring(6).replaceAll("[\\s\\u00A0]+$", ""); // 去掉所有空白符,包括uincode编码的对应ascii码=160的奇葩空格
            page.putField("time", time);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override public Site getSite() {
        return site;
    }
}
