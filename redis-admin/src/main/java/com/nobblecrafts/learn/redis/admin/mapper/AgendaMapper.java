package com.nobblecrafts.learn.redis.admin.mapper;

import com.nobblecrafts.learn.redis.admin.domain.Agenda;
import com.nobblecrafts.learn.redis.admin.domain.AgendaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = MapperUtils.class)
public abstract class AgendaMapper {

    public static final AgendaMapper INSTANCE = Mappers.getMapper(AgendaMapper.class);

    public abstract AgendaDTO toDTO(Agenda agenda);

    public abstract Iterable<AgendaDTO> toDTO(Iterable<Agenda> agendas);

    public abstract List<AgendaDTO> toDTO(List<Agenda> agendas);

    public abstract Agenda toEntity(AgendaDTO dto);

    public abstract void update(@MappingTarget Agenda agenda, AgendaDTO dto);


}