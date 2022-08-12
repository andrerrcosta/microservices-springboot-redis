package com.nobblecrafts.learn.redis.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "amqp-config")
public class AMQPConfigData {
    private String schedulerExchange;
    private String adminExchange;
    private String adminQueue;
    private String schedulerRoutingKey;
    private String adminRoutingKey;
}
