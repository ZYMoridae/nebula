package com.jz.nebula.service;

import com.jz.nebula.entity.News;
import com.jz.nebula.service.channel.NewsSearchParameter;
import com.jz.nebula.service.channel.bytedance.ByteDanceInterface;
import com.jz.nebula.service.channel.bytedance.SearchParameter;
import com.jz.nebula.service.channel.bytedance.ToutiaoCategory;
import com.jz.nebula.service.channel.netease.NeteaseCategory;
import com.jz.nebula.service.channel.netease.NeteaseInterface;
import com.jz.nebula.service.elasticsearch.ElasticSearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * The news feeding service mainly for providing news
 *
 * @author yuezhou
 * @version 1.0
 * @since 2019-10-10
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
public class NewsFeedingService {
    private final Logger logger = LogManager.getLogger(NewsFeedingService.class);
    @Autowired
    ElasticSearchService elasticSearchService;
    private int threadPoolCount = 4;

    public int getThreadPoolCount() {
        return threadPoolCount;
    }

    public void setThreadPoolCount(int threadPoolCount) {
        this.threadPoolCount = threadPoolCount;
    }

    /**
     * Get news from search parameter
     *
     * @param newsSearchParameter
     *
     * @return
     */
    public List<News> getNews(NewsSearchParameter newsSearchParameter) {
        long startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(this.threadPoolCount);
        List<FutureTask> taskList = new ArrayList<>();

        ByteDanceInterface byteDanceInterface = getByteDanceInterface(newsSearchParameter);

        // TODO: Create one to many category mapping
        NeteaseInterface neteaseInterface = getNeteaseInterace(newsSearchParameter);

        // Create future task
        FutureTask byteDanceTask = new FutureTask(byteDanceInterface);
        FutureTask neteaseTask = new FutureTask(neteaseInterface);

        logger.info("newsFeedingService::get news start");

        // Toutiao API
        taskList.add(byteDanceTask);
        executorService.execute(byteDanceTask);

        // Netease API
        taskList.add(neteaseTask);
        executorService.execute(neteaseTask);

        List<News> newsList = new ArrayList<>();

        for (FutureTask futureTask : taskList) {
            try {
                newsList.addAll((ArrayList<News>) futureTask.get());
            } catch (Exception e) {

            }
        }

        logger.info("newsFeedingService::get news finish, time taken " + (System.currentTimeMillis() - startTime));

        executorService.shutdown();

        // Create news index in elastic search
        for (News news : newsList
        ) {
            elasticSearchService.createNewsIndex(news);
        }

        return newsList;
    }

    /**
     * Get Netease interface
     *
     * @param newsSearchParameter
     *
     * @return
     */
    private NeteaseInterface getNeteaseInterace(NewsSearchParameter newsSearchParameter) {
        NeteaseInterface neteaseInterface;
        com.jz.nebula.service.channel.netease.SearchParameter searchParameter = new com.jz.nebula.service.channel.netease.SearchParameter();
        searchParameter.setCategory(NeteaseCategory.CATG_TECH);

        neteaseInterface = new NeteaseInterface("https://3g.163.com/touch/reconstruct/article/list/", searchParameter);

        return neteaseInterface;
    }

    /**
     * Get Bytedance interface
     *
     * @param newsSearchParameter
     *
     * @return
     */
    private ByteDanceInterface getByteDanceInterface(NewsSearchParameter newsSearchParameter) {
        ByteDanceInterface byteDanceInterface;
        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setCategory(ToutiaoCategory.CATG_HOT);

        byteDanceInterface = new ByteDanceInterface("https://www.toutiao.com/api/pc/feed/", searchParameter);
        byteDanceInterface.setReturnRawFormat(false);
        return byteDanceInterface;
    }

}
