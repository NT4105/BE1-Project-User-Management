package main.java.com.project.util;

import main.java.com.project.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private static final String FILE_NAME = "src/main/resources/user.dat";

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.err.println("File does not exist.");
            return users;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                List<?> list = (List<?>) obj;
                for (Object item : list) {
                    if (item instanceof User) {
                        users.add((User) item);
                    } else {
                        System.err.println("Invalid data in file: Not a User instance.");
                    }
                }
            } else {
                System.err.println("Invalid data in file: Not a List instance.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load users: " + e.getMessage());
        }
        return users;
    }

    public static void saveUsers(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Remember to save your account.");
        }
    }
}