package com.lc.moviehell.spider;

import com.lc.moviehell.dao.domain.Movie;
import com.lc.moviehell.service.IMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by lc on 15/7/24.
 */
public class MoviePipeline implements Pipeline {
    private static final Logger logger = LoggerFactory.getLogger(
        MoviePipeline.class);

    private IMovieService movieService;

    public MoviePipeline(IMovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void process(ResultItems result, Task task) {
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
            url = url.substring(0, url.length() - 5);
            String[] tmp = url.split("/");
            String time = tmp[tmp.length - 2];
            long id = Long.parseLong(tmp[tmp.length - 1]);
            Movie movie = new Movie();
            movie.setId(id);
            movie.setName(name);
            movie.setImg(img);
            movie.setHref(hrefs);
            movie.setIntro(intro);
            movie.setTime(time);
            movie.setCreatetime(System.currentTimeMillis());
            if (!movieService.hasMovie(id)) {
                logger.info("new movie id:{} name:{}", id, name);
                movieService.insertMovie(movie);
            } else {
                logger.debug("exist movie id:{}", id);
            }
        }
    }
}
