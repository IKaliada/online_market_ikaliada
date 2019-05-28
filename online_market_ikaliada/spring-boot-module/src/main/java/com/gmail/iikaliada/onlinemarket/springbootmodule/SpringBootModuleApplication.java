package com.gmail.iikaliada.onlinemarket.springbootmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {
        "com.gmail.iikaliada.onlinemarket.repositorymodule",
        "com.gmail.iikaliada.onlinemarket.springbootmodule",
        "com.gmail.iikaliada.onlinemarket.servicemodule"},
        exclude = {UserDetailsServiceAutoConfiguration.class,
                LiquibaseAutoConfiguration.class}
)
@EntityScan(basePackages = "com.gmail.iikaliada.onlinemarket.repositorymodule.model")
@PropertySource({"classpath:mail.properties", "classpath:validation.properties"})
public class SpringBootModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootModuleApplication.class, args);
    }
}
