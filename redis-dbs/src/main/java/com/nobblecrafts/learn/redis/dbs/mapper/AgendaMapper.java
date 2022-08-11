package com.nobblecrafts.learn.redis.dbs.mapper;

import com.nobblecrafts.learn.redis.dbs.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.dbs.domain.RedisAgenda;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AgendaMapper {

  public static final AgendaMapper INSTANCE = Mappers.getMapper(AgendaMapper.class);

  public abstract AgendaDTO toDTO(RedisAgenda agenda);
  public abstract RedisAgenda toRedisAgenda(AgendaDTO dto);

}