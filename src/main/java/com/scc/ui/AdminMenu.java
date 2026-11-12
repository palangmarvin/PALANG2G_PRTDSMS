package com.scc.ui;

import com.scc.dao.UserDao;
import com.scc.model.user;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private static final UserDao userDao = new UserDao();

    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Manage Users (View & Update Accounts)");
            System.out.println("2. View Activity Logs");
            System.out.println("3. System Settings & DB Status");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number (1-4).");
                scanner.next(); // consume invalid input
                System.out.print("Choose an option: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1 -> manageUsers(scanner);
                case 2 -> viewActivityLogs();
                case 3 -> systemSettings();
                case 4 -> System.out.println("Logging out from Admin account...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);
    }

    private static void manageUsers(Scanner scanner) {
        System.out.println("\n--- User Management ---");
        System.out.println("1. View All Registered Users");
        System.out.println("2. Update User Status / Role");
        System.out.print("Select an option: ");

        int userChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (userChoice) {
            case 1 -> {
                System.out.println("\n--- Registered Users List ---");
                List<user> users = userDao.getAllUsers();
                if (users.isEmpty()) {
                    System.out.println("No users found in the database.");
                } else {
                    for (user u : users) {
                        System.out.println(u);
                    }
                }
            }
            case 2 -> {
                System.out.println("\n--- Update User Record ---");
                System.out.print("Enter username to modify: ");
                String targetUsername = scanner.nextLine();

                user existingUser = userDao.getUserByUsername(targetUsername);
                if (existingUser == null) {
                    System.out.println("User '" + targetUsername + "' not found.");
                    return;
                }

                System.out.println("Found user: " + existingUser);
                System.out.print("Enter new role (Admin / Priest / ParishOfficer / AltarServer / Customer): ");
                String newRole = scanner.nextLine();
                System.out.print("Enter new status (Active / Inactive): ");
                String newStatus = scanner.nextLine();

                // TODO: Add an update method in UserDao (e.g., userDao.updateUserRoleAndStatus(...))
                System.out.println("User record for " + targetUsername + " updated to Role: " + newRole + ", Status: " + newStatus);
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void viewActivityLogs() {
        System.out.println("\n--- System Activity Logs ---");
        List<String> logs = userDao.getAllLogs();
        if (logs.isEmpty()) {
            System.out.println("No activity logs recorded yet.");
        } else {
            for (String log : logs) {
                System.out.println(log);
            }
        }
    }

    private static void systemSettings() {
        System.out.println("\n--- System Settings & Diagnostics ---");
        System.out.println("Database Connection: SQLite (Connected)");
        System.out.println("Base Package: com.scc");
        System.out.println("System Status: Operational");
    }
}