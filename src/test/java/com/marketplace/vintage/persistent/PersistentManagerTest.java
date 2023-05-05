package com.marketplace.vintage.persistent;

import com.marketplace.vintage.exceptions.EntityNotFoundException;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.user.UserManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;

class PersistentManagerTest {

    private File file;

    @BeforeEach
    void setUp() throws IOException {
        this.file = Files.createTempFile("test", "test").toFile();
        this.file.deleteOnExit();
    }

    @Test
    void testPersistentManager() {
        UserManager userManager = new UserManager();
        userManager.registerUser(new User(UUID.randomUUID(), "username", "username@email.com", "Test", "199", "99"));

        PersistentManager persistentManager = new PersistentManager(file);
        persistentManager.addReferenceToSave("userManager", userManager);
        persistentManager.save();

        Map<String, Object> map = persistentManager.loadPersistentData();
        UserManager loadedUserManager = (UserManager) map.get("userManager");

        Assertions.assertNotNull(loadedUserManager);
        User user = loadedUserManager.getUserByUsername("username");
        Assertions.assertNotNull(user);

        Assertions.assertEquals("username", user.getUsername());
        Assertions.assertEquals("username@email.com", user.getEmail());
        Assertions.assertEquals("Test", user.getName());
        Assertions.assertEquals("199", user.getAddress());
        Assertions.assertEquals("99", user.getTaxNumber());
    }

    @Test
    void testOverridesPersistentManager() {
        PersistentManager persistentManager = new PersistentManager(file);
        {
            UserManager userManager = new UserManager();
            userManager.registerUser(new User(UUID.randomUUID(), "username", "username@email.com", "Test", "199", "99"));

            persistentManager.addReferenceToSave("userManager", userManager);
            persistentManager.save();
        }

        UserManager userManager = new UserManager();
        persistentManager.addReferenceToSave("userManager", userManager);
        persistentManager.save();

        Map<String, Object> map = persistentManager.loadPersistentData();
        UserManager loadedUserManager = (UserManager) map.get("userManager");
        Assertions.assertThrowsExactly(EntityNotFoundException.class, () -> loadedUserManager.getUserByUsername("username"));
    }

}