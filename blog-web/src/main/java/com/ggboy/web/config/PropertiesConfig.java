package com.ggboy.web.config;

import com.ggboy.common.constant.PropertiesConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:config/static.properties")
public class PropertiesConfig {

    @Value("${default.pageSize}")
    private Integer pageSize;
    @Value("${default.blog.list.pageSize}")
    private Integer blogListPageSize;
    @Value("${default.blog.list.showPageButtonSize}")
    private Integer blogListShowPageButtonSize;
    @PostConstruct
    public void setProperties() {
        PropertiesConstant.init(pageSize, blogListPageSize, blogListShowPageButtonSize);
    }
}