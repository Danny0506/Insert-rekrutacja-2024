package com.insert.recruitment.order;

import static com.insert.recruitment.order.OrderTestFactory.createChangeStatusCommandAsJson;
import static com.insert.recruitment.order.OrderTestFactory.createOrderCommandAsJson;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.insert.recruitment.BaseTestIT;
import com.insert.recruitment.error.ErrorMessage;
import com.insert.recruitment.error.ReasonCode;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

class OrderTestIT extends BaseTestIT {

  private static final String ORDER_NOT_EXIST_MESSAGE = "Order with id %s does not exists.";
  private static final String ORDER_ALREADY_HAS_STATUS_MESSAGE = "Order already has %s status.";

  @Test
  void shouldCorrectCreateNewOrder() throws Exception {
    // given
    final String commandAsJson = createOrderCommandAsJson();

    // when
    final MvcResult result =
        mockMvc
            .perform(post("/orders").headers(prepareHttpHeaders()).content(commandAsJson))
            .andDo(print())
            .andReturn();

    // then
    assertThat(result.getResponse().getStatus()).isEqualTo(201);
  }

  @Test
  void shouldGetAllOrders() throws Exception {
    // given
    // when
    final MvcResult result =
        mockMvc.perform(get("/orders").headers(prepareHttpHeaders())).andDo(print()).andReturn();

    // then
    final List<OrderDto> orders =
        objectMapper.readValue(
            result.getResponse().getContentAsString(),
            objectMapper.getTypeFactory().constructCollectionType(List.class, OrderDto.class));

    assertThat(result.getResponse().getStatus()).isEqualTo(200);
    assertThat(orders).hasSize(4);
  }

  @Test
  void shouldCorrectAcceptOrder() throws Exception {
    // given
    final long orderId = 2L;
    final String changeStatusCommandAsJson = createChangeStatusCommandAsJson(true);

    // when
    final MvcResult result =
        mockMvc
            .perform(
                patch("/orders/{orderId}/acceptance", orderId)
                    .headers(prepareHttpHeaders())
                    .content(changeStatusCommandAsJson))
            .andDo(print())
            .andReturn();

    // then
    final OrderDto order =
        objectMapper.readValue(result.getResponse().getContentAsString(), OrderDto.class);

    assertThat(result.getResponse().getStatus()).isEqualTo(200);
    assertThat(order).isNotNull();
    assertThat(order.id()).isEqualTo(orderId);
    assertThat(order.status()).isEqualTo(OrderStatus.APPROVED);
  }

  @Test
  void shouldCorrectEndOrder() throws Exception {
    // given
    final long orderId = 2L;
    final String changeStatusCommandAsJson = createChangeStatusCommandAsJson(true);

    // when
    final MvcResult result =
        mockMvc
            .perform(
                patch("/orders/{orderId}/termination", orderId)
                    .headers(prepareHttpHeaders())
                    .content(changeStatusCommandAsJson))
            .andDo(print())
            .andReturn();

    // then
    final OrderDto order =
        objectMapper.readValue(result.getResponse().getContentAsString(), OrderDto.class);

    assertThat(result.getResponse().getStatus()).isEqualTo(200);
    assertThat(order).isNotNull();
    assertThat(order.id()).isEqualTo(orderId);
    assertThat(order.status()).isEqualTo(OrderStatus.COMPLETED);
  }

  @Test
  void shouldReturnOrderNotExistErrorWhenGetOrder() throws Exception {
    // given
    final long orderId = 222L;

    // when
    final MvcResult result =
        mockMvc
            .perform(get("/orders/{orderId}", orderId).headers(prepareHttpHeaders()))
            .andDo(print())
            .andReturn();

    // then
    final ErrorMessage message =
        objectMapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    assertThat(result.getResponse().getStatus()).isEqualTo(404);
    assertThat(message.reasonCode()).isEqualTo(ReasonCode.ORDER_NOT_EXIST.toString());
    assertThat(message.message()).isEqualTo(format(ORDER_NOT_EXIST_MESSAGE, orderId));
  }

  @Test
  void shouldCorrectGetOrder() throws Exception {
    // given
    final long orderId = 3L;

    // when
    final MvcResult result =
        mockMvc
            .perform(get("/orders/{orderId}", orderId).headers(prepareHttpHeaders()))
            .andDo(print())
            .andReturn();

    // then
    final OrderDto order =
        objectMapper.readValue(result.getResponse().getContentAsString(), OrderDto.class);

    assertThat(result.getResponse().getStatus()).isEqualTo(200);
    assertThat(order).isNotNull();
    assertThat(order.id()).isEqualTo(orderId);
  }

  @Test
  void shouldReturnCannotChangeOrderStatusErrorWhenEndOrder() throws Exception {
    // given
    final long orderId = 4L;
    final String changeStatusCommandAsJson = createChangeStatusCommandAsJson(true);

    // when
    final MvcResult result =
        mockMvc
            .perform(
                patch("/orders/{orderId}/termination", orderId)
                    .headers(prepareHttpHeaders())
                    .content(changeStatusCommandAsJson))
            .andDo(print())
            .andReturn();

    // then
    final ErrorMessage message =
        objectMapper.readValue(result.getResponse().getContentAsString(), ErrorMessage.class);

    assertThat(result.getResponse().getStatus()).isEqualTo(400);
    assertThat(message.reasonCode()).isEqualTo(ReasonCode.CANNOT_CHANGE_ORDER_STATUS.toString());
    assertThat(message.message())
        .isEqualTo(format(ORDER_ALREADY_HAS_STATUS_MESSAGE, OrderStatus.COMPLETED));
  }

  @Test
  void shouldCorrectCancelOrder() throws Exception {
    // given
    final long orderId = 3L;

    // when
    final MvcResult result =
        mockMvc
            .perform(delete("/orders/{orderId}", orderId).headers(prepareHttpHeaders()))
            .andDo(print())
            .andReturn();

    // then
    assertThat(result.getResponse().getStatus()).isEqualTo(200);
  }
}
