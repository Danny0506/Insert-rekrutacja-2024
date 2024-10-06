package com.insert.recruitment.order;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<Void> createOrder(@Valid @RequestBody OrderCommand orderCommand) {
    orderService.createOrder(orderCommand);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PatchMapping("/{orderId}/acceptance")
  public ResponseEntity<OrderDto> acceptOrder(
      @PathVariable Long orderId, @Valid @RequestBody ChangeStatusCommand statusCommand) {
    final OrderDto order = orderService.acceptOrder(orderId, statusCommand);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @PatchMapping("/{orderId}/termination")
  public ResponseEntity<OrderDto> endOrder(
      @PathVariable Long orderId, @Valid @RequestBody ChangeStatusCommand statusCommand) {
    final OrderDto order = orderService.endOrder(orderId, statusCommand);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
    orderService.cancelOrder(orderId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<OrderDto>> getOrders() {
    final List<OrderDto> orders = orderService.getOrders();
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
    final OrderDto order = orderService.getOrder(orderId);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }
}
