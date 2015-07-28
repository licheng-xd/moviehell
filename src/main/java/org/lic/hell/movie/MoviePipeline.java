package org.lic.hell.movie;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by lc on 15/7/24.
 */
public class MoviePipeline implements Pipeline {
    @Override public void process(ResultItems resultItems, Task task) {
        String name = resultItems.get("name");
        String img = resultItems.get("img");
        String href = resultItems.get("href");
        String introduce = resultItems.get("introduce");
        System.out.println(name);
        if (img == null || href == null || introduce == null) {
            System.out.println("invalid data");
            return;
        }
        Movie movie = new Movie(name, img, href, introduce);
        LocalCache.getInstance().addLast(movie);
    }
}
