package com.project.backend_project.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                        // New lambda syntax for authorization
                        .csrf(csrf -> csrf.disable())

                        // New lambda syntax for authorization
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                    "/api/v1/staffs/login**",
                                    // "/api/v1/customers/**",
                                    "/api/v1/staffs/refresh-token",
                                    "/api/v1/staffs/logout",
                                    "/v2/api-docs",
                                    "/v3/api-docs",
                                    "/v3/api-docs/**",
                                    "/swagger-resources",
                                    "/swagger-resources/**",
                                    "/configuration/ui",
                                    "/configuration/security",
                                    "/swagger-ui/**",
                                    "/webjars/**",
                                    "/swagger-ui.html").permitAll()
                                .anyRequest()
                                .authenticated())

                        // new Lambda syntax for session manangement
                        .sessionManagement(
                                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )

                        // set authentication provider
                        .authenticationProvider(authenticationProvider)

                        // register JWT filter
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                        .logout(logout -> logout
                            .logoutUrl("/api/v1/staffs/logout")
                            .addLogoutHandler(logoutHandler)
                            .logoutSuccessHandler(
                                (request, response, authentication) ->
                                SecurityContextHolder.clearContext()
                            )
                        );

        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}


