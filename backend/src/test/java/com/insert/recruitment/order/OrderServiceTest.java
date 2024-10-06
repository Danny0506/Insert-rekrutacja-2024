package com.insert.recruitment.order;

import static com.insert.recruitment.order.OrderTestFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import com.insert.recruitment.exception.CannotChangeOrderStatusException;
import com.insert.recruitment.exception.OrderNotExistException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@MockitoSettings(strictness = Strictness.LENIENT)
class OrderServiceTest {

  @InjectMocks private OrderService orderService;

  @Mock private OrderRepository orderRepository;

  @Test
  void shouldCorrectCreateOrder() {
    // given
    final OrderCommand command = createOrderCommand();
    when(orderRepository.save(any(OrderEntity.class))).thenReturn(any(OrderEntity.class));

    // when
    orderService.createOrder(command);

    // then
    verify(orderRepository, times(1)).save(any(OrderEntity.class));
  }

  @Test
  void shouldCorrectCancelOrder() {
    // given
    final Long orderId = 1L;
    final OrderEntity entity = createOrderEntity();
    doNothing().when(orderRepository).delete(entity);
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));

    // when
    orderService.cancelOrder(orderId);

    // then
    verify(orderRepository, times(1)).delete(entity);
    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void shouldThrowOrderNotExistExceptionWhenCancelOrder() {
    // given
    final Long orderId = 2L;
    final OrderEntity entity = createOrderEntity();
    when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

    // when then
    assertThrows(OrderNotExistException.class, () -> orderService.cancelOrder(orderId));

    verify(orderRepository, times(0)).delete(entity);
    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void shouldCorrectEndOrder() {
    // given
    final Long orderId = 1L;
    final ChangeStatusCommand statusCommand = changeStatus();
    final OrderEntity entity = createOrderEntity();
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));

    // when
    final OrderDto result = orderService.endOrder(orderId, statusCommand);

    // then
    verify(orderRepository, times(1)).findById(orderId);

    assertThat(result.nameOfOrder()).isEqualTo(NAME_OF_ORDER);
    assertThat(result.sender()).isEqualTo(SENDER);
    assertThat(result.receiver()).isEqualTo(RECEIVER);
    assertThat(result.status()).isEqualTo(OrderStatus.COMPLETED);
    assertThat(result.description()).isEqualTo(DESCRIPTION);
    assertThat(result.orderPrice()).isEqualTo(ORDER_PRICE);
    assertThat(result.dateOfOrder()).isEqualTo(DATE);
  }

  @Test
  void shouldNotChangedStatusToEndOrder() {
    // given
    final Long orderId = 1L;
    final ChangeStatusCommand statusCommand = notChangeStatus();
    final OrderEntity entity = createOrderEntity();
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));

    // when
    final OrderDto result = orderService.endOrder(orderId, statusCommand);

    // then
    verify(orderRepository, times(1)).findById(orderId);

    assertThat(result.nameOfOrder()).isEqualTo(NAME_OF_ORDER);
    assertThat(result.sender()).isEqualTo(SENDER);
    assertThat(result.receiver()).isEqualTo(RECEIVER);
    assertThat(result.status()).isEqualTo(OrderStatus.CREATED);
    assertThat(result.description()).isEqualTo(DESCRIPTION);
    assertThat(result.orderPrice()).isEqualTo(ORDER_PRICE);
    assertThat(result.dateOfOrder()).isEqualTo(DATE);
  }

  @Test
  void shouldThrowOrderNotExistExceptionWhenEndOrder() {
    // given
    final Long orderId = 2L;
    final ChangeStatusCommand statusCommand = changeStatus();
    when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

    // when then
    assertThrows(OrderNotExistException.class, () -> orderService.endOrder(orderId, statusCommand));

    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void shouldThrowCannotChangeOrderStatusExceptionWhenEndOrder() {
    // given
    final Long orderId = 2L;
    final ChangeStatusCommand statusCommand = changeStatus();
    final OrderEntity entity = createOrderEntity();
    entity.setStatus(OrderStatus.COMPLETED);
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));

    // when then
    assertThrows(
        CannotChangeOrderStatusException.class,
        () -> orderService.endOrder(orderId, statusCommand));

    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void shouldCorrectAcceptOrder() {
    // given
    final Long orderId = 1L;
    final ChangeStatusCommand statusCommand = changeStatus();
    final OrderEntity entity = createOrderEntity();
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));

    // when
    final OrderDto result = orderService.acceptOrder(orderId, statusCommand);

    // then
    verify(orderRepository, times(1)).findById(orderId);

    assertThat(result.nameOfOrder()).isEqualTo(NAME_OF_ORDER);
    assertThat(result.sender()).isEqualTo(SENDER);
    assertThat(result.receiver()).isEqualTo(RECEIVER);
    assertThat(result.status()).isEqualTo(OrderStatus.APPROVED);
    assertThat(result.description()).isEqualTo(DESCRIPTION);
    assertThat(result.orderPrice()).isEqualTo(ORDER_PRICE);
    assertThat(result.dateOfOrder()).isEqualTo(DATE);
  }

  @Test
  void shouldNotChangedStatusToAcceptOrder() {
    // given
    final Long orderId = 1L;
    final ChangeStatusCommand statusCommand = notChangeStatus();
    final OrderEntity entity = createOrderEntity();
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));

    // when
    final OrderDto result = orderService.acceptOrder(orderId, statusCommand);

    // then
    verify(orderRepository, times(1)).findById(orderId);

    assertThat(result.nameOfOrder()).isEqualTo(NAME_OF_ORDER);
    assertThat(result.sender()).isEqualTo(SENDER);
    assertThat(result.receiver()).isEqualTo(RECEIVER);
    assertThat(result.status()).isEqualTo(OrderStatus.CREATED);
    assertThat(result.description()).isEqualTo(DESCRIPTION);
    assertThat(result.orderPrice()).isEqualTo(ORDER_PRICE);
    assertThat(result.dateOfOrder()).isEqualTo(DATE);
  }

  @Test
  void shouldThrowOrderNotExistExceptionWhenAcceptOrder() {
    // given
    final Long orderId = 2L;
    final ChangeStatusCommand statusCommand = changeStatus();
    when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

    // when then
    assertThrows(
        OrderNotExistException.class, () -> orderService.acceptOrder(orderId, statusCommand));

    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void shouldThrowCannotChangeOrderStatusExceptionWhenAcceptOrder() {
    // given
    final Long orderId = 2L;
    final ChangeStatusCommand statusCommand = changeStatus();
    final OrderEntity entity = createOrderEntity();
    entity.setStatus(OrderStatus.APPROVED);
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));

    // when then
    assertThrows(
        CannotChangeOrderStatusException.class,
        () -> orderService.acceptOrder(orderId, statusCommand));

    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void shouldCorrectGetAllOrders() {
    // given
    final OrderEntity entity = createOrderEntity();
    final List<OrderEntity> orders = List.of(entity, entity);
    when(orderRepository.findAll()).thenReturn(orders);

    // when
    final List<OrderDto> result = orderService.getOrders();

    // then
    verify(orderRepository, times(1)).findAll();
    assertThat(result).hasSize(2);

    OrderDto firstElement = result.get(0);
    OrderDto secondElement = result.get(1);

    assertThat(firstElement.nameOfOrder()).isEqualTo(NAME_OF_ORDER);
    assertThat(firstElement.sender()).isEqualTo(SENDER);
    assertThat(firstElement.receiver()).isEqualTo(RECEIVER);
    assertThat(firstElement.status()).isEqualTo(OrderStatus.CREATED);
    assertThat(firstElement.description()).isEqualTo(DESCRIPTION);
    assertThat(firstElement.orderPrice()).isEqualTo(ORDER_PRICE);
    assertThat(firstElement.dateOfOrder()).isEqualTo(DATE);

    assertThat(secondElement.nameOfOrder()).isEqualTo(NAME_OF_ORDER);
    assertThat(secondElement.sender()).isEqualTo(SENDER);
    assertThat(secondElement.receiver()).isEqualTo(RECEIVER);
    assertThat(secondElement.status()).isEqualTo(OrderStatus.CREATED);
    assertThat(secondElement.description()).isEqualTo(DESCRIPTION);
    assertThat(secondElement.orderPrice()).isEqualTo(ORDER_PRICE);
    assertThat(secondElement.dateOfOrder()).isEqualTo(DATE);
  }

  @Test
  void shouldCorrectEmptyListWhenOrdersNotExist() {
    // given
    final List<OrderEntity> orders = List.of();
    when(orderRepository.findAll()).thenReturn(orders);

    // when
    final List<OrderDto> result = orderService.getOrders();

    // then
    verify(orderRepository, times(1)).findAll();
    assertThat(result).isEmpty();
  }

  @Test
  void shouldCorrectGetOneOrder() {
    // given
    final Long orderId = 1L;
    final OrderEntity entity = createOrderEntity();
    when(orderRepository.findById(orderId)).thenReturn(Optional.of(entity));

    // when
    final OrderDto result = orderService.getOrder(orderId);

    // then
    verify(orderRepository, times(1)).findById(orderId);

    assertThat(result.nameOfOrder()).isEqualTo(NAME_OF_ORDER);
    assertThat(result.sender()).isEqualTo(SENDER);
    assertThat(result.receiver()).isEqualTo(RECEIVER);
    assertThat(result.status()).isEqualTo(OrderStatus.CREATED);
    assertThat(result.description()).isEqualTo(DESCRIPTION);
    assertThat(result.orderPrice()).isEqualTo(ORDER_PRICE);
    assertThat(result.dateOfOrder()).isEqualTo(DATE);
  }

  @Test
  void shouldThrowOrderNotExistExceptionWhenGetOneOrder() {
    // given
    final Long orderId = 2L;
    when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

    // when then
    assertThrows(OrderNotExistException.class, () -> orderService.getOrder(orderId));

    verify(orderRepository, times(1)).findById(orderId);
  }
}
