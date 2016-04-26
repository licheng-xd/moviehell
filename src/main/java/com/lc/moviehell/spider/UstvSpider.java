package com.lc.moviehell.spider;

import com.lc.moviehell.util.RegexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.regex.Matcher;

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
            String timeTmp = html.xpath("//div[@class='co_content8']/ul/text()").toString();
            String time = null;
            Matcher m = RegexUtil.timePattern.matcher(timeTmp);
            if (m.find()) {
                time = m.group();
            }
            if (name == null || time == null) {
                page.setSkip(true);
                List<String> urls = html.xpath(
                    "//div[@class='co_content3']//td//a/@href").regex(".*/tv/oumeitv.*[1-9]\\d*.html$").all();
//                logger.info(urls.toString());
                page.addTargetRequests(urls);
                return;
            }
            if (!url.contains("/tv/oumeitv")) {
                page.setSkip(true);
                return;
            }
            page.putField("name", name);
            String image = html.xpath("//span[@style='FONT-SIZE: 12px']/p/img/@src").toString();
            if (image == null) {
                image = html.xpath("//span[@style='FONT-SIZE: 12px']/img/@src").toString();
                if (image == null) {
                    image = html.xpath("//span[@style='FONT-SIZE: 12px']/p/font/img/@src").toString();
                }
            }
            page.putField("img", image);
            List<String> introduces = html.xpath("//span[@style='FONT-SIZE: 12px']").all();
            StringBuilder sb = new StringBuilder();
            for (String intro : introduces) {
                intro = intro.replace("<br />", "\r\n").replaceAll("<[^>]*>", "");
                sb.append(intro.trim()).append("\r\n");
            }
            String introduce  = sb.toString().trim();
            page.putField("introduce", introduce);
            page.putField("url", url);
            page.putField("hrefs", html.xpath("//td[@style='WORD-WRAP: break-word']/a/@href").all());
            page.putField("time", time);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override public Site getSite() {
        return site;
    }
}
