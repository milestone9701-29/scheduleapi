package com.tr.scheduleapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Auditing -> EntityListener
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } // default : 10
}
