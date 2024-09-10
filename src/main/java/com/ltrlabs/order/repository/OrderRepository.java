package com.ltrlabs.order.repository;

import com.ltrlabs.order.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    UUID createOrderForCustomer(UUID customerID);
    void removeOrder(UUID orderID);
    void updateOrder(Order order);
    Optional<Order> getOrder(UUID orderID);
    List<Order> getCustomerOrders(UUID customerID);
    List<Order> getAllOrders();
    boolean isOrderCreated(UUID orderID);
}
