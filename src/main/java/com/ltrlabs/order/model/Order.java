package com.ltrlabs.order.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 11L;

    private UUID orderID;
    private BigDecimal netTotal;
    private BigDecimal total;
    private BigDecimal tax;
    private UUID customerID;
    private LocalDateTime time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderID, order.orderID);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderID);
    }
}
