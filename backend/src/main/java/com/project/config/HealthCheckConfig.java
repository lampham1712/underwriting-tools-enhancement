package com.project.config;

import com.project.service.health.HealthCheckStrategy;
import com.project.service.health.Jt400HealthCheckStrategy;
import com.project.service.health.RestHealthCheckStrategy;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HealthCheckConfig {

    private final RestTemplateBuilder restTemplateBuilder;

    public HealthCheckConfig(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Bean
    public HealthCheckStrategy ccrStrategy() {
        return new RestHealthCheckStrategy("CCR", "http://localhost:8081/health", restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy paymentGatewayStrategy() {
        return new RestHealthCheckStrategy("PAY_GATE", "http://localhost:8082/health", restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy bowStrategy() {
        return new RestHealthCheckStrategy("BOW", "http://localhost:8083/health", restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy laStrategy() {
        return new RestHealthCheckStrategy("LA", "http://localhost:8084/health", restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy cubeStrategy() {
        return new RestHealthCheckStrategy("CUBE", "http://localhost:8085/health", restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy casepediaStrategy() {
        return new RestHealthCheckStrategy("CASEPEDIA", "http://localhost:8086/health", restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy nanoStrategy() {
        return new RestHealthCheckStrategy("NANO", "http://localhost:8087/health", restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy jt400Strategy() {
        // Using mock credentials from quickstart.md/application.properties
        return new Jt400HealthCheckStrategy("JT400", "192.168.1.100", "testuser", "testpass");
    }

    @Bean
    public HealthCheckStrategy intStrategy() {
        return new RestHealthCheckStrategy("INT", "http://localhost:8089/health", restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy owbOpusStrategy() {
        return new RestHealthCheckStrategy("OWB_OPUS", "http://localhost:8090/health", restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy uwmeStrategy() {
        return new RestHealthCheckStrategy("UWME", "http://localhost:8091/health", restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy fcrmStrategy() {
        return new RestHealthCheckStrategy("FCRM", "http://localhost:8092/health", restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy dmsStrategy() {
        return new RestHealthCheckStrategy("DMS", "http://localhost:8093/health", restTemplateBuilder);
    }
}
