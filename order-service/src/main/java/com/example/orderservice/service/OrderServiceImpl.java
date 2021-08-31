package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.repository.OrderEntity;
import com.example.orderservice.repository.OrderRepository;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

  private final OrderRepository orderRepository;
  private final ModelMapper modelMapper;

  public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
    this.orderRepository = orderRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public OrderDto createOrder(OrderDto orderDto) {
    orderDto.setOrderId(UUID.randomUUID().toString());
    orderDto.setTotalPrice(orderDto.getUnitPrice() * orderDto.getQty());
    OrderEntity orderEntity = modelMapper.map(orderDto, OrderEntity.class);
    orderRepository.save(orderEntity);
    return orderDto;
  }

  @Override
  public OrderDto getOrderByOrderId(String orderId) {
    OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
    return modelMapper.map(orderEntity, OrderDto.class);
  }

  @Override
  public Iterable<OrderEntity> getOrdersByUserId(String userId) {
    return orderRepository.findByUserId(userId);
  }
}
