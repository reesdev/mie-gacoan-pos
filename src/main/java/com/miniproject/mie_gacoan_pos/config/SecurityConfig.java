package com.miniproject.mie_gacoan_pos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")

                        .requestMatchers("/sales/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        return http.build();
}
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User
                .withUsername("admin")
                .password("{noop}12345")
                .roles("ADMIN")
                .build();

        UserDetails user = User
                .withUsername("user")
                .password("{noop}12345")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}