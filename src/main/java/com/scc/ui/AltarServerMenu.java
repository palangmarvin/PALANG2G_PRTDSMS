package com.scc.ui;

import java.util.Scanner;

public class AltarServerMenu {
    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Altar Server Menu ---");
            System.out.println("1. View Assigned Mass Schedules");
            System.out.println("2. Update My Availability");
            System.out.println("3. View Parish Announcements");
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
                case 1 -> viewAssignedSchedules(scanner);
                case 2 -> updateAvailability(scanner);
                case 3 -> viewAnnouncements();
                case 4 -> System.out.println("Logging out from Altar Server account...");
                default -> System.out.println("Invalid choice. Please choose between 1 and 4.");
            }
        } while (choice != 4);
    }

    private static void viewAssignedSchedules(Scanner scanner) {
        System.out.println("\n--- View Assigned Schedules ---");
        System.out.print("Enter your Altar Server ID: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid numeric ID.");
            scanner.next();
            System.out.print("Enter your Altar Server ID: ");
        }
        int serverId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // TODO: Query 'mass_schedule' table where altar_server_id = serverId
        System.out.println("Fetching scheduled masses assigned to Altar Server ID: " + serverId + "...");
    }

    private static void updateAvailability(Scanner scanner) {
        System.out.println("\n--- Update Availability ---");
        System.out.print("Enter your Altar Server ID: ");
        int serverId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter your availability status (e.g., Available / On Leave / Preferred Sunday Morning): ");
        String availability = scanner.nextLine();

        // TODO: Update availability in database or server profile table
        System.out.println("Availability status for Server ID " + serverId + " updated to: '" + availability + "' successfully!");
    }

    private static void viewAnnouncements() {
        System.out.println("\n--- Parish Announcements ---");
        // TODO: Fetch announcements from database or parish board
        System.out.println("Reminder: Altar servers must arrive 30 minutes before scheduled mass time for orientation and vesting.");
    }
}