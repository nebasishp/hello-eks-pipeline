package com.example.config;

import com.example.service.RdsSecretService;
import com.fasterxml.jackson.databind.JsonNode;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RdsDataSourceConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Bean
    public DataSource dataSource(RdsSecretService secretService) {

        JsonNode secret = secretService.getSecret();

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(secret.get("username").asText());
        dataSource.setPassword(secret.get("password").asText());

        return dataSource;
    }
}