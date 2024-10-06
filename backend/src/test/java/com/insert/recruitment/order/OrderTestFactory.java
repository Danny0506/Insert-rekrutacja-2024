package com.insert.recruitment.order;

import java.math.BigDecimal;
import java.time.LocalDate;

class OrderTestFactory {

  static final String NAME_OF_ORDER = "Order";

  static final Long ID = 1L;
  static final BigDecimal ORDER_PRICE = BigDecimal.valueOf(5000);
  static final String DESCRIPTION = "description";
  static final String RECEIVER = "Kowalski";
  static final String SENDER = "shop";
  static final LocalDate DATE = LocalDate.of(2023, 1, 1);

  static OrderCommand createOrderCommand() {
    return new OrderCommand(NAME_OF_ORDER, ORDER_PRICE, DESCRIPTION, RECEIVER, SENDER);
  }

  static ChangeStatusCommand changeStatus() {
    return new ChangeStatusCommand(true);
  }

  static ChangeStatusCommand notChangeStatus() {
    return new ChangeStatusCommand(false);
  }

  static OrderEntity createOrderEntity() {
    final OrderEntity orderEntity = new OrderEntity();
    orderEntity.setId(ID);
    orderEntity.setStatus(OrderStatus.CREATED);
    orderEntity.setOrderPrice(ORDER_PRICE);
    orderEntity.setNameOfOrder(NAME_OF_ORDER);
    orderEntity.setDescription(DESCRIPTION);
    orderEntity.setReceiver(RECEIVER);
    orderEntity.setSender(SENDER);
    orderEntity.setDateOfOrder(DATE);
    return orderEntity;
  }

  static String createOrderCommandAsJson()
  {
    return """
        {
            "nameOfOrder": "galaxy s23",
            "description": "galaxy s23",
            "orderPrice": 5000,
            "receiver": "Kowal",
            "sender": "Sklep"
        }
      """;
  }

  static String createChangeStatusCommandAsJson(boolean isChanged)
  {
    return """
        {
            "isChangeStatusOrder": %s
        }
      """.formatted(isChanged);
  }
}
