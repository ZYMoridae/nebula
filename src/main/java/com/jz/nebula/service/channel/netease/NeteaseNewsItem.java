package com.jz.nebula.service.channel.netease;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NeteaseNewsItem {
    private String docid;

    private String source;

    private String title;

    private String priority;

    private String url;

    @SerializedName("digest")
    private String intro;

    @SerializedName("imgsrc")
    private String imageUrl;

    private String ptime;

}
