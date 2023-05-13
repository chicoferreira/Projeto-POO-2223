package com.marketplace.vintage.user;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;

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

        validateUsername(userUsername);

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

    public boolean existsUserWithUsername(String username) {
        return this.usersByUsername.containsKey(username);
    }

    public void validateUsername(String username) {
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

        return user.clone();
    }

    public User getUserByUsername(String userUsername) {
        User user = this.usersByUsername.get(userUsername);

        if (user == null) {
            throw new EntityNotFoundException("A user with the username " + userUsername + " was not found");
        }

        return user.clone();
    }

    public User getUserByEmail(String email) {
        User user = this.usersByEmail.get(email.toLowerCase());

        if (user == null) {
            throw new EntityNotFoundException("A user with the email " + email + " was not found");
        }

        return user.clone();
    }

    public void updateUser(User user) {
        if (!this.usersById.containsKey(user.getId())) {
            throw new EntityNotFoundException("A user with the id " + user.getId() + " was not found");
        }

        if (!this.usersByUsername.containsKey(user.getUsername())) {
            throw new EntityNotFoundException("A user with the username " + user.getUsername() + " was not found");
        }

        if (!this.usersByEmail.containsKey(user.getEmail())) {
            throw new EntityNotFoundException("A user with the email " + user.getEmail() + " was not found");
        }

        this.usersById.put(user.getId(), user);
        this.usersByUsername.put(user.getUsername(), user);
        this.usersByEmail.put(user.getEmail(), user);
    }

    public List<User> getAll() {
        return new ArrayList<>(this.usersById.values());
    }

}
