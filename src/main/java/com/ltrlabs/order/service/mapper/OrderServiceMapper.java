package com.ltrlabs.order.service.mapper;

import com.ltrlabs.order.model.Order;
import com.ltrlabs.order.model.OrderItem;
import com.ltrlabs.order.service.dto.ItemDTO;
import com.ltrlabs.order.service.dto.OrderDTO;

import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceMapper {

    public static OrderItem mapToItem(ItemDTO itemDTO) {
        if (itemDTO == null) return null;
        return OrderItem.builder()
                .itemID(itemDTO.getItemID())
                .productID(itemDTO.getProductID())
                .quantity(itemDTO.getQuantity())
                .build();
    }

    public static ItemDTO mapToItemDTO(OrderItem item) {
        if (item == null) return null;
        return ItemDTO.builder()
                .itemID(item.getItemID())
                .productID(item.getProductID())
                .netPrice(item.getNetPrice())
                .netTotal(item.getNetTotal())
                .total(item.getTotal())
                .quantity(item.getQuantity())
                .build();
    }

    public static OrderDTO mapToOrderDTO(Order order) {
        if (order == null) return null;
        return OrderDTO.builder()
                .orderID(order.getOrderID())
                .netTotal(order.getNetTotal())
                .total(order.getTotal())
                .tax(order.getTax())
                .build();
    }

    public static List<ItemDTO> mapToItemsDTO(List<OrderItem> items) {
        return items.stream().map(OrderServiceMapper::mapToItemDTO).toList();
    }
}
