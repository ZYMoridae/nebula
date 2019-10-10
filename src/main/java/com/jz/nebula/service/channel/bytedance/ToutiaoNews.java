package com.jz.nebula.service.channel.bytedance;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ToutiaoNews {

    @SerializedName("chinese_tag")
    private String chineseTag;

    @SerializedName("media_avatar_url")
    private String mediaAvatarUrl;

    @SerializedName("is_feed_ad")
    private boolean isIsFeedAd;

    private String title;

    @SerializedName("abstract")
    private String intro;

    private String url;

    @SerializedName("image_url")
    private String imageUrl;

    private String itemId;

    private String tag;

    private String source;

    @SerializedName("image_list")
    private List<ToutiaoImageItem> imageList;

    @SerializedName("behot_time")
    private long beHotTime;

    @SerializedName("middle_image")
    private String middleImageUrl;
}


