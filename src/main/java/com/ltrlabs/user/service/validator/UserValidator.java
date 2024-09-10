package com.ltrlabs.user.service.validator;

import com.ltrlabs.user.service.dto.UserDTO;

import java.util.Optional;

public class UserValidator {

    private static final int PASSWORD_LENGTH = 6;

    public static boolean isUserValidForRegistration(UserDTO user) {
        return Optional.ofNullable(user)
                .filter(u -> !u.isFirstNameEmpty())
                .filter(u -> !u.isLastNameEmpty())
                .filter(u -> !u.isEmailEmpty())
                .filter(u -> !u.isPasswordEmpty())
                .isPresent();
    }

    public static boolean isPasswordValid(String password) {
        return Optional.ofNullable(password)
                .filter(p -> p.length() > PASSWORD_LENGTH)
                .isPresent();
    }

    public static boolean isPasswordValid(String passwordA, String passwordB) {
        return Optional.ofNullable(passwordA)
                .filter(p -> p.equals(passwordB))
                .isPresent();
    }
}
