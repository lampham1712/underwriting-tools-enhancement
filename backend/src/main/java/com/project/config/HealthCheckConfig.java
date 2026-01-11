package com.project.config;

import com.project.service.health.HealthCheckStrategy;
import com.project.service.health.Jt400HealthCheckStrategy;
import com.project.service.health.RestHealthCheckStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class HealthCheckConfig {

    private final RestTemplateBuilder restTemplateBuilder;

    public HealthCheckConfig(RestTemplateBuilder restTemplateBuilder,
                             @Value("${http.client.connection-timeout:5000}") long connectTimeout,
                             @Value("${http.client.read-timeout:5000}") long readTimeout) {
        this.restTemplateBuilder = restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout));
    }

    @Bean
    public HealthCheckStrategy ccrStrategy(@Value("${systems.ccr.url}") String url) {
        return new RestHealthCheckStrategy("CCR", url, restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy paymentGatewayStrategy(@Value("${systems.payment-gateway.url}") String url) {
        return new RestHealthCheckStrategy("PAY_GATE", url, restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy bowStrategy(@Value("${systems.bow.url}") String url) {
        return new RestHealthCheckStrategy("BOW", url, restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy laStrategy(@Value("${systems.la.url}") String url) {
        return new RestHealthCheckStrategy("LA", url, restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy cubeStrategy(@Value("${systems.cube.url}") String url) {
        return new RestHealthCheckStrategy("CUBE", url, restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy casepediaStrategy(@Value("${systems.casepedia.url}") String url) {
        return new RestHealthCheckStrategy("CASEPEDIA", url, restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy nanoStrategy(@Value("${systems.nano.url}") String url) {
        return new RestHealthCheckStrategy("NANO", url, restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy jt400Strategy(@Value("${systems.jt400.host}") String host,
                                             @Value("${systems.jt400.username}") String user,
                                             @Value("${systems.jt400.password}") String pass) {
        return new Jt400HealthCheckStrategy("JT400", host, user, pass);
    }

    @Bean
    public HealthCheckStrategy intStrategy(@Value("${systems.int.url}") String url) {
        return new RestHealthCheckStrategy("INT", url, restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy owbOpusStrategy(@Value("${systems.owb-opus.url}") String url) {
        return new RestHealthCheckStrategy("OWB_OPUS", url, restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy uwmeStrategy(@Value("${systems.uwme.url}") String url) {
        return new RestHealthCheckStrategy("UWME", url, restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy fcrmStrategy(@Value("${systems.fcrm.url}") String url) {
        return new RestHealthCheckStrategy("FCRM", url, restTemplateBuilder);
    }

    @Bean
    public HealthCheckStrategy dmsStrategy(@Value("${systems.dms.url}") String url) {
        return new RestHealthCheckStrategy("DMS", url, restTemplateBuilder);
    }
}
