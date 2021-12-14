package com.nobblecrafts.learn.admin.exception;

import com.nobblecrafts.learn.admin.exception.handler.ExceptionDetails;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ValidationExceptionDetails extends ExceptionDetails {
  private String fields;
  private String fieldsMessage;
}
