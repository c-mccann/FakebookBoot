package com.carlmccann2.fakebookboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{


    // TODO: wrote classpath:/static/js or css rather than classpath:/resources/static/js
    // TODO: below and in application.yml. fixed it. commented both to see which one actually worked
    // TODO: this or static locations in application.yml. but it still worked without either
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
//        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
//        super.addResourceHandlers(registry);
//
//    }
}
