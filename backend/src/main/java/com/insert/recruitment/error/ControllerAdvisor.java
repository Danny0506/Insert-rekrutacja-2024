package com.insert.recruitment.error;

import static com.insert.recruitment.error.ReasonCode.*;
import static java.lang.String.format;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import com.insert.recruitment.exception.CannotChangeOrderStatusException;
import com.insert.recruitment.exception.OrderNotExistException;
import jakarta.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
@Slf4j
public class ControllerAdvisor {

  private static final String SOURCE = "INSERT_RECRUITMENT_API";
  private static final String LOG_CONSTANT = "Error was occurred. Message: %s ";

  @ExceptionHandler(OrderNotExistException.class)
  public ResponseEntity<ErrorMessage> handleOrderNotExistException(
      OrderNotExistException exception) {
    log.info(format(LOG_CONSTANT, exception.getMessage()));
    log.debug(format(LOG_CONSTANT, exception.getMessage()));

    final ErrorMessage errorMessage =
        new ErrorMessage(
            SOURCE, LocalDateTime.now(), ORDER_NOT_EXIST.name(), exception.getMessage());

    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(CannotChangeOrderStatusException.class)
  public ResponseEntity<ErrorMessage> handleCannotChangeOrderStatusException(
      CannotChangeOrderStatusException exception) {
    log.info(format(LOG_CONSTANT, exception.getMessage()));
    log.debug(format(LOG_CONSTANT, exception.getMessage()));

    final ErrorMessage errorMessage =
        new ErrorMessage(
            SOURCE, LocalDateTime.now(), CANNOT_CHANGE_ORDER_STATUS.name(), exception.getMessage());

    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorMessage> handleConstraintViolationException(
      ConstraintViolationException exception) {
    log.info(format(LOG_CONSTANT, exception.getMessage()));
    log.debug(format(LOG_CONSTANT, exception.getMessage()));

    final ErrorMessage errorMessage =
        new ErrorMessage(
            SOURCE, LocalDateTime.now(), REQUEST_VALIDATION.name(), exception.getMessage());

    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public ResponseEntity<ErrorMessage> handleSQLIntegrityConstraintViolationException(
      SQLIntegrityConstraintViolationException exception) {
    log.info(format(LOG_CONSTANT, exception.getMessage()));
    log.debug(format(LOG_CONSTANT, exception.getMessage()));

    final ErrorMessage errorMessage =
        new ErrorMessage(
            SOURCE,
            LocalDateTime.now(),
            SQL_INTEGRITY_CONSTRAINT_VALIDATION.name(),
            exception.getMessage());

    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorMessage> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException exception) {
    log.info(format(LOG_CONSTANT, exception.getMessage()));
    log.debug(format(LOG_CONSTANT, exception.getMessage()));

    final ErrorMessage errorMessage =
        new ErrorMessage(
            SOURCE, LocalDateTime.now(), METHOD_NOT_ALLOW.name(), exception.getMessage());

    return new ResponseEntity<>(errorMessage, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException exception) {
    log.info(format(LOG_CONSTANT, exception.getMessage()));
    log.debug(format(LOG_CONSTANT, exception.getMessage()));

    final ErrorMessage errorMessage =
        new ErrorMessage(
            SOURCE, LocalDateTime.now(), ARGUMENT_TYPE_MISMATCH.name(), exception.getMessage());

    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingPathVariableException.class)
  public ResponseEntity<ErrorMessage> handleMissingPathVariableException(
      MissingPathVariableException exception) {
    log.info(format(LOG_CONSTANT, exception.getMessage()));
    log.debug(format(LOG_CONSTANT, exception.getMessage()));

    final ErrorMessage errorMessage =
        new ErrorMessage(
            SOURCE, LocalDateTime.now(), MISSING_PATH_VARIABLE.name(), exception.getMessage());

    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException exception) {
    log.info(format(LOG_CONSTANT, exception.getMessage()));
    log.debug(format(LOG_CONSTANT, exception.getMessage()));

    final ErrorMessage errorMessage =
        new ErrorMessage(
            SOURCE,
            LocalDateTime.now(),
            MESSAGE_NOT_READABLE.name(),
            "Required request body is missing.");

    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    log.info(format(LOG_CONSTANT, exception.getMessage()));
    log.debug(format(LOG_CONSTANT, exception.getMessage()));

    final ErrorMessage errorMessage =
        new ErrorMessage(
            SOURCE,
            LocalDateTime.now(),
            METHOD_ARGUMENT_NOT_VALID.name(),
            "Method argument contain illegal value.");

    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }
}
