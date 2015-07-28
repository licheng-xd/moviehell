package org.lic.hell.movie;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by lc on 15/7/22.
 */
public class MovieSpider implements PageProcessor {
    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
        .setRetryTimes(3).setSleepTime(1000).setUseGzip(true);

    @Override
    public void process(Page page) {
        String name = page.getHtml().xpath("//div[@class='title_all']/h1/font/text()").toString();
        if (name == null){
            page.setSkip(true);
            List<String> urls = page.getHtml().css("div.co_content2").links().all();
            List<String> target = urls.subList(0, 15);
            System.out.println(target.toString());
            page.addTargetRequests(target);
            return;
        }
        page.putField("name", name);
        page.putField("href", page.getHtml().xpath("//td[@style='WORD-WRAP: break-word']/a/@href").toString());
        page.putField("img", page.getHtml()
            .xpath("//span[@style='FONT-SIZE: 12px']/p/img/@src").toString());
        String introduce = page.getHtml()
            .xpath("//span[@style='FONT-SIZE: 12px']/p").replace("<p>", "").replace("</p>", "")
            .replace("<br />", "\r\n").replace("<.*>", " ").toString().trim();
        page.putField("introduce", introduce);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new MovieSpider())
            .addUrl("http://dytt8.net/")
            .addPipeline(new MoviePipeline())
            .thread(1)
            .run();
    }
}
