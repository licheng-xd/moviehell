package com.lc.moviehell.spider;

import com.alibaba.fastjson.JSON;
import com.lc.moviehell.dao.domain.Ustv;
import com.lc.moviehell.service.IUstvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Created by lc on 16/4/25.
 */
public class UstvPipeline implements Pipeline {
    private static final Logger logger = LoggerFactory.getLogger(UstvPipeline.class);

    private IUstvService ustvService;

    public UstvPipeline(IUstvService ustvService) {
        this.ustvService = ustvService;
    }

    @Override public void process(ResultItems result, Task task) {
        String name = result.get("name");
        String img = result.get("img");
        List<String> hrefs = result.get("hrefs");
        String introduce = result.get("introduce");
        String url = result.get("url");
        String time = result.get("time");
        if (img == null) {
            img = "http://nos-yx.netease.com/yixinpublic/moviehell-404.jpg";
        }
        if (name == null || hrefs == null || introduce == null || url == null) {
            logger.warn("invalid result: {}", result);
        } else {
            if (!url.contains("/tv/oumeitv")) {
                return;
            }
            if (introduce.contains("下载地址")) {
                introduce = introduce.substring(0, introduce.indexOf("下载地址") - 2);
            }
            url = url.substring(0, url.length() - 5);
            String[] tmp = url.split("/");
            long id = Long.parseLong(tmp[tmp.length - 1]);
            Ustv ustv = ustvService.getUstvById(id);
            long now = System.currentTimeMillis();
            if (ustv == null) {
                // 创建
                ustv = new Ustv();
                ustv.setId(id);
                ustv.setName(name);
                ustv.setImg(img);
                ustv.setHrefs(JSON.toJSONString(hrefs));
                ustv.setSize(hrefs.size());
                ustv.setIntro(introduce);
                ustv.setTime(time);
                ustv.setCreatetime(now);
                ustv.setCreatetime(now);
                ustvService.insertUstv(ustv);
                logger.info("create new ustv {}, {}", id, name);
            } else {
                // 更新
                if (ustv.getSize() < hrefs.size()) {
                    ustv.setHrefs(JSON.toJSONString(hrefs));
                    ustv.setName(name);
                    ustv.setSize(hrefs.size());
                    ustv.setTime(time);
                    ustv.setUpdatetime(now);
                    ustvService.updateUstv(ustv);
                    logger.info("update ustv {}, {}", id, name);
                }
                else {
                    logger.warn("ustv size not modify id:{} oldsize:{} newsize:{}", id, ustv.getSize(), hrefs.size());
                }
            }
        }
    }
}
