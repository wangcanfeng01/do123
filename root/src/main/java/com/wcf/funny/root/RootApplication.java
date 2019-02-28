package com.wcf.funny.root;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.wcf.funny")
@MapperScan("com.wcf.funny.**.mapper")
public class RootApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(RootApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(RootApplication.class);
	}

}