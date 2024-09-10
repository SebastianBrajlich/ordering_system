package com.ltrlabs.customer.service.mapper;

import com.ltrlabs.customer.mode.Customer;
import com.ltrlabs.customer.service.dto.CustomerDTO;
import com.ltrlabs.user.service.dto.UserDTO;

public class CustomerServiceMapper {

    public static Customer mapToCustomer(CustomerDTO customerDTO) {
        if (customerDTO == null) return null;
        return Customer.builder()
                .customerID(customerDTO.getCustomerID())
                .firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .email(customerDTO.getEmail())
                .build();
    }

    public static CustomerDTO mapToCustomerDTO(Customer customer) {
        if (customer == null) return null;
        return CustomerDTO.builder()
                .customerID(customer.getCustomerID())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .build();
    }

    public static CustomerDTO mapToCustomerDTO(UserDTO userDTO) {
        if (userDTO == null) return null;
        return CustomerDTO.builder()
                .customerID(userDTO.getUserID())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .build();
    }
}
