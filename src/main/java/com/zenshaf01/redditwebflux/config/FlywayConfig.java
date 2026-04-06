package com.zenshaf01.redditwebflux.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FlywayConfig {
    @Value("${spring.flyway.url}")
    private String url;

    @Value("${spring.flyway.user}")
    private String user;

    @Value("${spring.flyway.password}")
    private String password;

    @Value("${spring.flyway.schemas}")
    private String schemas;

    @Value("${spring.flyway.locations}")
    private String locations;

    @Value("${spring.flyway.baseline-on-migrate}")
    private boolean baselineOnMigrate;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        log.debug("Creating Flyway and migrating schema...");

        return Flyway.configure()
            .dataSource(url, user, password)
            .schemas(schemas)
            .locations(locations)
            .baselineOnMigrate(baselineOnMigrate)
            .load();
    }
}
