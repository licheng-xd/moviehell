package com.lc.moviehell.spider;

import com.lc.moviehell.service.impl.P1080ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lc on 16/9/6.
 */
public class P1080Spider implements PageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(P1080Spider.class);

    private Site site = Site.me()
        .setRetryTimes(3).setSleepTime(1000).setUseGzip(true);

    private static String suffix = "- 高清Mp4吧-免费高清电影资源下载 - Powered by Mp4Ba.Com";

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void process(Page page) {
        try {
            String url = page.getUrl().toString();
            Html html = page.getHtml();
            if (html.xpath("//div[@class='intro']").all().size() == 0) {
                page.setSkip(true);
                List<String> urls = html.xpath(
                    "//tbody[@id='data_list']//td//a/@href").regex(".*hash.*").all();
                page.addTargetRequests(urls);
                return;
            }

            String title = html.xpath("//title/text()").get();
            String name = title.substring(0, title.length() - suffix.length());

            String image = html.xpath("//div[@class='intro']/img/@src").toString();

            List<String> introduces = html.xpath("//div[@class='intro']").all();
            StringBuilder sb = new StringBuilder();
            for (String intro : introduces) {
                intro = intro.replaceAll("<span(.*)</span>", "").replace("<br />", "\r\n").replaceAll("<[^>]*>", "");
                sb.append(intro.trim()).append("\r\n");
            }
            String introduce  = sb.toString().trim();

            String href = html.xpath("//a[@id='download']/@href").get();

            String time = format.format(new Date());
            List<String> basics = html.xpath("//div[@class='basic_info']/p/text()").all();
            for (String basic : basics) {
                if (basic.contains("发布时间")) {
                    time = basic.split(" ")[1];
                    break;
                }
            }

            page.putField("name", name);
            page.putField("time", time);
            page.putField("img", image);
            page.putField("introduce", introduce);
            page.putField("url", url);
            page.putField("href", href);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new P1080Spider())
            .addUrl("http://www.mp4ba.com/index.php?page=2s")
            .addPipeline(new P1080Pipeline(new P1080ServiceImpl()))
            .thread(1)
            .run();
    }
}
