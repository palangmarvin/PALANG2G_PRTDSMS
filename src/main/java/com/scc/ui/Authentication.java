package com.scc.ui;

import com.scc.dao.ActivityLogDao;
import com.scc.dao.UserDao;
import com.scc.model.user;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Authentication {
    private static final UserDao userDao = new UserDao();
    private static final Scanner scanner = new Scanner(System.in);

    public static void showAuthMenu() {
        int choice;
        do {
            System.out.println("\n=== PARISH SYSTEM AUTHENTICATION ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number (1-3).");
                scanner.next();
                System.out.print("Choose an option: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    user loggedInUser = login();
                    if (loggedInUser != null) {
                        navigateUserMenu(loggedInUser);
                    }
                }
                case 2 -> register();
                case 3 -> {
                    System.out.println("Exiting application. Goodbye!");
                    ActivityLogDao.logAction(null, "LOGOUT / EXIT", "Application closed.");
                }
                default -> System.out.println("Invalid choice. Please choose between 1 and 3.");
            }
        } while (choice != 3);
    }

    public static user login() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        user userObj = userDao.getUserByUsername(username);

        // Hash the entered password to compare it with the stored hash
        String hashedInputPassword = hashPassword(password);

        if (userObj != null && userObj.password().equals(hashedInputPassword)) {
            System.out.println("Login successful! Welcome, " + userObj.username() + " (" + userObj.roles() + ")");
            ActivityLogDao.logAction(userObj.id(), "LOGIN_SUCCESS", "User logged in successfully.");
            return userObj;
        }

        System.out.println("Invalid username or password.");
        ActivityLogDao.logAction(null, "LOGIN_FAILED", "Failed login attempt for username: " + username);
        return null;
    }

    public static void register() {
        System.out.println("\n=== REGISTER ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (Admin / Priest / ParishOfficer / AltarServer / Customer): ");
        String role = scanner.nextLine();

        // Hash the password before saving it to the database
        String hashedPassword = hashPassword(password);

        user newUser = new user(0, username, hashedPassword, role, "Active");

        if (userDao.addUser(newUser)) {
            System.out.println("Registration successful!");
            user createdUser = userDao.getUserByUsername(username);
            Integer newId = (createdUser != null) ? createdUser.id() : null;
            ActivityLogDao.logAction(newId, "USER_REGISTERED", "New account registered with role: " + role);
        } else {
            System.out.println("Registration failed. Username may already exist.");
            ActivityLogDao.logAction(null, "REGISTER_FAILED", "Failed registration attempt for username: " + username);
        }
    }

    // Helper method to hash passwords using SHA-256
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    private static void navigateUserMenu(user userObj) {
        String role = userObj.roles().trim().toUpperCase();

        if (role.contains("ADMIN")) {
            AdminMenu.showMenu();
        } else if (role.contains("PRIEST")) {
            PriestMenu.showMenu();
        } else if (role.contains("PARISH") || role.contains("OFFICER")) {
            ParishOfficerMenu.showMenu();
        } else if (role.contains("ALTAR") || role.contains("SERVER")) {
            AltarServerMenu.showMenu();
        } else if (role.contains("CUSTOMER")) {
            CustomerMenu.showMenu();
        } else {
            System.out.println("Role not recognized or menu not found for: " + userObj.roles());
        }
    }
}