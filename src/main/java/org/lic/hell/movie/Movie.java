package org.lic.hell.movie;

/**
 * Created by lc on 15/7/24.
 */
public class Movie {
    private String name;

    private String img;

    private String href;

    private String introduce;

    public Movie() {
    }

    public Movie(String name, String img, String href, String introduce) {
        this.name = name;
        this.img = img;
        this.href = href;
        this.introduce = introduce;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
