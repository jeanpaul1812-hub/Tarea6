package com.trabajo6.security;

import com.trabajo6.jpa.UserEntity;
import com.trabajo6.jpa.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/login", "/autos", "/autos/**", "/usuarios", "/usuarios/**"))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/autos/**").authenticated()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll())
            .httpBasic(httpBasic -> { });
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository repository) {
        return username -> {
            UserEntity user = repository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            return User.withUsername(user.getUsuario())
                .password("{noop}" + user.getClave())
                .roles(user.isAdministrador() ? "ADMIN" : "USER")
                .build();
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
