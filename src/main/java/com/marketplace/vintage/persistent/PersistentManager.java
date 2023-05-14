package com.marketplace.vintage.persistent;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PersistentManager {

    private final File file;
    private final PersistentMap persistentData;

    public PersistentManager(@NotNull File file) {
        this.file = file;
        this.persistentData = new PersistentMap();
    }

    public void addReferenceToSave(String key, Object value) {
        this.persistentData.put(key, value);
    }

    private void ensureFileExists() {
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new RuntimeException("Could not create file");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        if (persistentData.isEmpty()) {
            throw new IllegalStateException("There is no data to save");
        }

        ensureFileExists();

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(persistentData);
            objectOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> loadPersistentData() {
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            return (PersistentMap) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Corrupted binary", e);
        }
    }

    public static class PersistentMap extends HashMap<String, Object> { // This exists to avoid type erasure
    }
}
