package com.ltrlabs.user.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UserDTO {

    private UUID userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isCustomer;

    public boolean isFirstNameEmpty() {
        return firstName == null || firstName.isEmpty();
    }

    public boolean isLastNameEmpty() {
        return lastName == null || lastName.isEmpty();
    }

    public boolean isEmailEmpty() {
        return email == null || email.isEmpty();
    }

    public boolean isPasswordEmpty() {
        return email == null || email.isEmpty();
    }

}
