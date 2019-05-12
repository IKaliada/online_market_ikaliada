package com.gmail.iikaliada.onlinemarket.springbootmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {
        "com.gmail.iikaliada.onlinemarket.repositorymodule",
        "com.gmail.iikaliada.onlinemarket.springbootmodule",
        "com.gmail.iikaliada.onlinemarket.servicemodule"},
        exclude = UserDetailsServiceAutoConfiguration.class
)
public class SpringBootModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootModuleApplication.class, args);
    }
}
