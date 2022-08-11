package com.nobblecrafts.learn.redis.dbs.task;

import com.nobblecrafts.learn.redis.dbs.domain.RedisAgenda;
import com.nobblecrafts.learn.redis.dbs.repository.RedisAgendaRepository;
import com.nobblecrafts.learn.redis.dbs.repository.RedisVoteRepository;
import com.nobblecrafts.learn.redis.dbs.mapper.AgendaMapper;
import com.nobblecrafts.learn.redis.dbs.util.RedisVoteBuilder;
import com.nobblecrafts.learn.redis.message.AMQPPublisher;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CloseVoting implements Runnable {

    private final RedisAgenda agenda;
    private final RedisAgendaRepository agendaRepository;
    private final RedisVoteRepository voteRepository;
    private final AMQPPublisher publisher;
    private final AgendaMapper mapper;
    private final RedisVoteBuilder redisVoteBuilder;

    public CloseVoting(RedisAgenda agenda, RedisVoteRepository voteRepository, RedisAgendaRepository agendaRepository,
                       AMQPPublisher publisher, AgendaMapper mapper) {
        this.agenda = agenda;
        this.voteRepository = voteRepository;
        this.agendaRepository = agendaRepository;
        this.publisher = publisher;
        this.mapper = AgendaMapper.INSTANCE;
        this.redisVoteBuilder = new RedisVoteBuilder();
    }

    @Override
    public void run() {
        var allVotes = this.voteRepository.findAllById(agenda.getAssociates().stream()
                .map(a -> redisVoteBuilder.buildVoteKey(a, agenda.getId()))
                .collect(Collectors.toList()));

        Map<Long, String> votes = new HashMap<>();
        allVotes.forEach(v -> votes.put(redisVoteBuilder.getIdFromRedisVote(v), v.getVote()));
        agenda.setVotes(votes);
        agendaRepository.delete(agenda);
        voteRepository.deleteAll(allVotes);
        publisher.publish(mapper.toDTO(agenda));
        log.info("Votação da agenda {} foi encerrada", agenda);
    }

}