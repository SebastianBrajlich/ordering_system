package com.ltrlabs.customer.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CustomerDTO {

    private UUID customerID;
    private String firstName;
    private String lastName;
    private String email;

    public boolean isFirstNameEmpty() {
        return firstName == null || firstName.isEmpty();
    }

    public boolean isLastNameEmpty() {
        return lastName == null || lastName.isEmpty();
    }

    public boolean isEmailEmpty() {
        return email == null || email.isEmpty();
    }
}
