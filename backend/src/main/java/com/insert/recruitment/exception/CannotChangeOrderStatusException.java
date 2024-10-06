package com.insert.recruitment.exception;

import static java.lang.String.format;

public class CannotChangeOrderStatusException extends RuntimeException {

  private static final String MESSAGE = "Order already has %s status.";

  public CannotChangeOrderStatusException(String status) {
    super(format(MESSAGE, status));
  }
}
