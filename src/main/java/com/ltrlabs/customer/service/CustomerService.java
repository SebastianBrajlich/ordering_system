package com.ltrlabs.customer.service;

import com.ltrlabs.customer.mode.Customer;
import com.ltrlabs.customer.repository.CustomerRepository;
import com.ltrlabs.customer.service.dto.CustomerDTO;
import com.ltrlabs.customer.service.exception.CustomerExistException;
import com.ltrlabs.customer.service.exception.CustomerNotValidException;
import com.ltrlabs.customer.service.exception.NoSuchCustomerException;
import com.ltrlabs.customer.service.mapper.CustomerServiceMapper;
import com.ltrlabs.customer.service.validator.CustomerValidator;
import com.ltrlabs.user.service.exception.InvalidPasswordException;
import com.ltrlabs.user.service.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public UUID registerCustomer(CustomerDTO customer, String password) throws CustomerExistException, CustomerNotValidException, InvalidPasswordException{
        if (!CustomerValidator.isCustomerValidForRegistration(customer)) throw new CustomerNotValidException();
        if (!UserValidator.isPasswordValid(password)) throw new InvalidPasswordException();
        if (isCustomerRegistered(customer.getEmail())) throw new CustomerExistException();
        Customer c = CustomerServiceMapper.mapToCustomer(customer);
        c.setPassword(password);
        return customerRepository.createCustomer(c);
    }

    public UUID loginCustomer(String email, String password) throws NoSuchCustomerException, InvalidPasswordException{
        Optional<Customer> c = customerRepository.findCustomerByEmail(email);
        if (c.isPresent()) {
            Customer customer = c.get();
            if (UserValidator.isPasswordValid(customer.getPassword(), password)) return customer.getCustomerID();
            else throw new InvalidPasswordException();
        }
        throw new NoSuchCustomerException();

    }

    public Optional<CustomerDTO> getCustomer(UUID customerID) {
        return customerRepository.getCustomer(customerID).map(CustomerServiceMapper::mapToCustomerDTO);
    }

    public boolean isCustomerRegistered(UUID customerID) {
        return customerRepository.isCustomerWithId(customerID);
    }

    private boolean isCustomerRegistered(String email) {
        return customerRepository.isCustomerWithEmail(email);
    }
}
