package com.ltrlabs.customer.repository;

import com.ltrlabs.configuration.ResourceConfig;
import com.ltrlabs.customer.mode.Customer;
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
public class CustomerRepositoryImpl implements CustomerRepository{

    private final Map<UUID, Customer> db = new HashMap<>();
    private final ResourceConfig resourceConfig;

    @Override
    public UUID createCustomer(Customer customer) {
        UUID uuid = generateUUID();
        customer.setCustomerID(uuid);
        db.put(uuid, customer);
        return uuid;
    }

    @Override
    public void removeCustomer(UUID customerID) {
        db.remove(customerID);
    }

    @Override
    public Optional<Customer> updateCustomer(Customer customer) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> getCustomer(UUID customerID) {
        return Optional.ofNullable(db.get(customerID));
    }

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        return db.values()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return db.values().stream().toList();
    }

    @Override
    public boolean isCustomerWithEmail(String email) {
        return db.values().stream().anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean isCustomerWithId(UUID customerID) {
        return db.containsKey(customerID);
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

    private void addCustomers(List<Customer> customers) {
        customers.forEach(c -> db.put(c.getCustomerID(), c));
    }

    @PostConstruct
    public void init() {
        readCustomers();
    }

    @PreDestroy
    public void destroy() {
        persistCustomers();
    }

    private void readCustomers() {
        String path = resourceConfig.getCustomerFilePath();
        if (ResourceManager.isFileEmpty(path)) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Object object = ois.readObject();
            if (object != null) {
                List<Customer> customers = (List<Customer>) object;
                addCustomers(customers);
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

    private void persistCustomers() {
        String path = resourceConfig.getCustomerFilePath();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            List<Customer> customers = getAllCustomers();
            if (customers != null && !customers.isEmpty()) {
                oos.writeObject(customers);
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
