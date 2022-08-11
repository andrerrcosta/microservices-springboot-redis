package com.nobblecrafts.learn.redis.dbs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SchedulerException extends RuntimeException {

  public SchedulerException(String message) {
    super(message);
  }

}
