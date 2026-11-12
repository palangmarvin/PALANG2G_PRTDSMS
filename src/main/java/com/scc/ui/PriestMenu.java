package com.scc.ui;

import java.util.Scanner;

public class PriestMenu {
    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Priest Menu ---");
            System.out.println("1. View Mass Schedule");
            System.out.println("2. Manage Sacraments (Baptism, Wedding, Confirmation)");
            System.out.println("3. View Service & Document Requests");
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
                case 1 -> viewMassSchedule();
                case 2 -> manageSacraments(scanner);
                case 3 -> viewServiceRequests();
                case 4 -> System.out.println("Logging out from Priest account...");
                default -> System.out.println("Invalid choice. Please choose between 1 and 4.");
            }
        } while (choice != 4);
    }

    private static void viewMassSchedule() {
        System.out.println("\n--- Mass Schedule ---");
        // TODO: Call a MassScheduleDao method to fetch and display schedule records from the database
        System.out.println("Fetching upcoming mass schedules and assignments...");
    }

    private static void manageSacraments(Scanner scanner) {
        System.out.println("\n--- Manage Sacraments ---");
        System.out.println("1. Record Baptism");
        System.out.println("2. Record Wedding");
        System.out.println("3. Record Confirmation");
        System.out.print("Select sacrament option: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number (1-3).");
            scanner.next(); // consume invalid input
            System.out.print("Select sacrament option: ");
        }

        int sacChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (sacChoice) {
            case 1 -> {
                System.out.println("\n--- Record Baptism ---");
                System.out.print("Enter Parishioner ID: ");
                int parishionerId = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.print("Enter Child's Full Name: ");
                String childName = scanner.nextLine();
                System.out.print("Enter Schedule Date (YYYY-MM-DD): ");
                String scheduleDate = scanner.nextLine();

                // TODO: Insert into database using a SacramentDao or ServiceRequestDao
                System.out.println("Successfully recorded Baptism for " + childName + " on " + scheduleDate + "!");
            }
            case 2 -> {
                System.out.println("\n--- Record Wedding ---");
                System.out.print("Enter Groom's Full Name: ");
                String groom = scanner.nextLine();
                System.out.print("Enter Bride's Full Name: ");
                String bride = scanner.nextLine();
                System.out.print("Enter Schedule Date (YYYY-MM-DD): ");
                String scheduleDate = scanner.nextLine();

                // TODO: Insert wedding record into database
                System.out.println("Successfully recorded Wedding for " + groom + " & " + bride + " on " + scheduleDate + "!");
            }
            case 3 -> {
                System.out.println("\n--- Record Confirmation ---");
                System.out.print("Enter Parishioner ID: ");
                int parishionerId = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.print("Enter Candidate's Full Name: ");
                String candidateName = scanner.nextLine();
                System.out.print("Enter Schedule Date (YYYY-MM-DD): ");
                String scheduleDate = scanner.nextLine();

                // TODO: Insert confirmation record into database
                System.out.println("Successfully recorded Confirmation for " + candidateName + " on " + scheduleDate + "!");
            }
            default -> System.out.println("Invalid sacrament choice. Returning to menu.");
        }
    }

    private static void viewServiceRequests() {
        System.out.println("\n--- Parish Service & Document Requests ---");
        System.out.println("Loading active parish requests...");
    }
}