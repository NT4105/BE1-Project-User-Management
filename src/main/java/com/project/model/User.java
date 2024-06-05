package main.java.com.project.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.regex.Pattern;

public class User {
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String confirmpassword;
    private String phone;
    private String email;
    private String validationMessage;

    // Constructor
    public User(String username, String firstname, String lastname, String password, String confirmpassword, String phone, String email) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.confirmpassword = confirmpassword;
        this.phone = phone;
        this.email = email;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Method to validate user information with error messages
    public boolean isValidUser() {
        if (username == null || username.isEmpty()) {
            validationMessage = "Username is required.";
            return false;
        }
        if (firstname == null || firstname.isEmpty()) {
            validationMessage = "First name is required.";
            return false;
        }
        if (lastname == null || lastname.isEmpty()) {
            validationMessage = "Last name is required.";
            return false;
        }
        if (password == null || password.isEmpty()) {
            validationMessage = "Password is required.";
            return false;
        }
        if (!password.equals(confirmpassword)) {
            validationMessage = "Passwords do not match.";
            return false;
        }
        if (phone == null || phone.isEmpty()) {
            validationMessage = "Phone number is required.";
            return false;
        }
        if (email == null || email.isEmpty()) {
            validationMessage = "Email is required.";
            return false;
        }
        if (!isEmailValid(email)) {
            validationMessage = "Invalid email format.";
            return false;
        }
        validationMessage = "User information is valid.";
        return true;
    }

    // Method to get validation message
    public String getValidationMessage() {
        return validationMessage;
    }

    // Private method to validate email format
    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Method to display user information
    public void displayUserInfo() {
        System.out.println("Username: " + username);
        System.out.println("First Name: " + firstname);
        System.out.println("Last Name: " + lastname);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
    }

    // Encrypt password(SHA256) before saving to file.	
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}