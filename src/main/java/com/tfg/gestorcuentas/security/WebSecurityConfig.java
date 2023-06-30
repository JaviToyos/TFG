package com.tfg.gestorcuentas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public JWTAuthorizationFilter filtroAutenticacionJWT() {
        return new JWTAuthorizationFilter();
    }

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .addFilterAfter(filtroAutenticacionJWT(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests().requestMatchers("/login").permitAll()
                .requestMatchers("/registro").permitAll()
                .requestMatchers("/login/passBackUp").permitAll()
                .requestMatchers("/datosPersonales/update").permitAll()
                .requestMatchers("/nordigen/access").permitAll()
                .requestMatchers("/nordigen/availableBanks").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }
}