package com.fintec.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.fintec.auth.MethodSecurityConfig;
import com.fintec.auth.ResourceServerConfiguration;
import com.fintec.configuration.ApplicationConfiguration;
import com.fintec.configuration.ApplicationInitializer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAutoConfiguration(exclude = { OAuth2AutoConfiguration.class,DataSourceAutoConfiguration.class,JpaRepositoriesAutoConfiguration.class, SecurityAutoConfiguration.class })
@SpringBootApplication
@EnableSwagger2
@EnableWebSecurity
// @EnableHystrixDashboard
// @EnableHystrix
@ComponentScan(basePackages = "com.fintec")
@EnableGlobalMethodSecurity(prePostEnabled = true, /* securedEnabled = true, */proxyTargetClass = true)
@Import({ ResourceServerConfiguration.class, MethodSecurityConfig.class,ApplicationConfiguration.class })
public class FintecApplication extends ApplicationInitializer{
	public static void main(String[] args) {
		SpringApplication.run(FintecApplication.class, args);
	}
}
