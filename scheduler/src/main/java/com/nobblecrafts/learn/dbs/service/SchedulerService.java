package com.nobblecrafts.learn.dbs.service;

import java.util.Date;

import com.nobblecrafts.learn.dbs.domain.AgendaDTO;
import com.nobblecrafts.learn.dbs.repository.RedisAgendaRepository;
import com.nobblecrafts.learn.dbs.repository.RedisVoteRepository;
import com.nobblecrafts.learn.dbs.task.CloseVoting;
import com.nobblecrafts.learn.dbs.task.OpenVoting;
import com.nobblecrafts.learn.dbs.util.AgendaMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchedulerService {

  private final Long delay;
  private final RedisAgendaRepository agendaRepository;
  private final RedisVoteRepository voteRepository;
  private final AgendaMapper mapper;
  private final ThreadPoolTaskScheduler scheduler;
  private final AgendaPublisher publisher;

  @Autowired
  public SchedulerService(RedisAgendaRepository agendaRepository, RedisVoteRepository voteRepository,
      ThreadPoolTaskScheduler scheduler, AgendaPublisher publisher, @Value("${config.scheduler.delay}") Long delay) {
    this.mapper = new AgendaMapper();
    this.agendaRepository = agendaRepository;
    this.voteRepository = voteRepository;
    this.scheduler = scheduler;
    this.publisher = publisher;
    this.delay = delay;
  }

  /**
   * A princípio eu estou usando um {@link TaskScheduler} Motivo:
   * - Eu não conheço uma forma, que seria o ideal, já disponibilizada pelo
   * spring, de basear-se em metadados de agendamentos para não precisar usar
   * a memória para agendar uma tarefa.
   * (Essa abordagem é ruim porque a memória de uma aplicação x tempo se torna
   * volátil em comparação a um banco de dados)
   * - Um outro microserviço responsável por verificar os eventos mais
   * recentes em um banco de dados e coloca-los na memória de acordo com a
   * proximidade seria uma abordagem interessante. Mas isso seria mais trabalho
   * que o
   * necessário
   * para uma demonstração técnica. (Essa abordagem seria feita da seguinte forma:
   * Este microserviço seria responsável por fazer consultas periódicas ao banco
   * através
   * de um "Observable" e escalonaria em uma fila do RabbitMQ para este
   * serviço
   * de acordo com a proximidade. Seria necessário, no entanto, de um tratamento
   * adicional. Pois cada instância do microserviço ficaria inconsciente dos
   * escalonamentos das outras instâncias. Uma vez que este escalonamento
   * também se daria através de filas vindas do AdminService)
   * De qualquer forma, fica aqui registrado um ponto de fragilidade no ponto de
   * vista da escalabilidade. (Uma vez que o ponto crítico é
   * a memória volátil e não o desempenho).
   * 
   * @param agenda
   */
  public void schedule(AgendaDTO agenda) {
    log.info("\nAgendando agenda {} com delay de {}ms\n", agenda, delay);
    var now = new Date().getTime();
    var start = new Date(agenda.getStart().getTime() + delay);
    var end = (agenda.getEnd() == null || agenda.getEnd().getTime() < agenda.getStart().getTime())
        ? new Date(agenda.getStart().getTime() + 60000)
        : agenda.getEnd();
    var redisAgenda = mapper.convertFromDTOToEntity(agenda);

    if (agenda.getStart().getTime() > now) {
      scheduler.schedule(new OpenVoting(redisAgenda, agendaRepository), start);
      scheduler.schedule(new CloseVoting(redisAgenda, voteRepository, agendaRepository, publisher), end);
    }
  }

}
