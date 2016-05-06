package com.lc.moviehell.spider;

import com.lc.moviehell.dao.domain.Documentary;
import com.lc.moviehell.service.IDocumentaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by lc on 16/5/6.
 */
public class DocumentaryPipeline implements Pipeline {
    private static final Logger logger = LoggerFactory.getLogger(
        DocumentaryPipeline.class);

    private IDocumentaryService documentaryService;

    public DocumentaryPipeline(IDocumentaryService documentaryService) {
        this.documentaryService = documentaryService;
    }

    @Override public void process(ResultItems result, Task task) {
        String name = result.get("name");
        String img = result.get("img");
        String hrefs = result.get("hrefs");
        String intro = result.get("intro");
        String url = result.get("url");
        if (img == null) {
            img = "http://nos-yx.netease.com/yixinpublic/moviehell-404.jpg";
        }
        if (name == null || hrefs == null || intro == null || url == null) {
            logger.warn("invalid result: {}", result);
        } else {
            long now = System.currentTimeMillis();
            url = url.substring(0, url.length() - 5);
            String[] tmp = url.split("/");
            String time = tmp[tmp.length - 2];
            String id = tmp[tmp.length - 1];
            Documentary doc = documentaryService.getById(id);
            if (doc == null) {
                doc = new Documentary();
                doc.setId(id);
                doc.setName(name);
                doc.setImg(img);
                doc.setIntro(intro);
                doc.setHrefs(hrefs);
                doc.setTime(time);
                doc.setUrl(url);
                doc.setCreatetime(now);
                doc.setUpdatetime(now);
                documentaryService.insert(doc);
                logger.info("create new documentary {}, {}", id, name);
            } else if (!time.equals(doc.getTime())) {
                doc.setName(name);
                doc.setImg(img);
                doc.setIntro(intro);
                doc.setHrefs(hrefs);
                doc.setTime(time);
                doc.setUrl(url);
                doc.setUpdatetime(now);
                documentaryService.update(doc);
            } else {
                logger.info("duplicate documentary id:{}", id);
            }
        }
    }
}
