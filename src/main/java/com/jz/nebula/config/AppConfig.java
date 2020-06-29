package com.jz.nebula.config;


import com.jz.nebula.component.interceptor.RouteInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public RouteInterceptor routeInterceptor() {
        return new RouteInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(routeInterceptor());
        // Only intercept CMS path
//        registry.addInterceptor(new CmsRouteInterceptor()).addPathPatterns("/cms/**").excludePathPatterns("/cms/login");
    }
}
