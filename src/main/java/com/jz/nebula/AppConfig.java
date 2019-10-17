package com.jz.nebula;


import com.jz.nebula.interceptor.CmsRouteInterceptor;
import com.jz.nebula.interceptor.RouteInterceptor;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RouteInterceptor());
        // Only intercept CMS path
        registry.addInterceptor(new CmsRouteInterceptor()).addPathPatterns("/cms/**").excludePathPatterns("/cms/login");
    }
}
