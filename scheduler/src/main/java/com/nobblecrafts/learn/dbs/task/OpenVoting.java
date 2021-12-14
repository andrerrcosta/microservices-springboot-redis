package com.nobblecrafts.learn.dbs.task;

import java.util.Date;

import com.nobblecrafts.learn.dbs.domain.RedisAgenda;
import com.nobblecrafts.learn.dbs.repository.RedisAgendaRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Partindo do princípio no qual desejamos abrir uma votação,
 * este método deve enviar ao cliente uma notificação onde
 * ele diz que a votação está aberta.
 * Para que isso ocorra, poucas possibilidades existem: WebSocket
 * ou WebFlux (Stream). Mas para ambas as opções, o cliente deve iniciar a
 * comunicação. Como você pode perceber, seria preciso implementar
 * o lado do cliente para acompanhar essa solução.
 * Por esse motivo, essa classe implementará apenas os preparativos
 * da votação com o Redis e não notificará o cliente da abertura
 * da votação. Faremos apenas as validações de data.
 */
@Slf4j
public class OpenVoting implements Runnable {

  private RedisAgenda agenda;
  private RedisAgendaRepository repository;

  public OpenVoting(RedisAgenda agenda, RedisAgendaRepository repository) {
    this.agenda = agenda;
    this.repository = repository;
  }

    @Override
  public void run() {
    if (agenda.getEnd() == null || agenda.getEnd().getTime() <= agenda.getStart().getTime())
      agenda.setEnd(new Date(agenda.getStart().getTime() + 60000));
    this.repository.save(agenda);
    log.info("Votação da agenda {} foi iniciada", agenda);
  }

}
