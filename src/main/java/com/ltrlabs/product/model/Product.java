package com.ltrlabs.product.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 13L;

    private UUID productID;
    private String productName;
    private BigDecimal productNetPrice;
    private BigDecimal taxPercent;
    private int quantityAvailable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productID, product.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productID);
    }
}
