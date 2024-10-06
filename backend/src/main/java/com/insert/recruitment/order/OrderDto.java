package com.insert.recruitment.order;

import java.math.BigDecimal;
import java.time.LocalDate;

record OrderDto (Long id, OrderStatus status, String nameOfOrder, BigDecimal orderPrice, LocalDate dateOfOrder, String description, String receiver, String sender) {
}
