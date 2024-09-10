package com.ltrlabs.product.repository;

import com.ltrlabs.product.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    UUID createProduct(Product product);
    boolean removeProduct(UUID productID);
    Optional<Product> updateProduct(Product product);
    Optional<Product> getProduct(UUID productId);
    List<Product> getAllProducts();

}