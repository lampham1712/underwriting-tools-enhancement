package com.project.service.health;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JdbcHealthCheckStrategy implements HealthCheckStrategy {

    private final String systemCode;
    private final DataSource dataSource;

    // Constructor for existing DataSource bean
    public JdbcHealthCheckStrategy(String systemCode, DataSource dataSource) {
        this.systemCode = systemCode;
        this.dataSource = dataSource;
    }

    // Constructor for ad-hoc creation
    public JdbcHealthCheckStrategy(String systemCode, String url, String user, String password) {
        this.systemCode = systemCode;
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        this.dataSource = ds;
    }

    @Override
    public HealthCheckResult checkHealth() {
        long start = System.currentTimeMillis();
        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(3)) {
                long duration = System.currentTimeMillis() - start;
                return HealthCheckResult.success(duration);
            } else {
                long duration = System.currentTimeMillis() - start;
                return HealthCheckResult.failure("Connection is not valid", duration);
            }
        } catch (SQLException e) {
            long duration = System.currentTimeMillis() - start;
            return HealthCheckResult.failure(e.getMessage(), duration);
        }
    }

    @Override
    public String getSystemCode() {
        return systemCode;
    }
}
