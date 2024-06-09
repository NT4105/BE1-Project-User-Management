package main.java.com.project.util;

import main.java.com.project.model.User;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    public static boolean isValidUser(User user) {
        return isValidUsername(user.getUsername()) &&
               isValidPassword(user.getPassword()) &&
               isValidPhone(user.getPhone()) &&
               isValidEmail(user.getEmail()) &&
               isPasswordConfirmed(user.getPassword(), user.getConfirmpassword());
    }

    public static boolean isValidUsername(String username) {
        return username != null && username.length() >= 5 && !username.contains(" ");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6 && !password.contains(" ");
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isPasswordConfirmed(String password, String confirmpassword) {
        return password != null && password.equals(confirmpassword);
    }
}