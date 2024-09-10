package com.ltrlabs.user.repository;

import com.ltrlabs.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    UUID createUser(User user);
    void removeUser(UUID userID);
    Optional<User> updateUser(User user);
    Optional<User> getUser(UUID userID);
    List<User> getAllUsers();
    boolean isUserWithEmail(String email);
    Optional<User> findUserByEmail(String email);
}
