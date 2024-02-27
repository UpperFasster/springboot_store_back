package com.UpperFasster.Magazine.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthRoleChecker customAuthRoleChecker;

    @Autowired
    public SecurityConfig(
            JWTAuthenticationFilter jwtAuthenticationFilter,
            CustomAuthRoleChecker customAuthRoleChecker
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthRoleChecker = customAuthRoleChecker;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/").permitAll()
//                        .requestMatchers("/admin").authenticated()
//                )
//                .addFilter(jwtAuthenticationFilter)
//                .addFilterAfter(customAuthRoleChecker, JWTAuthenticationFilter.class);

        return http.build();
    }
}
