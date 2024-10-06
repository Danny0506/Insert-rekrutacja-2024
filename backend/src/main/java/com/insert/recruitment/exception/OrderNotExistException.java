package com.insert.recruitment.exception;

import static java.lang.String.format;

public class OrderNotExistException extends RuntimeException {

  private static final String MESSAGE = "Order with id %s does not exists.";

  public OrderNotExistException(Long orderId) {
    super(format(MESSAGE, orderId));
  }
}
