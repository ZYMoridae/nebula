package com.jz.nebula.service.channel.netease;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import com.jz.nebula.entity.News;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


/**
 * Refer: https://www.jianshu.com/p/c54e25349b77
 */
public class NeteaseInterface implements Callable {

    private final Logger logger = LogManager.getLogger(NeteaseInterface.class);

    private String pageEndpoint;

    private String category;

    private boolean isReturnRawFormat;

    private SearchParameter searchParameter;

    public NeteaseInterface(String pageEndpoint, SearchParameter searchParameter) {
        this.pageEndpoint = pageEndpoint;
        this.isReturnRawFormat = false;
        this.searchParameter = searchParameter;
    }

    public SearchParameter getSearchParameter() {
        return searchParameter;
    }

    public void setSearchParameter(SearchParameter searchParameter) {
        this.searchParameter = searchParameter;
    }

    public String getPageEndpoint() {
        return pageEndpoint;
    }

    public void setPageEndpoint(String pageEndpoint) {
        this.pageEndpoint = pageEndpoint;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(this.buildSearchUrl(0, 10)));
        String rawResponse = request.execute().parseAsString();

        // Remove function name
        rawResponse = rawResponse.replace("artiList(", "");
        rawResponse = rawResponse.substring(0, rawResponse.length() - 1);


        logger.debug("neteaseInterfaec:: [{}]", rawResponse);

//        Gson gson = new GsonBuilder()
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                .create();

        Gson gson = new Gson();


        NeteaseResponse neteaseResponse = gson.fromJson(rawResponse, NeteaseResponse.class);

//        toutiaoResponse.sanitizeUrl();

        List<NeteaseNewsItem> neteaseNewsItemList = neteaseResponse.getTechResonse();

        if (isReturnRawFormat) {
            return neteaseNewsItemList;
        }

        for (NeteaseNewsItem neteaseNewsItem : neteaseNewsItemList) {
            News news = new News();
            news.setTitle(neteaseNewsItem.getTitle());
            news.setImgUrl(neteaseNewsItem.getImageUrl());
            news.setIntro(neteaseNewsItem.getIntro());
            newsList.add(news);
        }

        return newsList;
    }


    /**
     * Build search url based on offset and count
     *
     * @param startOffset
     * @param count
     * @return
     */
    private String buildSearchUrl(int startOffset, int count) {
        String url = this.pageEndpoint + this.searchParameter.getCategory() + "/" + startOffset + "-" + count + ".html";

        return url;
    }
}
