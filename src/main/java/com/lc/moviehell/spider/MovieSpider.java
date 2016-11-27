package com.lc.moviehell.spider;

import com.alibaba.fastjson.JSONArray;
import com.lc.moviehell.service.IMovieService;
import com.lc.moviehell.service.impl.MovieServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 电影爬虫
 *
 * Created by lc on 15/7/22.
 */
public class MovieSpider implements PageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(
        MovieSpider.class);

    public MovieSpider() {
    }

    private Site site = Site.me()
        .setRetryTimes(10).setSleepTime(2000).setUseGzip(true);

    @Override
    public void process(Page page) {
        try {
            Html html = page.getHtml();
            String name = html.xpath("//div[@id='main']/div[@class='col6']/div[@class=box]/h1/text()").get();
            String img = html.xpath("//div[@id='main']/div[@class='col6']/div[@class=box]/div[@id='endText']/p/img/@src").get();
            JSONArray array = new JSONArray();
            Html h = Html.create(page.getRawText());
            List<String> tds = h.xpath("//table/tbody//td[@bgcolor='#ffffbb']").all();
            for (String td : tds) {
                if (td.contains("</a>")) {
                    String href = Html.create(td).xpath("//a/@href").get();
                    if (!StringUtils.isEmpty(href)) {
                        String hrefName = td.replaceAll("<[^>]*>", "");
                        array.add(hrefName);
                        array.add(href);
                    }
                }
            }

            if (name == null || array.size() == 0) {
                page.setSkip(true);
                List<String> urls = page.getHtml().xpath("//ul[@class='list']/li/a/@href").all();
                System.out.println(urls.toString());
                page.addTargetRequests(urls);
                return;
            }

            String hrefs = array.toJSONString();
            List<String> intros = html.xpath("//div[@id='main']/div[@class='col6']/div[@class=box]/div[@id='endText']/p").all();
            StringBuilder sb = new StringBuilder();
            for (String intro : intros) {
                if (intro.contains("【下载地址"))
                    continue;
                intro = intro.replace("<br />", "\r\n").replaceAll("<[^>]*>", "");
                sb.append(intro.trim()).append("\r\n");
            }
            String intro = sb.toString().trim();

            page.putField("url", page.getUrl());
            page.putField("name", name);
            page.putField("img", img);
            page.putField("hrefs", hrefs);
            page.putField("intro", intro);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        IMovieService movieService = new MovieServiceImpl();
        Spider.create(new MovieSpider())
            .addUrl("http://www.6vhao.com/dy/2016-11-26/YJMDL.html")
            .addPipeline(new MoviePipeline(movieService))
            .thread(1)
            .run();
    }
}
