package com.ltrlabs.order.repository;

import com.ltrlabs.order.model.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemRepository {

    UUID createItem(OrderItem item);
    void removeItem(UUID itemID);
    void updateItem(OrderItem item);
    Optional<OrderItem> getItem(UUID itemID);
    List<OrderItem> getOrderItems(UUID orderID);
    List<OrderItem> getAllItems();
    void removeItemsByOrderId(UUID orderID);
    boolean isItemInOrder(UUID orderID, UUID itemID);
}
