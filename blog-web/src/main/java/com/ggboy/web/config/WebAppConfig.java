package com.ggboy.web.config;

import com.ggboy.web.converter.DateConverter;
import com.ggboy.web.converter.EnumConverterFactory;
import com.ggboy.web.interceptor.PurviewInterceptor;
import com.ggboy.web.interceptor.VerifyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new PurviewInterceptor()).addPathPatterns("/member/**", "/demand/release");
                registry.addInterceptor(new VerifyInterceptor()).addPathPatterns("/**");
//                registry.addInterceptor(new BaseInterceptor()).addPathPatterns("/**");
            }

            public void addFormatters(FormatterRegistry registry) {
                registry.addConverterFactory(new EnumConverterFactory());
                registry.addConverter(new DateConverter());
            }
        };
    }
}