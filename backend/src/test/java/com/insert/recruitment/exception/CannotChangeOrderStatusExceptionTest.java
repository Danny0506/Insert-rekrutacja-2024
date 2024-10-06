package com.insert.recruitment.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CannotChangeOrderStatusExceptionTest {

  @Test
  void shouldReturnCorrectMessage() {
    // given
    final String status = "ACCEPTED";
    final String expectedMessage = "Order already has ACCEPTED status.";

    // when
    final CannotChangeOrderStatusException exception = new CannotChangeOrderStatusException(status);

    // then
    assertThat(exception).hasMessage(expectedMessage);
  }
}
