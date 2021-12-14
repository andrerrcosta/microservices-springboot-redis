package com.nobblecrafts.learn.dbs.exception;

import com.nobblecrafts.learn.dbs.exception.handler.ExceptionDetails;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ValidationExceptionDetails extends ExceptionDetails {
  private String fields;
  private String fieldsMessage;
}
