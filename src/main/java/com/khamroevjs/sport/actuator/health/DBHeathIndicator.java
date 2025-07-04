package com.khamroevjs.sport.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DBHeathIndicator implements HealthIndicator {

    private final DataSource dataSource;
    public DBHeathIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(1000)) {
                return Health.up()
                        .withDetail("database", "PostgreSQL")
                        .withDetail("version", conn.getMetaData().getDatabaseProductVersion())
                        .build();
            }
        } catch (SQLException ex) {
            return Health.down()
                    .withException(ex)
                    .withDetail("error", "Connection failed: " + ex.getMessage())
                    .build();
        }
        return Health.unknown().build();
    }
}
