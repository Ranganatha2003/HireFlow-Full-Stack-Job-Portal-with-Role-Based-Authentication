package com.ranga.hireflow.config;

import com.ranga.hireflow.service.CustomUserDetailsService;
import com.ranga.hireflow.util.JwtFilter;
import org.springframework.http.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

        .requestMatchers("/api/users/login").permitAll()
        .requestMatchers("/api/users/register").permitAll()

        .requestMatchers("/api/jobs/**").permitAll()

        // 👑 ADMIN
        .requestMatchers(HttpMethod.POST, "/api/admin/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.PUT, "/api/admin/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/admin/**").hasRole("ADMIN")
        .requestMatchers("/api/admin/**").hasRole("ADMIN")

        .anyRequest().authenticated()
)


                // ✅ AUTH PROVIDER
                .authenticationProvider(authenticationProvider())

                // ✅ JWT FILTER
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
public UserDetailsService userDetailsService() {
    return customUserDetailsService;
}

@Bean
public AuthenticationProvider authenticationProvider() {

    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(customUserDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
}

}
