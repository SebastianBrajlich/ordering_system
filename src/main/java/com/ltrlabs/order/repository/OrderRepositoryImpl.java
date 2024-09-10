package com.ltrlabs.order.repository;

import com.ltrlabs.configuration.ResourceConfig;
import com.ltrlabs.order.model.Order;
import com.ltrlabs.product.model.Product;
import com.ltrlabs.utils.ResourceManager;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    private final Map<UUID, Order> db = new HashMap<>();
    private final ResourceConfig resourceConfig;

    @Override
    public UUID createOrderForCustomer(UUID customerID) {
        UUID uuid = generateUUID();
        Order order = Order.builder().customerID(customerID).orderID(uuid).build();
        db.put(uuid, order);
        return uuid;
    }

    @Override
    public void removeOrder(UUID orderID) {
        db.remove(orderID);
    }

    @Override
    public void updateOrder(Order order) {
        if (order != null && order.getOrderID() != null && db.containsKey(order.getOrderID())) {
            db.replace(order.getOrderID(), order);
        }
    }

    @Override
    public Optional<Order> getOrder(UUID orderID) {
        return Optional.ofNullable(db.get(orderID));
    }

    @Override
    public List<Order> getCustomerOrders(UUID customerID) {
        return db.values()
                .stream()
                .filter(o -> o.getCustomerID().equals(customerID))
                .toList();
    }

    @Override
    public List<Order> getAllOrders() {
        return db.values().stream().toList();
    }

    @Override
    public boolean isOrderCreated(UUID orderID) {
        if (orderID == null) return false;
        return db.containsKey(orderID);
    }

    private UUID generateUUID() {
        UUID uuid = null;
        boolean uniqueUUID = false;
        while (!uniqueUUID) {
            uuid = UUID.randomUUID();
            uniqueUUID = !db.containsKey(uuid);
        }
        return uuid;
    }

    private void addOrders(List<Order> orders) {
        orders.forEach(o -> db.put(o.getOrderID(), o));
    }

    @PostConstruct
    public void init() {
        readOrders();
    }

    @PreDestroy
    public void destroy() {
        persistOrders();
    }

    private void readOrders() {
        String path = resourceConfig.getOrdersFilePath();
        if (ResourceManager.isFileEmpty(path)) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Object object = ois.readObject();
            if (object != null) {
                List<Order> orders = (List<Order>) object;
                addOrders(orders);
            }
        } catch (FileNotFoundException e) {
            log.error("Some problem occurred while accessing path: {}. Exception message: {}", path, e.getMessage());
        } catch (IOException e) {
            log.error("Problem occurred while using ObjectInputStream. Exception message: {}", e.getMessage());
        } catch (SecurityException e) {
            log.error("Security problem occurred while deserializing customers. Exception message: {}", e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error("Class of deserialized object can't be found. Exception message: {}", e.getMessage());
        }
    }

    private void persistOrders() {
        String path = resourceConfig.getOrdersFilePath();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            List<Order> orders = getAllOrders();
            if (orders != null && !orders.isEmpty()) {
                oos.writeObject(orders);
                oos.flush();
            }
        } catch (FileNotFoundException e) {
            log.error("Some problem occurred while accessing path: {}. Exception message: {}", path, e.getMessage());
        } catch (IOException e) {
            log.error("Problem occurred while using ObjectOutputStream. Exception message: {}", e.getMessage());
        } catch (SecurityException e) {
            log.error("Security problem occurred while serializing customers. Exception message: {}", e.getMessage());
        }
    }
}
