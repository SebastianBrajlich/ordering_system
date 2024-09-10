package com.ltrlabs.user.service.mapper;

import com.ltrlabs.user.model.User;
import com.ltrlabs.user.service.dto.UserDTO;

public class UserServiceMapper {

    public static User mapToUser(UserDTO userDTO) {
        if (userDTO == null) return null;
        return User.builder()
                .userID(userDTO.getUserID())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }
}
