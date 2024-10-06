package com.insert.recruitment.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OrderNotExistExceptionTest {
  @Test
  void shouldReturnCorrectMessage() {
    // given
    final Long orderId = 1L;
    final String expectedMessage = "Order with id 1 does not exists.";

    // when
    final OrderNotExistException exception = new OrderNotExistException(orderId);

    // then
    assertThat(exception).hasMessage(expectedMessage);
  }
}
