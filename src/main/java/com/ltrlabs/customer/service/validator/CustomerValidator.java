package com.ltrlabs.customer.service.validator;

import com.ltrlabs.customer.service.dto.CustomerDTO;

import java.util.Optional;

public class CustomerValidator {

    public static boolean isCustomerValidForRegistration(CustomerDTO customer) {
        return Optional.ofNullable(customer)
                .filter(c -> !c.isFirstNameEmpty())
                .filter(c -> !c.isLastNameEmpty())
                .filter(c -> !c.isEmailEmpty())
                .isPresent();
    }
}
