package com.ltrlabs.order.service.dto;

import com.ltrlabs.customer.service.dto.CustomerDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderDTO {

    private UUID orderID;
    private BigDecimal netTotal;
    private BigDecimal total;
    private BigDecimal tax;
    private CustomerDTO customer;
    private List<ItemDTO> items;
}
