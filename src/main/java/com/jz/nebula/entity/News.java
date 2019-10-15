package com.jz.nebula.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class News {

    private String title;

    private String imgUrl;

    private String intro;

    public News() {

    }

    public News(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", intro='" + intro + '\'' +
                '}';
    }
}
