package org.lic.hell.movie;

import us.codecraft.webmagic.Spider;

/**
 * Created by lc on 15/7/24.
 */
public class Starter {
    public static void main(String[] args) throws InterruptedException {
        Spider.create(new MovieSpider())
            .addPipeline(new MoviePipeline())
            .addUrl("http://dytt8.net/").thread(1).run();
        new MainGui().run();
    }
}
