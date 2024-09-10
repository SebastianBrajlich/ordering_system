package com.ltrlabs.order.service;

import com.ltrlabs.customer.service.CustomerService;
import com.ltrlabs.customer.service.dto.CustomerDTO;
import com.ltrlabs.order.model.Order;
import com.ltrlabs.order.model.OrderItem;
import com.ltrlabs.order.repository.OrderItemRepository;
import com.ltrlabs.order.repository.OrderRepository;
import com.ltrlabs.order.service.dto.ItemDTO;
import com.ltrlabs.order.service.dto.OrderDTO;
import com.ltrlabs.order.service.exception.ItemNotValidException;
import com.ltrlabs.order.service.exception.NoOrderFoundException;
import com.ltrlabs.order.service.exception.OrderInvalidException;
import com.ltrlabs.order.service.mapper.OrderServiceMapper;
import com.ltrlabs.product.service.ProductService;
import com.ltrlabs.product.service.dto.ProductDTO;
import com.ltrlabs.product.service.exception.ProductNotValidException;
import com.ltrlabs.utils.Calculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository itemRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    public UUID createNewOrderForCustomer(UUID customerID) {
        return orderRepository.createOrderForCustomer(customerID);
    }

    public UUID addItemToOrder(UUID orderID, ItemDTO itemDTO) throws ItemNotValidException, ProductNotValidException, NoOrderFoundException {
        if (itemDTO == null) throw  new ItemNotValidException();
        if (!orderRepository.isOrderCreated(orderID)) throw new NoOrderFoundException();
        ProductDTO product = productService.getProduct(itemDTO.getProductID());
        OrderItem orderItem = OrderServiceMapper.mapToItem(itemDTO);
        orderItem.setOrderID(orderID);
        calculateItem(orderItem, itemDTO.getProductID());
        UUID itemId =  itemRepository.createItem(orderItem);
        calculateOrder(orderID);
        return itemId;
    }

    public void removeItemFromOrder(UUID itemID, UUID orderID) throws NoOrderFoundException {
        if (itemRepository.isItemInOrder(orderID, itemID)) {
            itemRepository.removeItem(itemID);
            calculateOrder(orderID);
        }
    }

    public void deleteOrder(UUID orderID) {
        itemRepository.removeItemsByOrderId(orderID);
        orderRepository.removeOrder(orderID);
    }

    public OrderDTO getOrder(UUID orderID) throws NoOrderFoundException {
        Optional<Order> order = orderRepository.getOrder(orderID);
        if (order.isPresent()) {
            UUID customerID = order.get().getCustomerID();
            List<ItemDTO> itemsDTO = getItemsDto(orderID);
            OrderDTO orderDTO = OrderServiceMapper.mapToOrderDTO(order.get());
            orderDTO.setCustomer(getCustomerDTO(customerID));
            orderDTO.setItems(itemsDTO);
            return orderDTO;

        }
        throw new NoOrderFoundException();


    }

    public List<OrderDTO> getCustomerOrders(UUID customerID) {
        return orderRepository.getCustomerOrders(customerID).stream()
                .map(OrderServiceMapper::mapToOrderDTO)
                .peek(o -> o.setItems(getItemsDto(o.getOrderID())))
                .peek(o -> o.setCustomer(getCustomerDTO(customerID)))
                .toList();
    }

    private List<ItemDTO> getItemsDto(UUID orderID) {
        List<OrderItem> orderItems = itemRepository.getOrderItems(orderID);
        return OrderServiceMapper.mapToItemsDTO(orderItems);
    }

    private CustomerDTO getCustomerDTO(UUID customerID) {
        return customerService.getCustomer(customerID).orElse(null);
    }

    private void calculateOrder(UUID orderID) throws NoOrderFoundException{
        Optional<Order> order = orderRepository.getOrder(orderID);
        if (order.isPresent()) {
            List<OrderItem> orderItems = itemRepository.getOrderItems(orderID);
            BigDecimal netTotal = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            BigDecimal tax;
            for (OrderItem item : orderItems) {
                netTotal = netTotal.add(item.getNetTotal());
                total = total.add(item.getTotal());
            }
            tax = total.min(netTotal);
            Order o = order.get();
            o.setNetTotal(netTotal);
            o.setTotal(total);
            o.setTax(tax);
            orderRepository.updateOrder(o);
        }
        throw new NoOrderFoundException();

    }

    private void calculateItem(OrderItem item, UUID productId) {
        ProductDTO product = productService.getProduct(productId);
        BigDecimal netPrice = product.getProductNetPrice();
        BigDecimal taxInPercent = product.getTaxPercent();
        int quantity = item.getQuantity();
        BigDecimal netTotal = Calculator.calculateNetTotal(netPrice, quantity);
        BigDecimal total = Calculator.calculateTotal(netTotal, taxInPercent);
        item.setNetPrice(netPrice);
        item.setNetTotal(netTotal);
        item.setTotal(total);
    }

}
