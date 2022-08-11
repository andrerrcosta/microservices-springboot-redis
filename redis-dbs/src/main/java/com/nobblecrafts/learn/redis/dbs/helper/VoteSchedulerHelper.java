package com.nobblecrafts.learn.redis.dbs.helper;

import com.nobblecrafts.learn.redis.dbs.config.SchedulerConfigData;
import com.nobblecrafts.learn.redis.dbs.domain.AgendaDTO;
import com.nobblecrafts.learn.redis.dbs.domain.RedisAgenda;
import com.nobblecrafts.learn.redis.dbs.exception.SchedulerException;
import com.nobblecrafts.learn.redis.dbs.mapper.AgendaMapper;
import com.nobblecrafts.learn.redis.dbs.repository.RedisAgendaRepository;
import com.nobblecrafts.learn.redis.dbs.repository.RedisVoteRepository;
import com.nobblecrafts.learn.redis.dbs.task.CloseVoting;
import com.nobblecrafts.learn.redis.dbs.task.OpenVoting;
import com.nobblecrafts.learn.redis.message.AMQPPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoteSchedulerHelper {

    private final AgendaMapper mapper = AgendaMapper.INSTANCE;
    private final SchedulerConfigData configData;
    private final ThreadPoolTaskScheduler scheduler;
    private final RedisAgendaRepository agendaRepository;
    private final RedisVoteRepository voteRepository;
    private final AMQPPublisher publisher;

    public RedisAgenda initializeRedisAgenda(AgendaDTO agenda) {

        Assert.isTrue(agenda != null,
                "A agenda não pode ser nula");
        Assert.isTrue(agenda.getStart() != null,
                "A agenda precisa ter uma data inicial");
        var now = new Date().getTime();
        if (agenda.getStart().getTime() < now)
            throw new SchedulerException("A data de início da agenda não pode ser anterior ao seu agendamento");

        log.info("\nAgendando agenda {} com delay de {}ms\n", agenda, configData.getDelay());
        agenda.setStart(new Date(agenda.getStart().getTime() + configData.getDelay()));
        log.info("\n\nAgenda agendada para: {}\n{}\n\n", agenda.getStart(), agenda);
        return mapper.toRedisAgenda(agenda);
    }

    public void scheduleAgenda(RedisAgenda agenda) {
        scheduler.schedule(new OpenVoting(agenda, agendaRepository), agenda.getStart());

        var end = (agenda.getEnd() == null || agenda.getEnd().getTime() < agenda.getStart().getTime())
                ? new Date(agenda.getStart().getTime() + 60000)
                : agenda.getEnd();

        scheduler.schedule(new CloseVoting(agenda, voteRepository, agendaRepository, publisher, mapper), end);
    }

}
