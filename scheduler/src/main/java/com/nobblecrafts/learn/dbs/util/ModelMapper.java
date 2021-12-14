package com.nobblecrafts.learn.dbs.util;

import java.util.function.Function;

public abstract class ModelMapper<T, U> {

  private final Function<T, U> fromRedis;
  private final Function<U, T> fromEntity;

  public ModelMapper(final Function<T, U> fromRedis, final Function<U, T> fromEntity) {
    this.fromRedis = fromRedis;
    this.fromEntity = fromEntity;
  }

  public final U convertFromRedisToEntity(final T redis) {
    return fromRedis.apply(redis);
  }

  public final T convertFromEntityToRedis(final U entity) {
    return fromEntity.apply(entity);
  }
}
