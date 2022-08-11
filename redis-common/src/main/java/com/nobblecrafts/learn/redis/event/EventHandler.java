package com.nobblecrafts.learn.redis.event;

public interface EventHandler<T> {

    void handle(final T dto);
}
