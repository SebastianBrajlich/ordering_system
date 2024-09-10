package com.ltrlabs.product.service.validator;

import com.ltrlabs.product.service.dto.ProductDTO;

import java.util.Optional;

public class ProductValidator {

    public static boolean isProductValidForRegistration(ProductDTO product) {
        return Optional.ofNullable(product)
                .filter(p -> !p.isProductNameEmpty())
                .filter(p -> p.getProductNetPrice() != null)
                .filter(p -> p.getTaxPercent() != null)
                .isPresent();
    }
}
