package com.ltrlabs.user.service;

import com.ltrlabs.customer.service.exception.CustomerExistException;
import com.ltrlabs.customer.service.exception.CustomerNotValidException;
import com.ltrlabs.user.model.User;
import com.ltrlabs.user.repository.UserRepository;
import com.ltrlabs.user.service.dto.UserDTO;
import com.ltrlabs.user.service.exception.InvalidPasswordException;
import com.ltrlabs.user.service.exception.NoSuchUserException;
import com.ltrlabs.user.service.exception.UserExistException;
import com.ltrlabs.user.service.exception.UserNotValidException;
import com.ltrlabs.user.service.mapper.UserServiceMapper;
import com.ltrlabs.user.service.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UUID registerUser(UserDTO user) throws CustomerExistException, CustomerNotValidException, InvalidPasswordException {
        if (!UserValidator.isUserValidForRegistration(user)) throw new UserNotValidException();
        if (!UserValidator.isPasswordValid(user.getPassword())) throw new InvalidPasswordException();
        if (isUserRegistered(user.getEmail())) throw new UserExistException();
        User u = UserServiceMapper.mapToUser(user);
        return userRepository.createUser(u);
    }

    public UUID loginUser(String email, String password) throws NoSuchUserException, InvalidPasswordException {
        Optional<User> u = userRepository.findUserByEmail(email);
        if (u.isPresent()) {
            User user = u.get();
            if (UserValidator.isPasswordValid(user.getPassword(), password)) return user.getUserID();
            else throw new InvalidPasswordException();
        }
        throw new NoSuchUserException();

    }

    private boolean isUserRegistered(String email) {
        return userRepository.isUserWithEmail(email);
    }
}
