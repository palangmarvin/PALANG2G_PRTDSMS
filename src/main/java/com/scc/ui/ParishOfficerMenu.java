package com.scc.ui;

import java.util.Scanner;

public class ParishOfficerMenu {
    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Parish Officer Menu ---");
            System.out.println("1. View & Manage Parish Records (Parishioners)");
            System.out.println("2. Manage Service & Document Requests");
            System.out.println("3. Manage Mass Schedules & Events");
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
                case 1 -> manageParishRecords(scanner);
                case 2 -> manageRequests(scanner);
                case 3 -> manageSchedules(scanner);
                case 4 -> System.out.println("Logging out from Parish Officer account...");
                default -> System.out.println("Invalid choice. Please choose between 1 and 4.");
            }
        } while (choice != 4);
    }

    private static void manageParishRecords(Scanner scanner) {
        System.out.println("\n--- Parish Records Management ---");
        System.out.println("1. Register New Parishioner");
        System.out.println("2. View All Parishioners");
        System.out.println("3. Update Parishioner Status (Active/Inactive)");
        System.out.print("Select an option: ");

        int recordChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (recordChoice) {
            case 1 -> {
                System.out.println("\n--- Register Parishioner ---");
                System.out.print("Enter Full Name: ");
                String fullName = scanner.nextLine();
                System.out.print("Enter Occupation: ");
                String occupation = scanner.nextLine();
                System.out.print("Enter Location/Address: ");
                String location = scanner.nextLine();
                System.out.print("Enter Contact Number: ");
                String contactNumber = scanner.nextLine();

                // TODO: Insert into 'parishioner' table via DAO
                System.out.println("Parishioner " + fullName + " registered successfully!");
            }
            case 2 -> {
                System.out.println("\n--- Parishioner List ---");
                // TODO: Fetch from ParishionerDao
                System.out.println("Loading active parishioner records...");
            }
            case 3 -> {
                System.out.println("\n--- Update Status ---");
                System.out.print("Enter Parishioner ID: ");
                int parishionerId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new status (Active / Inactive): ");
                String status = scanner.nextLine();

                // TODO: Update database status record
                System.out.println("Parishioner ID " + parishionerId + " status updated to " + status);
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void manageRequests(Scanner scanner) {
        System.out.println("\n--- Service & Document Requests ---");
        System.out.println("1. View Service Requests");
        System.out.println("2. View Document Requests");
        System.out.println("3. Process Payment / Issue Receipt");
        System.out.print("Select an option: ");

        int reqChoice = scanner.nextInt();
        scanner.nextLine();

        switch (reqChoice) {
            case 1 -> System.out.println("Loading service requests (Mass intentions, blessings, etc.)...");
            case 2 -> System.out.println("Loading document requests (Baptismal certificates, clearances, etc.)...");
            case 3 -> {
                System.out.println("\n--- Issue Receipt ---");
                System.out.print("Enter Parishioner ID: ");
                int pId = scanner.nextInt();
                System.out.print("Enter Request ID: ");
                int rId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter Amount Paid: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();

                // TODO: Insert record into 'receipt' table
                System.out.println("Receipt issued successfully for amount: " + amount);
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void manageSchedules(Scanner scanner) {
        System.out.println("\n--- Mass Schedules & Events ---");
        System.out.print("Enter Mass Type (e.g., Sunday Mass, Wedding, Funeral): ");
        String massType = scanner.nextLine();
        System.out.print("Enter Schedule Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter Schedule Time (HH:MM): ");
        String time = scanner.nextLine();

        // TODO: Insert into 'mass_schedule' table
        System.out.println("Mass schedule for " + massType + " set on " + date + " at " + time + " successfully!");
    }
}