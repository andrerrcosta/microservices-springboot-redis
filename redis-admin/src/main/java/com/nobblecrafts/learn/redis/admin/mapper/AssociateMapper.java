package com.nobblecrafts.learn.redis.admin.mapper;

import com.nobblecrafts.learn.redis.admin.domain.Agenda;
import com.nobblecrafts.learn.redis.admin.domain.Associate;
import com.nobblecrafts.learn.redis.admin.domain.AssociateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "default", uses = MapperUtils.class)
public abstract class AssociateMapper {

    public static final AssociateMapper INSTANCE = Mappers.getMapper(AssociateMapper.class);

    public abstract AssociateDTO toDTO(Associate associate);

    public abstract Collection<AssociateDTO> toDTO(Collection<Associate> associate);

    public abstract List<AssociateDTO> toDTO(List<Associate> associate);

    public abstract Associate toEntity(AssociateDTO dto);

    public abstract Collection<Associate> toEntity(Collection<AssociateDTO> dtos);

    public abstract List<Associate> toEntity(List<AssociateDTO> dtos);

    public abstract void update(@MappingTarget Associate associate, AssociateDTO dto);
}
