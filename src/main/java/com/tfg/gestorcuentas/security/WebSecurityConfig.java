package com.tfg.gestorcuentas.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public JWTAuthorizationFilter filtroAutenticacionJWT() {
        return new JWTAuthorizationFilter();
    }

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(final HttpServletRequest request) {
                return new CorsConfiguration().applyPermitDefaultValues();
            }
        });
        http.csrf().disable()
                .addFilterAfter(filtroAutenticacionJWT(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests().requestMatchers("/login").permitAll()
                .requestMatchers("/registro").permitAll()
                .requestMatchers("/login/passBackUp").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }
}