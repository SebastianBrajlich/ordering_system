package com.ltrlabs.customer.repository;

import com.ltrlabs.customer.mode.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    UUID createCustomer(Customer customer);
    void removeCustomer(UUID customerID);
    Optional<Customer> updateCustomer(Customer customer);
    Optional<Customer> getCustomer(UUID customerID);
    Optional<Customer> findCustomerByEmail(String email);
    List<Customer> getAllCustomers();
    boolean isCustomerWithEmail(String email);
    boolean isCustomerWithId(UUID customerID);
}
