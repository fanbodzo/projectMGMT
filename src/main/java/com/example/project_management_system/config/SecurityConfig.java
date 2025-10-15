package com.example.project_management_system.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        //zmiana juz nie wsyzstko jets tak o dostepne dalem autoryzacje
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // Rejestracja musi być publiczna
                        .requestMatchers("/api/projects/**", "/api/tasks/**", "/api/comments/**").authenticated() // Reszta API wymaga logowania
                        .anyRequest().permitAll() // Inne, nieznane ścieżki (np. "/") są publiczne
                )
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf.disable()); // Wyłączamy CSRF dla REST API

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}