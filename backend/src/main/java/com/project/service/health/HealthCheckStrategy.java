package com.project.service.health;

public interface HealthCheckStrategy {
    /**
     * Performs a health check on the external system.
     * @return HealthCheckResult containing status, latency, and error details.
     */
    HealthCheckResult checkHealth();

    /**
     * Returns the unique system code associated with this strategy.
     * @return System code (e.g., "CCR", "JT400").
     */
    String getSystemCode();
}
