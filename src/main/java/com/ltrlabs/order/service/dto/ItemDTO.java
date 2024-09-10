package com.ltrlabs.order.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ItemDTO {

    private UUID itemID;
    private UUID productID;
    private String productName;
    private BigDecimal netPrice;
    private int quantity;
    private BigDecimal netTotal;
    private BigDecimal total;
}
