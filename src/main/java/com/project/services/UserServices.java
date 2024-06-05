package main.java.com.project.services;

import main.java.com.project.model.User;
import main.java.com.project.util.FileUtil;
import main.java.com.project.util.ValidationUtil;
import main.java.com.project.exception.UserNotFoundException;
import main.java.com.project.exception.UserAlreadyExistsException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.logging.Level;



public class UserServices {
    private List<User> users;
    private static final Logger logger = Logger.getLogger(UserServices.class.getName());

    public UserServices() {
        this.users = FileUtil.loadUsers();
    }
    public void createUser(User user) {
        try {
            if (ValidationUtil.isValidUser(user)) {
                user.setPassword(user.getPassword()); // Hash the password before adding the user
                users.add(user);
                saveUsersToFile();
                logger.log(Level.INFO,"User created successfully: " + user.getUsername());
            } else {
                logger.log(Level.WARNING,"Invalid user data: " + user);
            }
        } catch (UserAlreadyExistsException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    public boolean checkUser(String userNameToCheck) {
        try {
            List<User> users = FileUtil.loadUsers(); // Load users from file
            boolean userExists = users.stream()
                    .anyMatch(u -> u.getUsername().equals(userNameToCheck));

            if (userExists) {
                System.out.println("Exist User");
            } else {
                System.out.println("No User Found!");
            }

            return userExists;
        } catch (Exception e) {
            System.out.println("Error checking user: " + e.getMessage());
            return false;
        }
    }

    public List<User> searchUser(String searchName) {
        try {
            List<User> foundUsers = users.stream()
                    .filter(u -> u.getFirstname().contains(searchName) || u.getLastname().contains(searchName))
                    .collect(Collectors.toList());

            if (foundUsers.isEmpty()) {
                System.out.println("Have no any user");
            } else {
                // Print the list of users ordered by first name
                foundUsers.stream()
                        .sorted(Comparator.comparing(User::getFirstname))
                        .forEach(User::displayUserInfo);
            }
            return foundUsers;
        } catch (Exception e) {
            System.out.println("Error searching user: " + e.getMessage());
            return null;
        }
    }

    public void updateUser(User user) {
        try {
            Optional<User> existingUserOptional = users.stream()
                    .filter(u -> u.getUsername().equals(user.getUsername()))
                    .findFirst();
    
            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();
                updateExistingUser(existingUser, user);
                saveUsersToFile();
                logger.log(Level.INFO,"User updated successfully: " + user.getUsername());
            } else {
                String message = "Username does not exist.";
                logger.log(Level.WARNING,message);
                throw new UserNotFoundException(message);
            }
        } catch (UserNotFoundException e) {
            System.out.println("Error updating user: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error updating user: " + e.getMessage());
        }
    }
    

    private void updateExistingUser(User existingUser, User newUser) {
        existingUser.setFirstname(newUser.getFirstname());
        existingUser.setLastname(newUser.getLastname());
        existingUser.setPassword(newUser.getPassword());
        existingUser.setConfirmpassword(newUser.getConfirmpassword());
        existingUser.setPhone(newUser.getPhone());
        existingUser.setEmail(newUser.getEmail());
    }

    public void deleteUser(String username) {
        try {
            User user = users.stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .orElseThrow(() -> {
                        String message = "Username does not exist.";
                        logger.warning(message);
                        return new UserNotFoundException(message);
                    });
            users.remove(user);
            saveUsersToFile();
            logger.log(Level.INFO,"User deleted successfully: " + username);
        } catch (UserNotFoundException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error deleting user: " + e.getMessage());
        }
    }
    
    public void saveUsersToFile() {
        if (!users.isEmpty()) { 
            FileUtil.saveUsers(users);
        } else {
            System.out.println("No users to save."); 
        }
    }

    public List<User> printUsers() {
        
        users = FileUtil.loadUsers();
        
        if (!users.isEmpty()) {

            users.stream()
                .sorted(Comparator.comparing(User::getFirstname))
                .forEach(User::displayUserInfo);
        } else {
            System.out.println("No users to print.");
        }
        return users; 
    }
}