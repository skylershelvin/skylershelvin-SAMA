package org.skylershelvin.SAMABackened.API.security;

import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration

public class WebSecurityConfig {

    private JwtRequestFilter jwtRequestFilter;

    public WebSecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests()
                // all pages I want to access without authentication
                .requestMatchers("/product", "/auth/register", "/auth/login",
                        "/auth/forgot", "/auth/reset", "/websocket", "/websocket/**").permitAll()
                .anyRequest().authenticated();
        return http.build();
        }
    }
