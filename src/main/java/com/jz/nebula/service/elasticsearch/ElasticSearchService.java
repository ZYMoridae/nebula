package com.jz.nebula.service.elasticsearch;

import com.google.gson.Gson;
import com.jz.nebula.entity.News;
import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ElasticSearchService {
    private final Logger logger = LogManager.getLogger(ElasticSearchService.class);

    private RestHighLevelClient client;

    private HttpHost[] httpHosts;

    @Autowired
    public ElasticSearchService() {
        this.client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    public ElasticSearchService(HttpHost[] httpHosts) {
        this.httpHosts = httpHosts;
        this.client = new RestHighLevelClient(RestClient.builder(httpHosts));
    }

    public RestHighLevelClient getClient() {
        return client;
    }

    public void setClient(RestHighLevelClient client) {
        this.client = client;
    }

    public HttpHost[] getHttpHosts() {
        return httpHosts;
    }

    public void setHttpHosts(HttpHost[] httpHosts) {
        this.httpHosts = httpHosts;
    }

    /**
     * Create index for each record
     *
     * @param news
     */
    public void createNewsIndex(News news) {
        IndexRequest request = new IndexRequest("nebula");
        Gson gson = new Gson();

        // TODO: How to make sure the news only saved
        HashMap<String, String> newsHashMap = this.formatNews(news);

        if (!this.hasNews(newsHashMap.get("hash_code"))) {
            String jsonString = gson.toJson(newsHashMap);

            request.source(jsonString, XContentType.JSON);

            try {
                IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
                logger.debug("createNewsIndex::record has been added");
//                if (indexResponse.status().getStatus() != 200) {
//                    throw new Exception("es::createNewsIndex action failed");
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Check news was added or not
     *
     * @param hashCode
     * @return
     */
    public boolean hasNews(String hashCode) {
        QueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("hash_code", hashCode));
        SearchResponse searchResponse = this.searchByHash("nebula", query);

        if (searchResponse != null) {
            logger.debug("total hits: " + searchResponse.getHits().getTotalHits().value);
            return searchResponse.getHits().getTotalHits().value >= 1;
        }

        return false;
    }

    /**
     * Query Nebula index by hash code
     *
     * @param index
     * @param query
     * @return
     */
    public SearchResponse searchByHash(String index, QueryBuilder query) {

        SearchResponse searchResponse = null;

        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // Build source
        sourceBuilder.query(query);

//        sourceBuilder.from(0);
//        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);

        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchResponse;
    }

    /**
     * Format news
     *
     * @param news
     * @return
     */
    private HashMap formatNews(News news) {
        HashMap<String, String> newsHashMap = new HashMap<>();
        newsHashMap.put("title", news.getTitle());
        newsHashMap.put("image_url", news.getImgUrl());
        newsHashMap.put("intro", news.getIntro());
        newsHashMap.put("time_stamp", String.valueOf(System.currentTimeMillis()));

        String newsHashCode = DigestUtils.md5DigestAsHex(news.getTitle().getBytes());
        newsHashMap.put("hash_code", newsHashCode);

        return newsHashMap;
    }


    /**
     * Search news based search parameter
     *
     * @return
     */
    private List<News> searchNews() {
        List<News> newsList = new ArrayList<>();

        // FIXME: Add search logic here

        return newsList;
    }
}
