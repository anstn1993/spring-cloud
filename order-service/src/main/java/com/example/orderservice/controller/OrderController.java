package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.repository.OrderEntity;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-service")
@Slf4j
public class OrderController {

  private final OrderService orderService;
  private final ModelMapper modelMapper;

  public OrderController(OrderService orderService, ModelMapper modelMapper) {
    this.orderService = orderService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/health_check")
  public String status(HttpServletRequest request) {
    return String.format("It's Working in Order Service on PORT %s", request.getServerPort());
  }

  @PostMapping("/{userId}/orders")
  public ResponseEntity<ResponseOrder> createOrder(@PathVariable String userId, @RequestBody RequestOrder requestOrder) {
    OrderDto orderDto = modelMapper.map(requestOrder, OrderDto.class);
    orderDto.setUserId(userId);
    orderService.createOrder(orderDto);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(modelMapper.map(orderDto, ResponseOrder.class));
  }

  @GetMapping("/{userId}/orders")
  public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable String userId) {
    Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);
    List<ResponseOrder> result = new ArrayList<>();
    for (OrderEntity order : orders) {
      result.add(modelMapper.map(order, ResponseOrder.class));
    }
    return ResponseEntity.ok(result);
  }
}
