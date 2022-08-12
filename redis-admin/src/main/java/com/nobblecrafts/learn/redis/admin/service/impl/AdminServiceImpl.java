package com.nobblecrafts.learn.redis.admin.service.impl;

import com.nobblecrafts.learn.redis.admin.domain.Agenda;
import com.nobblecrafts.learn.redis.admin.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.admin.domain.Associate;
import com.nobblecrafts.learn.redis.admin.domain.AssociateDTO;
import com.nobblecrafts.learn.redis.admin.exception.BadRequestException;
import com.nobblecrafts.learn.redis.admin.mapper.AgendaMapper;
import com.nobblecrafts.learn.redis.admin.mapper.AssociateMapper;
import com.nobblecrafts.learn.redis.admin.repository.AgendaRepository;
import com.nobblecrafts.learn.redis.admin.repository.AssociateRepository;
import com.nobblecrafts.learn.redis.admin.service.AdminService;
import com.nobblecrafts.learn.redis.message.AMQPPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final AgendaRepository repository;
    private final AssociateRepository associateRepository;
    private final AMQPPublisher publisher;
    private final AgendaMapper agendaMapper = AgendaMapper.INSTANCE;
    private final AssociateMapper associateMapper = AssociateMapper.INSTANCE;

    public AdminServiceImpl(AgendaRepository repository, AssociateRepository associateRepository, AMQPPublisher publisher) {
        this.repository = repository;
        this.associateRepository = associateRepository;
        this.publisher = publisher;
    }


    @Transactional
    public AgendaDTO createNewAgenda(AgendaDTO dto) {
        var afterMap = agendaMapper.toEntity(dto);
        var model = this.repository.save(afterMap);
        if (model != null)
            publisher.publish(agendaMapper.toDTO(model));
        return agendaMapper.toDTO(model);
    }

    @Transactional(readOnly = true)
    public List<AgendaDTO> getAllAgendas() {
        return agendaMapper.toDTO(repository.findAll());
    }

    @Transactional(readOnly = true)
    public AgendaDTO getAgenda(Long id) {
        Agenda output = null;
        try {
            output = this.repository.findById(id).get();
        } catch (Exception e) {
            log.error("Agenda não encontrada: {}", id);
            throw new BadRequestException("Agenda não encontrada: " + id);
        }
        return agendaMapper.toDTO(output);
    }

    @Transactional
    public AgendaDTO updateAgenda(AgendaDTO dto) {
        var model = this.repository.findById(dto.getId())
                .orElseThrow(() -> new BadRequestException("Agenda não encontrada"));
        agendaMapper.update(model, dto);
        return agendaMapper.toDTO(this.repository.save(model));
    }

    @Transactional
    public AssociateDTO addAssociate(AssociateDTO dto) {
        var associate = associateMapper.toEntity(dto);
        var saved = associateRepository.save(associate);
        var output = associateMapper.toDTO(saved);
        return output;
    }

    @Transactional
    public List<AssociateDTO> addAssociates(List<AssociateDTO> dtos) {
        return associateMapper.toDTO(associateRepository.saveAll(associateMapper.toEntity(dtos)));
    }

    @Transactional(readOnly = true)
    public List<AssociateDTO> getAllAssociates() {
        return associateMapper.toDTO(associateRepository.findAll());
    }

    @Transactional(readOnly = true)
    public AssociateDTO getAssociate(Long id) {
        Associate output = null;
        try {
            output = associateRepository.findById(id).get();
        } catch (Exception e) {
            log.error("Associado não encontrado: {}", id);
            throw new BadRequestException("Associado não encontrado: " + id);
        }
        return associateMapper.toDTO(output);
    }

}
