package com.jz.nebula.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
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
}
