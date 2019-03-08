package com.saturday.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.saturday.**")
@ServletComponentScan
@MapperScan("com.saturday.*.mapper")
@PropertySource("classpath:config/system.properties")
@PropertySource("classpath:config/security.properties")
@PropertySource("classpath:config/static.properties")
@EnableTransactionManagement
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}