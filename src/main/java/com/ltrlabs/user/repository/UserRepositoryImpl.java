package com.ltrlabs.user.repository;

import com.ltrlabs.configuration.ResourceConfig;
import com.ltrlabs.user.model.User;
import com.ltrlabs.utils.ResourceManager;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository{

    private final Map<UUID, User> db = new HashMap<>();
    private final ResourceConfig resourceConfig;

    @Override
    public UUID createUser(User user) {
        UUID uuid = generateUUID();
        user.setUserID(uuid);
        db.put(uuid, user);
        return uuid;
    }

    @Override
    public void removeUser(UUID userID) {
        db.remove(userID);
    }

    @Override
    public Optional<User> updateUser(User user) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUser(UUID userID) {
        return Optional.ofNullable(db.get(userID));
    }

    @Override
    public List<User> getAllUsers() {
        return db.values().stream().toList();
    }

    @Override
    public boolean isUserWithEmail(String email) {
        return db.values().stream().anyMatch(u -> u.getEmail().equals(email));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return db.values()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    private UUID generateUUID() {
        UUID uuid = null;
        boolean uniqueUUID = false;
        while (!uniqueUUID) {
            uuid = UUID.randomUUID();
            uniqueUUID = !db.containsKey(uuid);
        }
        return uuid;
    }

    private void addUsers(List<User> users) {
        users.forEach(u -> db.put(u.getUserID(), u));
    }

    @PostConstruct
    public void init() {
        readUsers();
    }

    @PreDestroy
    public void destroy() {
        persistUsers();
    }

    private void readUsers() {
        String path = resourceConfig.getUsersFilePath();
        if (ResourceManager.isFileEmpty(path)) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Object object = ois.readObject();
            if (object != null) {
                List<User> users = (List<User>) object;
                addUsers(users);
            }
        } catch (FileNotFoundException e) {
            log.error("Some problem occurred while accessing path: {}. Exception message: {}", path, e.getMessage());
        } catch (IOException e) {
            log.error("Problem occurred while using ObjectInputStream. Exception message: {}", e.getMessage());
        } catch (SecurityException e) {
            log.error("Security problem occurred while deserializing customers. Exception message: {}", e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error("Class of deserialized object can't be found. Exception message: {}", e.getMessage());
        }
    }

    private void persistUsers() {
        String path = resourceConfig.getUsersFilePath();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            List<User> users = getAllUsers();
            if (users != null && !users.isEmpty()) {
                oos.writeObject(users);
                oos.flush();
            }
        } catch (FileNotFoundException e) {
            log.error("Some problem occurred while accessing path: {}. Exception message: {}", path, e.getMessage());
        } catch (IOException e) {
            log.error("Problem occurred while using ObjectOutputStream. Exception message: {}", e.getMessage());
        } catch (SecurityException e) {
            log.error("Security problem occurred while serializing customers. Exception message: {}", e.getMessage());
        }
    }
}
