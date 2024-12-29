package com.khanjan.FarmCollector.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories (basePackages = "com.khanjan.FarmCollector.repository")
public class JpaEnversConfiguration {

    @Configuration
    static class DataSourceConfig {
        @Bean
        public javax.sql.DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:h2:mem:testdb")
                    .username("sa")
                    .password("")
                    .driverClassName("org.h2.Driver")
                    .build();
        }
    }
}
