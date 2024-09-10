package com.ltrlabs.auth.service;

import com.ltrlabs.customer.service.CustomerService;
import com.ltrlabs.customer.service.exception.CustomerExistException;
import com.ltrlabs.customer.service.exception.CustomerNotValidException;
import com.ltrlabs.customer.service.mapper.CustomerServiceMapper;
import com.ltrlabs.user.service.UserService;
import com.ltrlabs.user.service.dto.UserDTO;
import com.ltrlabs.user.service.exception.InvalidPasswordException;
import com.ltrlabs.user.service.exception.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerService customerService;
    private final UserService userService;

    public UUID registerUser(UserDTO user) throws CustomerExistException, CustomerNotValidException, InvalidPasswordException {
        UUID uuid = null;
        if (user == null) return uuid;
        if (user.isCustomer()) {
            uuid = customerService.registerCustomer(CustomerServiceMapper.mapToCustomerDTO(user), user.getPassword());
        }
        else {
            uuid = userService.registerUser(user);
        }
        return uuid;
    }

    public UUID logInUser(String email, String password, boolean isCustomer) throws NoSuchUserException, InvalidPasswordException {
        if (isCustomer) return customerService.loginCustomer(email, password);
        return userService.loginUser(email, password);
    }
}
