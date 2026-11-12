package com.scc.ui;

import java.util.Scanner;

public class CustomerMenu {
    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Request Mass Intentions / Services");
            System.out.println("2. Request Parish Documents");
            System.out.println("3. View My Requests & Status");
            System.out.println("4. View Announcements & Mass Schedule");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number (1-5).");
                scanner.next(); // consume invalid input
                System.out.print("Choose an option: ");
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1 -> requestService(scanner);
                case 2 -> requestDocument(scanner);
                case 3 -> viewMyRequests(scanner);
                case 4 -> viewAnnouncements();
                case 5 -> System.out.println("Logging out from Customer account...");
                default -> System.out.println("Invalid choice. Please choose between 1 and 5.");
            }
        } while (choice != 5);
    }

    private static void requestService(Scanner scanner) {
        System.out.println("\n--- Request Mass Intention / Service ---");
        System.out.print("Enter your Parishioner ID: ");
        int parishionerId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Service Type (e.g., Mass Intention, Blessing, Wedding, Baptism): ");
        String serviceType = scanner.nextLine();
        System.out.print("Enter Desired Schedule Date (YYYY-MM-DD): ");
        String scheduleDate = scanner.nextLine();
        System.out.print("Enter Offered Amount / Donation: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        // TODO: Insert into 'service_request' table via DAO
        System.out.println("Service request for '" + serviceType + "' submitted successfully! Status: Pending.");
    }

    private static void requestDocument(Scanner scanner) {
        System.out.println("\n--- Request Parish Document ---");
        System.out.print("Enter your Parishioner ID: ");
        int parishionerId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Document Type (e.g., Baptismal Certificate, Confirmation Certificate, Clearance): ");
        String documentType = scanner.nextLine();

        // TODO: Insert into 'document_request' table via DAO
        System.out.println("Document request for '" + documentType + "' submitted successfully! Status: Pending.");
    }

    private static void viewMyRequests(Scanner scanner) {
        System.out.println("\n--- My Requests & Status ---");
        System.out.print("Enter your Parishioner ID to lookup requests: ");
        int parishionerId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // TODO: Query 'service_request' and 'document_request' tables for this parishioner_id
        System.out.println("Fetching records for Parishioner ID: " + parishionerId + "...");
    }

    private static void viewAnnouncements() {
        System.out.println("\n--- Parish Announcements & Mass Schedule ---");
        // TODO: Fetch public announcements or mass schedules from database
        System.out.println("No new announcements at this time. Regular Sunday Masses are scheduled at 6:00 AM, 9:00 AM, and 5:00 PM.");
    }
}