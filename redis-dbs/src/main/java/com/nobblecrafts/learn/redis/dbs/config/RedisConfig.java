package com.nobblecrafts.learn.redis.dbs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

  private final String hostname;
  private final int port;
  @Autowired
  public RedisConfig(@Value("${redis.hostname}") final String hostname, @Value("${redis.port}") final int port) {
    this.hostname = hostname;
    this.port = port;
  }

  @Bean
  public LettuceConnectionFactory connectionFactory() {
    final RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
    configuration.setHostName(hostname);
    configuration.setPort(port);

    return new LettuceConnectionFactory(configuration);
  }

  @Bean
  @Primary
  public RedisTemplate<String, Object> redisTemplate() {
    final RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory());
    return template;
  }

}
