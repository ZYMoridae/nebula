package com.jz.nebula.controller;

import com.jz.nebula.entity.News;
import com.jz.nebula.entity.Role;
import com.jz.nebula.service.NewsFeedingService;
import com.jz.nebula.service.channel.NewsSearchParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsFeedingService newsFeedingService;

    @GetMapping("")
    public @ResponseBody
    List<News> getNews(@RequestParam String category) {
        NewsSearchParameter newsSearchParameter = new NewsSearchParameter();
        newsSearchParameter.setCategory(category);
        return newsFeedingService.getNews(newsSearchParameter);
    }

}