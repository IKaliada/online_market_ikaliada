package com.gmail.iikaliada.onlinemarket.springbootmodule.config;

import com.gmail.iikaliada.onlinemarket.springbootmodule.handler.AppUrlAuthenticationSuccessHandler;
import com.gmail.iikaliada.onlinemarket.springbootmodule.handler.LoginAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.ADMIN_AUTHORITY_CONSTANT;
import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.CUSTOMER_AUTHORITY_CONSTANT;
import static com.gmail.iikaliada.onlinemarket.servicemodule.constant.AuthoritiesConstants.SELLER_AUTHORITY_CONSTANT;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public WebSecurityConfigurer(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/private/users/**", "/private/reviews/**")
                .hasAnyAuthority(ADMIN_AUTHORITY_CONSTANT)
                .antMatchers("/private/review", "/public/**", "/articles/**", "/private/items/**", "/private/orders/**")
                .hasAnyAuthority(CUSTOMER_AUTHORITY_CONSTANT)
                .antMatchers("/", "/denied", "/about", "/login")
                .permitAll()
                .antMatchers("/articles/**", "/private/items/**", "/private/orders/**")
                .hasAnyAuthority(SELLER_AUTHORITY_CONSTANT, CUSTOMER_AUTHORITY_CONSTANT)
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .successHandler(authenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AppUrlAuthenticationSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new LoginAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }
}
