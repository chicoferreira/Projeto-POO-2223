package com.marketplace.vintage.user;

import com.marketplace.vintage.exceptions.EntityAlreadyExistsException;
import com.marketplace.vintage.exceptions.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

    private final Map<UUID, User> usersById;
    private final Map<String, User> usersByEmail;

    public UserManager() {
        this.usersById = new HashMap<>();
        this.usersByEmail = new HashMap<>();
    }

    public void registerUser(@NotNull User user) {
        UUID userId = user.getId();
        String userEmail = user.getEmail().toLowerCase();

        if (this.usersById.containsKey(userId)) {
            throw new EntityAlreadyExistsException("A user with that id already exists");
        }

        if (this.usersByEmail.containsKey(userEmail)) {
            throw new EntityAlreadyExistsException("A user with that email already exists");
        }

        this.usersById.put(userId, user);
        this.usersByEmail.put(userEmail, user);
    }

    public User getUserById(UUID id) {
        User user = this.usersById.get(id);

        if (user == null) {
            throw new EntityNotFoundException("A user with the id " + id + " was not found");
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
