package org.skylershelvin.SAMABackened.API.security;

import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration

public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
                http.authorizeHttpRequests().anyRequest().permitAll();
        return http.build();
        }
    }
