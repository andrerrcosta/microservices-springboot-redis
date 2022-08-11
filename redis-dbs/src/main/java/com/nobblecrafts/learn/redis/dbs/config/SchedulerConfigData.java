package com.nobblecrafts.learn.redis.dbs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "scheduler-config")
public class SchedulerConfigData {
    private Long delay;
}
