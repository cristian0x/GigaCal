package com.gigacal.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.gigacal.repository")
public class PersistenceConfig {
}
