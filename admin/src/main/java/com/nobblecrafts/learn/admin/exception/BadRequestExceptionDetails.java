package com.nobblecrafts.learn.admin.exception;

import com.nobblecrafts.learn.admin.exception.handler.ExceptionDetails;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Esse super builder é usado para subclasses. Eu não sei direito o porquê mas
 * dá para entender que o padrão em sí irá entrar em conflito com a superclasse
 * porque ela é automatizada e não irá levar em consideração a superclasse.
 */

@Getter
@SuperBuilder
public class BadRequestExceptionDetails extends ExceptionDetails {

}
