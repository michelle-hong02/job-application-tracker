package com.mhong.jobtracker.config;

import com.mhong.jobtracker.security.CustomUserDetailsService;
import com.mhong.jobtracker.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    // Constructor injection for required dependencies
    public SecurityConfig(JwtAuthFilter jwtAuthFilter,
                          CustomUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Main security config
     * Disable stateful features
     * Define endpoint access rules and JWT filter setup
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            // Disable CSRF (not needed for stateless JWT)
            .csrf(csrf -> csrf.disable())

            //
            .authorizeHttpRequests(auth -> auth
                //public endpoints
                .requestMatchers("/auth/**").permitAll()

                // All other endpoints require authentication
                .anyRequest().authenticated()
            )
            // Stateless session (required for JWT)
            .sessionManagement(sess ->
                sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Set custom authentication provider
            .authenticationProvider(authenticationProvider())

            // Add JWT filter before Spring Security's default filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Defining here avoids creating a new encoder every call
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /*
        Authentication provider config
        Tells Spring that when someone authenticates using username/password, use a DAO
        provider that loads users via UserDetailsService and checks passwords with BCrypt
     */
    @Bean public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(encoder());
        return provider;
    }

    /*
        Authentication manager bean
        Coordinator used during logic
        1. accepts credentials
        2. asks Spring Security, "is this user real, does this password match?"
            a. Internally calls UserDetailsService.loadUserByUsername (data loader)
            b. internally calls PasswordEncoder to verify the password
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }
}
