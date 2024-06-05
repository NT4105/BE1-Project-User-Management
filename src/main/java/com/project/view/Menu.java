package main.java.com.project.view;

import main.java.com.project.model.User;
import main.java.com.project.services.UserServices;
import main.java.com.project.exception.UserNotFoundException;
import main.java.com.project.exception.UserAlreadyExistsException;
import java.util.Scanner;

public class Menu {
    private final UserServices userService;
    private final Scanner sc;

    public Menu() {
        this.userService = new UserServices();
        this.sc = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("1. Create user account");
            System.out.println("2. Check existing user");
            System.out.println("3. Search user by name");
            System.out.println("4. Update user");
            System.out.println("5. Delete user");
            System.out.println("6. Save users to file");
            System.out.println("7. Print all users from file");
            System.out.println("8. Quit");
            System.out.print("Enter your choice: ");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 1 && choice <= 8) {
                    try {
                        switch (choice) {
                            case 1:
                                createUser();
                                break;
                            case 2:
                                checkUser();
                                break;
                            case 3:
                                searchUser();
                                break;
                            case 4:
                                updateUser();
                                break;
                            case 5:
                                deleteUser();
                                break;
                            case 6:
                                saveUsersToFile();
                                break;
                            case 7:
                                printUsers();
                                break;
                            case 8:
                                System.exit(0);
                        }
                    } catch (UserNotFoundException | UserAlreadyExistsException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Invalid choice, please enter a number between 1 and 8.");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                sc.next();
            }
        }
    }

    private void createUser() {
        while (true) {
            String username = inputWithOptionToReturn("Enter username: ");
            if (username == null) return;

            String firstname = inputWithOptionToReturn("Enter first name: ");
            if (firstname == null) return;

            String lastname = inputWithOptionToReturn("Enter last name: ");
            if (lastname == null) return;

            String password = inputWithOptionToReturn("Enter password: ");
            if (password == null) return;

            String confirmpassword = inputWithOptionToReturn("Confirm your password: ");
            if (confirmpassword == null) return;

            String phone = inputWithOptionToReturn("Enter phone number: ");
            if (phone == null) return;

            String email = inputWithOptionToReturn("Enter email: ");
            if (email == null) return;

            User user = new User(username, firstname, lastname, password, confirmpassword, phone, email);
            userService.createUser(user);
            if (confirmBackToMenu()) {
                break;
            }
        }
    }

    private void checkUser() {
        while (true) {
            String username = inputWithOptionToReturn("Enter username to check: ");
            if (username == null) break;
            userService.checkUser(username);
            if (confirmBackToMenu()) {
                break;
            }
        }
    }

    private void searchUser() {
        while (true) {
            String name = inputWithOptionToReturn("Enter name (first/lastname) to search: ");
            if (name == null) break;
            userService.searchUser(name);
            if (confirmBackToMenu()) {
                break;
            }
        }
    }

    private void updateUser() {
        while (true) {
            String username = inputWithOptionToReturn("Enter username to update: ");
            if (username == null) break;

            String firstname = inputWithOptionToReturn("Enter new first name: ");
            if (firstname == null) break;

            String lastname = inputWithOptionToReturn("Enter new last name: ");
            if (lastname == null) break;

            String password = inputWithOptionToReturn("Enter new password: ");
            if (password == null) break;

            String confirmpassword = inputWithOptionToReturn("Confirm your new password: ");
            if (confirmpassword == null) break;

            String phone = inputWithOptionToReturn("Enter new phone number: ");
            if (phone == null) break;

            String email = inputWithOptionToReturn("Enter new email: ");
            if (email == null) break;

            User user = new User(username, firstname, lastname, password, confirmpassword, phone, email);
            userService.updateUser(user);
            if (confirmBackToMenu()) {
                break;
            }
        }
    }

    private void deleteUser() {
        while (true) {
            String username = inputWithOptionToReturn("Enter username to delete: ");
            if (username == null) break;
            userService.deleteUser(username);
            if (confirmBackToMenu()) {
                break;
            }
        }
    }

    private void saveUsersToFile() {
        userService.saveUsersToFile();
        System.out.println("Users saved to file successfully.");
    }

    private void printUsers() {
        userService.printUsers().forEach(System.out::println);
    }

    private String inputWithOptionToReturn(String prompt) {
        System.out.print(prompt);
        String input = sc.nextLine().trim();
        if (input.equalsIgnoreCase("Back")) {
            if (confirmBackToMenu()) {
                return null;
            } else {
                return inputWithOptionToReturn(prompt);
            }
        }
        return input;
    }

    private boolean confirmBackToMenu() {
        while (true) {
            System.out.print("Do you want to go back to the menu? (y/n): ");
            String choice = sc.nextLine().trim().toLowerCase();
            if (choice.equals("y")) {
                return true;
            } else if (choice.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid choice, please enter 'y' or 'n'.");
            }
        }
    }
}