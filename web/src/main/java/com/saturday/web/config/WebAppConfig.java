package com.saturday.web.config;

import com.saturday.web.converter.DateConverter;
import com.saturday.web.converter.EnumConverterFactory;
import com.saturday.web.interceptor.BaseInterceptor;
import com.saturday.web.interceptor.LoginInterceptor;
import com.saturday.web.interceptor.VerifyInterceptor;
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
            // Spring 拦截器
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new BaseInterceptor()).addPathPatterns("/**");
                registry.addInterceptor(new VerifyInterceptor()).addPathPatterns("/**");
                registry.addInterceptor(new LoginInterceptor()).addPathPatterns(new String[]{
                        "/console/**", "/user/**", "/cache/**", "/file/**"});
            }

            // Spring 参数转换器
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverterFactory(new EnumConverterFactory());
                registry.addConverter(new DateConverter());
            }

            // Spring 静态资源映射
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
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