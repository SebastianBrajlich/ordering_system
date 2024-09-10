package com.ltrlabs.product.service.mapper;

import com.ltrlabs.product.model.Product;
import com.ltrlabs.product.service.dto.ProductDTO;

public class ProductServiceMapper {

    public static Product mapToProduct(ProductDTO productDTO) {
        if (productDTO == null) return null;
        return Product.builder()
                .productID(productDTO.getProductID())
                .productName(productDTO.getProductName())
                .productNetPrice(productDTO.getProductNetPrice())
                .taxPercent(productDTO.getTaxPercent())
                .quantityAvailable(productDTO.getQuantityAvailable())
                .build();
    }

    public static ProductDTO mapToProductDTO(Product product) {
        if (product == null) return null;
        return ProductDTO.builder()
                .productID(product.getProductID())
                .productName(product.getProductName())
                .productNetPrice(product.getProductNetPrice())
                .taxPercent(product.getTaxPercent())
                .quantityAvailable(product.getQuantityAvailable())
                .build();
    }
}
