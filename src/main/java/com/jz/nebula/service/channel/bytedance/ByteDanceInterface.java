package com.jz.nebula.service.channel.bytedance;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jz.nebula.entity.News;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * Ref: https://www.jianshu.com/p/05a479e7ff8f
 * <p>
 * Implementation might be different based on the response structure
 */
public class ByteDanceInterface implements Callable {

    private final Logger logger = LogManager.getLogger(ByteDanceInterface.class);

    private String pageEndpoint;

    private SearchParameter searchParameter;

    private boolean isReturnRawFormat;


    public ByteDanceInterface(String pageEndpoint, SearchParameter searchParameter) {
        this.pageEndpoint = pageEndpoint;
        this.searchParameter = searchParameter;
        this.isReturnRawFormat = false;
    }

    public String getPageEndpoint() {
        return pageEndpoint;
    }

    public void setPageEndpoint(String pageEndpoint) {
        this.pageEndpoint = pageEndpoint;
    }

    public SearchParameter getSearchParameter() {
        return searchParameter;
    }

    public void setSearchParameter(SearchParameter searchParameter) {
        this.searchParameter = searchParameter;
    }

    public boolean isReturnRawFormat() {
        return isReturnRawFormat;
    }

    public void setReturnRawFormat(boolean returnRawFormat) {
        isReturnRawFormat = returnRawFormat;
    }

    @Override
    public Object call() throws Exception {

        List<News> newsList = new ArrayList<>();

        HttpRequestFactory requestFactory
                = new NetHttpTransport().createRequestFactory();
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(this.buildSearchUrl()));
        String rawResponse = request.execute().parseAsString();

        logger.debug("byteDance:: [{}]", rawResponse);

//        Gson gson = new GsonBuilder()
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                .create();

        Gson gson = new Gson();


        ToutiaoResponse toutiaoResponse = gson.fromJson(rawResponse, ToutiaoResponse.class);

        toutiaoResponse.sanitizeUrl();

        List<ToutiaoNews> toutiaoNewsList = toutiaoResponse.getData();

        if (this.isReturnRawFormat) {
            return toutiaoNewsList;
        }

        for (ToutiaoNews toutiaoNews : toutiaoNewsList) {
            News news = new News();
            news.setTitle(toutiaoNews.getTitle());
            news.setImgUrl(toutiaoNews.getImageUrl());
            news.setIntro(toutiaoNews.getIntro());
            newsList.add(news);
        }

        return newsList;
    }

    /**
     * @return
     */
    private String buildSearchUrl() {
        String url = this.pageEndpoint;

        if (!Objects.isNull(this.searchParameter)) {
            url += "?category=" + this.searchParameter.getCategory();
        }

        return url;
    }

}
