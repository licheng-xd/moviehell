package com.lc.moviehell.spider;

import com.alibaba.fastjson.JSON;
import com.lc.moviehell.dao.domain.Ustv;
import com.lc.moviehell.service.IUstvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lc on 16/4/25.
 */
public class UstvPipeline implements Pipeline {
    private static final Logger logger = LoggerFactory.getLogger(UstvPipeline.class);

    private IUstvService ustvService;

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public UstvPipeline(IUstvService ustvService) {
        this.ustvService = ustvService;
    }

    @Override public void process(ResultItems result, Task task) {
        try {
            String name = result.get("name");
            String img = result.get("img");
            List<String> hrefs = result.get("hrefs");
            String intro = result.get("intro");
            String url = result.get("url");
            if (img == null) {
                img = "http://nos-yx.netease.com/yixinpublic/moviehell-404.jpg";
            }
            if (name == null || hrefs == null || intro == null || url == null) {
                logger.warn("invalid result: {}", result);
            } else {
                url = url.substring(0, url.length() - 5);
                String[] tmp = url.split("/");
                String id = tmp[tmp.length - 1];
                Ustv ustv = ustvService.getUstvById(id);
                long now = System.currentTimeMillis();
                if (ustv == null) {
                    // 创建
                    String time = tmp[tmp.length - 2];
                    ustv = new Ustv();
                    ustv.setId(id);
                    ustv.setName(name);
                    ustv.setImg(img);
                    ustv.setHrefs(JSON.toJSONString(hrefs));
                    ustv.setSize(hrefs.size());
                    ustv.setIntro(intro);
                    ustv.setTime(time);
                    ustv.setUpdatetime(now);
                    ustv.setCreatetime(now);
                    ustvService.insertUstv(ustv);
                    logger.info("create new ustv {}, {}", id, name);
                } else {
                    // 更新
                    String time = format.format(new Date());
                    if (ustv.getSize() < hrefs.size()) {
                        ustv.setHrefs(JSON.toJSONString(hrefs));
                        ustv.setName(name);
                        ustv.setSize(hrefs.size());
                        ustv.setTime(time);
                        ustv.setUpdatetime(now);
                        ustvService.updateUstv(ustv);
                        logger.info("update ustv {}, {}", id, name);
                    } else {
                        logger.warn(
                            "ustv size not modify id:{} oldsize:{} newsize:{}",
                            id, ustv.getSize(), hrefs.size());
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
