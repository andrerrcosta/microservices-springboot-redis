package com.nobblecrafts.learn.admin.util;

import java.util.function.Function;

public abstract class ModelMapper<T, U> {

  private final Function<T, U> fromDTO;
  private final Function<U, T> fromEntity;

  public ModelMapper(final Function<T, U> fromDTO, final Function<U, T> fromEntity) {
    this.fromDTO = fromDTO;
    this.fromEntity = fromEntity;
  }

  public final U convertFromDtoToEntity(final T dto) {
    return fromDTO.apply(dto);
  }

  public final T convertFromEntityToDto(final U entity) {
    return fromEntity.apply(entity);
  }
}
