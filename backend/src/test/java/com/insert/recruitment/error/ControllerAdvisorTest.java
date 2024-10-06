package com.insert.recruitment.error;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.insert.recruitment.exception.CannotChangeOrderStatusException;
import com.insert.recruitment.exception.OrderNotExistException;
import jakarta.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

class ControllerAdvisorTest {

  private static final String SOURCE = "INSERT_RECRUITMENT_API";

  private final ControllerAdvisor controllerAdvisor = new ControllerAdvisor();

  @Test
  void shouldHandleCannotChangeOrderStatusException() {
    // given
    final String status = "ACTIVATED";
    final CannotChangeOrderStatusException exception = new CannotChangeOrderStatusException(status);
    final String expectedResponse = "Order already has ACTIVATED status.";

    // when
    ResponseEntity<ErrorMessage> response =
        controllerAdvisor.handleCannotChangeOrderStatusException(exception);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(requireNonNull(response.getBody()).message()).isEqualTo(expectedResponse);
    assertThat(requireNonNull(response.getBody()).source()).isEqualTo(SOURCE);
  }

  @Test
  void shouldHandleConstraintViolationException() {
    // given
    final String message = "message";
    final ConstraintViolationException exception =
        new ConstraintViolationException(message, Set.of());

    // when
    ResponseEntity<ErrorMessage> response =
        controllerAdvisor.handleConstraintViolationException(exception);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(requireNonNull(response.getBody()).message()).isEqualTo(message);
    assertThat(requireNonNull(response.getBody()).source()).isEqualTo(SOURCE);
  }

  @Test
  void shouldHandleSQLIntegrityConstraintViolationException() {
    // given
    final String reason = "ERROR";
    final SQLIntegrityConstraintViolationException exception =
        new SQLIntegrityConstraintViolationException(reason);

    // when
    ResponseEntity<ErrorMessage> response =
        controllerAdvisor.handleSQLIntegrityConstraintViolationException(exception);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(requireNonNull(response.getBody()).message()).isEqualTo(reason);
    assertThat(requireNonNull(response.getBody()).source()).isEqualTo(SOURCE);
  }

  @Test
  void shouldHandleOrderNotExistException() {
    // given
    final Long id = 1L;
    final OrderNotExistException exception = new OrderNotExistException(id);
    final String expectedResponse = "Order with id 1 does not exists.";

    // when
    ResponseEntity<ErrorMessage> response =
        controllerAdvisor.handleOrderNotExistException(exception);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(requireNonNull(response.getBody()).message()).isEqualTo(expectedResponse);
    assertThat(requireNonNull(response.getBody()).source()).isEqualTo(SOURCE);
  }

  @Test
  void shouldHandleMethodNotSupportedException() {
    // given
    final String method = "PATCH";
    final HttpRequestMethodNotSupportedException exception =
        new HttpRequestMethodNotSupportedException(method);
    final String expectedResponse = "Request method 'PATCH' is not supported";

    // when
    ResponseEntity<ErrorMessage> response =
        controllerAdvisor.handleHttpRequestMethodNotSupportedException(exception);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    assertThat(requireNonNull(response.getBody()).message()).isEqualTo(expectedResponse);
    assertThat(requireNonNull(response.getBody()).source()).isEqualTo(SOURCE);
  }

  @Test
  void shouldHandleMethodArgumentTypeMismatchException() {
    // given
    final MethodArgumentTypeMismatchException exception =
        mock(MethodArgumentTypeMismatchException.class);

    // when
    ResponseEntity<ErrorMessage> response =
        controllerAdvisor.handleMethodArgumentTypeMismatchException(exception);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(requireNonNull(response.getBody()).source()).isEqualTo(SOURCE);
  }

  @Test
  void shouldHandleMissingPathVariableException() {
    // given
    final MissingPathVariableException exception = mock(MissingPathVariableException.class);

    // when
    ResponseEntity<ErrorMessage> response =
        controllerAdvisor.handleMissingPathVariableException(exception);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(requireNonNull(response.getBody()).source()).isEqualTo(SOURCE);
  }

  @Test
  void shouldHandleHttpMessageNotReadableException() {
    // given
    final HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);

    // when
    ResponseEntity<ErrorMessage> response =
        controllerAdvisor.handleHttpMessageNotReadableException(exception);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(requireNonNull(response.getBody()).source()).isEqualTo(SOURCE);
  }

  @Test
  void shouldHandleMethodArgumentNotValidException() {
    // given
    final MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);

    // when
    ResponseEntity<ErrorMessage> response =
        controllerAdvisor.handleMethodArgumentNotValidException(exception);

    // then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(requireNonNull(response.getBody()).source()).isEqualTo(SOURCE);
  }
}
