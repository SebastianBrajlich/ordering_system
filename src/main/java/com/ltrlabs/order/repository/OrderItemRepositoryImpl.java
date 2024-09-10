package com.ltrlabs.order.repository;

import com.ltrlabs.configuration.ResourceConfig;
import com.ltrlabs.order.model.OrderItem;
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
public class OrderItemRepositoryImpl implements OrderItemRepository{

    private final Map<UUID, OrderItem> db = new HashMap<>();
    private final ResourceConfig resourceConfig;

    @Override
    public UUID createItem(OrderItem item) {
        UUID uuid = generateUUID();
        item.setItemID(uuid);
        db.put(uuid, item);
        return uuid;
    }

    @Override
    public void removeItem(UUID itemID) {
        db.remove(itemID);
    }

    @Override
    public void updateItem(OrderItem item) {
       if (item != null && item.getItemID() != null && db.containsKey(item.getItemID())) {
           db.replace(item.getItemID(), item);
       }
    }

    @Override
    public Optional<OrderItem> getItem(UUID itemID) {
        return Optional.ofNullable(db.get(itemID));
    }

    @Override
    public List<OrderItem> getOrderItems(UUID orderID) {
        return db.values()
                .stream()
                .filter(i -> i.getItemID().equals(orderID))
                .toList();
    }

    @Override
    public List<OrderItem> getAllItems() {
        return db.values().stream().toList();
    }

    @Override
    public void removeItemsByOrderId(UUID orderID) {
        db.values()
                .stream()
                .filter(i -> i.getItemID().equals(orderID))
                .forEach(i -> db.remove(i.getItemID()));
    }

    @Override
    public boolean isItemInOrder(UUID orderID, UUID itemID) {
        if (db.containsKey(itemID)) return db.get(itemID).getOrderID().equals(orderID);
        return false;
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

    private void addItems(List<OrderItem> items) {
        items.forEach(i -> db.put(i.getItemID(), i));
    }

    @PostConstruct
    public void init() {
        readItems();
    }

    @PreDestroy
    public void destroy() {
        persistItems();
    }

    private void readItems() {
        String path = resourceConfig.getItemsFilePath();
        if (ResourceManager.isFileEmpty(path)) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Object object = ois.readObject();
            if (object != null) {
                List<OrderItem> items = (List<OrderItem>) object;
                addItems(items);
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

    private void persistItems() {
        String path = resourceConfig.getItemsFilePath();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            List<OrderItem> orders = getAllItems();
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
