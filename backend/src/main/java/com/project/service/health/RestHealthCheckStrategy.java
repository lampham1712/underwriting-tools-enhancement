package com.project.service.health;

import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import java.time.Duration;

public class RestHealthCheckStrategy implements HealthCheckStrategy {

    private final String systemCode;
    private final String url;
    private final RestTemplate restTemplate;

    public RestHealthCheckStrategy(String systemCode, String url, RestTemplateBuilder builder) {
        this.systemCode = systemCode;
        this.url = url;
        this.restTemplate = builder.build();
    }

    @Override
    public HealthCheckResult checkHealth() {
        long start = System.currentTimeMillis();
        try {
            restTemplate.getForEntity(url, String.class);
            long duration = System.currentTimeMillis() - start;
            return HealthCheckResult.success(duration);
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
