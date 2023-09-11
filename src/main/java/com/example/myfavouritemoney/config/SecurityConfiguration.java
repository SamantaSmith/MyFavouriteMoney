package com.example.myfavouritemoney.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http.csrf().disable().authorizeRequests()
                .requestMatchers("/v3/api-docs").permitAll()
                .requestMatchers("/configuration/ui").permitAll()
                .requestMatchers("/swagger-resources/**").permitAll()
                .requestMatchers("/configuration/security").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/swagger-ui/*").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                .requestMatchers("/v3/**").permitAll()
                .anyRequest().authenticated().and().oauth2Login();
        return http.build();
        // @formatter:on
    }
}
