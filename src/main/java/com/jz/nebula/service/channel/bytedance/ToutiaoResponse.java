package com.jz.nebula.service.channel.bytedance;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ToutiaoResponse {
    private boolean hasMore;

    private String message;

    private List<ToutiaoNews> data;

    /**
     * Sanitize the url
     */
    public void sanitizeUrl() {
        UrlValidator urlValidator = new UrlValidator();

        for (ToutiaoNews toutiaoNews : this.data) {
            if (!urlValidator.isValid(toutiaoNews.getImageUrl())) {
                toutiaoNews.setImageUrl("https:" + toutiaoNews.getImageUrl());
            }

            if (!urlValidator.isValid(toutiaoNews.getMediaAvatarUrl())) {
                toutiaoNews.setMediaAvatarUrl("https:" + toutiaoNews.getMediaAvatarUrl());
            }

            if (!Objects.isNull(toutiaoNews.getImageList())) {
                for (ToutiaoImageItem toutiaoImageItem : toutiaoNews.getImageList()) {
                    if (!urlValidator.isValid(toutiaoImageItem.getUrl())) {
                        toutiaoImageItem.setUrl("https:" + toutiaoImageItem.getUrl());
                    }
                }
            }
        }
    }

}

