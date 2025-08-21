package com.hakan.helpdesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Postman için CSRF kapat
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // tüm istekleri serbest bırak
                )
                .httpBasic(Customizer.withDefaults()); // şimdilik basic auth açık kalsın

        return http.build();
    }
}
