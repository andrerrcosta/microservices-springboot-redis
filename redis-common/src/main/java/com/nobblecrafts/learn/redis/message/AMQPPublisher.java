package com.nobblecrafts.learn.redis.message;

public interface AMQPPublisher<T> {
    public void publish(final T dto);
}
