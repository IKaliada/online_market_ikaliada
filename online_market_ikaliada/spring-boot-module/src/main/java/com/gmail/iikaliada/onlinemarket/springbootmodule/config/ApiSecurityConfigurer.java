package com.gmail.iikaliada.onlinemarket.springbootmodule.config;

import com.gmail.iikaliada.onlinemarket.springbootmodule.handler.ApiAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.SECURE_API_AUTHORITY_CONSTANT;

@Configuration
@Order(1)
public class ApiSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public ApiSecurityConfigurer(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.antMatcher("/api/v1/private/users/**").antMatcher("/api/v1/articles/**")
                .authorizeRequests()
                .anyRequest()
                .hasRole(SECURE_API_AUTHORITY_CONSTANT)
                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(apiAccessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public AccessDeniedHandler apiAccessDeniedHandler() {
        return new ApiAccessDeniedHandler();
    }
}
