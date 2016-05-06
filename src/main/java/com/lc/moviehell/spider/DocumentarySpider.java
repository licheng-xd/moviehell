package com.lc.moviehell.spider;

import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 纪录片爬虫
 *
 * Created by lc on 16/5/6.
 */
public class DocumentarySpider implements PageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(
        DocumentarySpider.class);

    private Site site = Site.me()
        .setRetryTimes(3).setSleepTime(1000).setUseGzip(true);

    @Override public void process(Page page) {
        try {
            String url = page.getUrl().toString();

            Html html = page.getHtml();
            String name = html.xpath("//div[@class='contentinfo']/h1/a/text()").get();
            String img = html.xpath("//div[@class='contentinfo']/div[@id='text']/p/img/@src").get();
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
            String hrefs = array.toJSONString();
            if (name == null || array.size() == 0) {
                page.setSkip(true);
                List<String> urls = html.xpath("//div[@class='listimg']/a/@href").all();
                page.addTargetRequests(urls);
                return;
            }

            List<String> intros = html.xpath("//div[@class='contentinfo']/div[@id='text']/p").all();
            StringBuilder sb = new StringBuilder();
            for (String intro : intros) {
                if (intro.contains("【下载地址"))
                    continue;
                intro = intro.replace("<br />", "\r\n").replaceAll("<[^>]*>", "");
                sb.append(intro.trim()).append("\r\n");
            }
            String intro = sb.toString().trim();

            page.putField("url", url);
            page.putField("name", name);
            page.putField("img", img);
            page.putField("hrefs", hrefs);
            page.putField("intro", intro);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override public Site getSite() {
        return site;
    }
}
