package com.project.service.health;

import com.ibm.as400.access.AS400;

public class Jt400HealthCheckStrategy implements HealthCheckStrategy {

    private final String systemCode;
    private final String host;
    private final String user;
    private final String password;

    public Jt400HealthCheckStrategy(String systemCode, String host, String user, String password) {
        this.systemCode = systemCode;
        this.host = host;
        this.user = user;
        this.password = password;
    }

    @Override
    public HealthCheckResult checkHealth() {
        long start = System.currentTimeMillis();
        try (AS400 system = new AS400(host, user, password.toCharArray())) {
            // Using CENTRAL service as recommended in research
            system.connectService(AS400.CENTRAL);
            if (system.isConnectionAlive(AS400.CENTRAL)) {
                long duration = System.currentTimeMillis() - start;
                return HealthCheckResult.success(duration);
            } else {
                long duration = System.currentTimeMillis() - start;
                return HealthCheckResult.failure("Connection not alive", duration);
            }
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - start;
            return HealthCheckResult.failure(e.getMessage(), duration);
        }
    }

    @Override
    public String getSystemCode() {
        return systemCode;
    }
}
