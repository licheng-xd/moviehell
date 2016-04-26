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
            // 初始化美剧频道
            String url = page.getUrl().toString();
            Html html = page.getHtml();
            String name = html.xpath("//div[@class='title_all']/h1/font/text()").toString();
            String image = html.xpath("//span[@style='FONT-SIZE: 12px']/p/img/@src").toString();
            if (name == null || image == null) {
                page.setSkip(true);
                List<String> urls = html.xpath(
                    "//div[@class='co_area2']//td//a[@class='ulink']/@href").all();
                page.addTargetRequests(urls);
                return;
            }
            page.putField("name", name);
            page.putField("img", image);
            List<String> introduces = html.xpath("//span[@style='FONT-SIZE: 12px']/p").all();
            StringBuilder sb = new StringBuilder();
            for (String intro : introduces) {
                intro = intro.replace("<br />", "\r\n").replaceAll("<[^>]*>", "");
                sb.append(intro.trim()).append("\r\n");
            }
            String introduce  = sb.toString().trim();
            page.putField("introduce", introduce);
            page.putField("url", url);
            page.putField("hrefs", html.xpath("//td[@style='WORD-WRAP: break-word']/a/@href").all());
            String timeTmp = html.xpath("//div[@class='co_content8']/ul/text()").toString();
            String time = null;
            Matcher m = RegexUtil.timePattern.matcher(timeTmp);
            if (m.find()) {
                time = m.group();
            }
            page.putField("time", time);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
