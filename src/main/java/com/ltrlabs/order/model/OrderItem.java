package com.ltrlabs.order.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 12L;

    private UUID itemID;
    private UUID productID;
    private UUID orderID;
    private BigDecimal netPrice;
    private BigDecimal netTotal;
    private BigDecimal total;
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(itemID, orderItem.itemID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(itemID);
    }
}
