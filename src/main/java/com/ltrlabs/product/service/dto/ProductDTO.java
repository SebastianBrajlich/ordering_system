package com.ltrlabs.product.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductDTO {

    private UUID productID;
    private String productName;
    private BigDecimal productNetPrice;
    private BigDecimal taxPercent;
    private int quantityAvailable;

    public boolean isProductNameEmpty() {
        return productName == null || productName.isEmpty();
    }
}
