package com.ggboy.web.config;

import com.ggboy.web.converter.DateConverter;
import com.ggboy.web.converter.EnumConverterFactory;
import com.ggboy.web.interceptor.LoginInterceptor;
import com.ggboy.web.interceptor.VerifyInterceptor;
import com.jfinal.template.ext.spring.JFinalViewResolver;
import com.jfinal.template.source.ClassPathSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/console/**");
                registry.addInterceptor(new VerifyInterceptor()).addPathPatterns("/**");
//                registry.addInterceptor(new BaseInterceptor()).addPathPatterns("/**");
            }

            public void addFormatters(FormatterRegistry registry) {
                registry.addConverterFactory(new EnumConverterFactory());
                registry.addConverter(new DateConverter());
            }

            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
                registry.addResourceHandler("/image/**").addResourceLocations("file:///media/app/blog/image/");
            }
        };
    }

    @Bean
    public JFinalViewResolver enjoy() {
        return new JFinalViewResolver(){{
            setDevMode(true);
            setSessionInView(true);
            setSourceFactory(new ClassPathSourceFactory());
            setOrder(1);
            setContentType("text/html; charset=utf-8");
            setPrefix("/view/");
            setSuffix(".html");
            setCache(false);
        }};
    }
}