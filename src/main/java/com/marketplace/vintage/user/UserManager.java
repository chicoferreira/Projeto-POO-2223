package com.marketplace.vintage.user;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager implements Serializable {

    private final Map<UUID, User> usersById;
    private final Map<String, User> usersByUsername;
    private final Map<String, User> usersByEmail;

    public UserManager() {
        this.usersById = new HashMap<>();
        this.usersByUsername = new HashMap<>();
        this.usersByEmail = new HashMap<>();
    }

    public User createUser(String username, String email, String name, String address, String taxNumber) {
        User user = new User(username, email, name, address, taxNumber);
        this.registerUser(user);
        return user;
    }

    public void registerUser(@NotNull User user) {
        String userUsername = user.getUsername();
        String userEmail = user.getEmail().toLowerCase();

        checkUsername(userUsername);

        if (this.usersById.containsKey(user.getId())) {
            throw new EntityAlreadyExistsException("A user with that id already exists");
        }

        if (this.usersByEmail.containsKey(userEmail)) {
            throw new EntityAlreadyExistsException("A user with that email already exists");
        }

        this.usersById.put(user.getId(), user);
        this.usersByUsername.put(userUsername, user);
        this.usersByEmail.put(userEmail, user);
    }

    public boolean existsUserWithEmail(String email) {
        return this.usersByEmail.containsKey(email.toLowerCase());
    }

    public void checkUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("The user's username cannot be null or empty");
        }

        if (username.contains(" ")) {
            throw new IllegalArgumentException("The user's username cannot contain spaces");
        }

        if (this.usersByUsername.containsKey(username)) {
            throw new EntityAlreadyExistsException("A user with that username already exists");
        }
    }

    public User getUserById(UUID userId) {
        User user = this.usersById.get(userId);

        if (user == null) {
            throw new EntityNotFoundException("A user with the id " + userId + " was not found");
        }

        return user;
    }

    public User getUserByUsername(String userUsername) {
        User user = this.usersByUsername.get(userUsername);

        if (user == null) {
            throw new EntityNotFoundException("A user with the username " + userUsername + " was not found");
        }

        return user;
    }

    public User getUserByEmail(String email) {
        User user = this.usersByEmail.get(email.toLowerCase());

        if (user == null) {
            throw new EntityNotFoundException("A user with the email " + email + " was not found");
        }

        return user;
    }
}
