package com.ltrlabs.product.repository;

import com.ltrlabs.configuration.ResourceConfig;
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
public class ProductRepositoryImpl implements ProductRepository{

    private final Map<UUID, Product> db = new HashMap<>();
    private final ResourceConfig resourceConfig;

    @Override
    public UUID createProduct(Product product) {
        return null;
    }

    @Override
    public boolean removeProduct(UUID productID) {
        return false;
    }

    @Override
    public Optional<Product> updateProduct(Product product) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> getProduct(UUID productId) {
        return Optional.empty();
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
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

    private void addProducts(List<Product> products) {
        products.forEach(p -> db.put(p.getProductID(), p));
    }

    @PostConstruct
    public void init() {
        readProducts();
    }

    @PreDestroy
    public void destroy() {
        persistProducts();
    }

    private void readProducts() {
        String path = resourceConfig.getProductsFilePath();
        if (ResourceManager.isFileEmpty(path)) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Object object = ois.readObject();
            if (object != null) {
                List<Product> products = (List<Product>) object;
                addProducts(products);
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

    private void persistProducts() {
        String path = resourceConfig.getProductsFilePath();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            List<Product> products = getAllProducts();
            if (products != null && !products.isEmpty()) {
                oos.writeObject(products);
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
