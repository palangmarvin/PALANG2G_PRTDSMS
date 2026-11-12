package com.scc.dao;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        // TABLE 1: Parishioner
        String createParishionerTable = "CREATE TABLE IF NOT EXISTS parishioner ("
                + "parishioner_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "full_name TEXT NOT NULL, "
                + "occupation TEXT, "
                + "location TEXT, "
                + "contact_number TEXT, "
                + "registration_date TEXT, "
                + "status TEXT CHECK(status IN ('Active', 'Inactive')) DEFAULT 'Active')";

        // TABLE 2: User Account
        String createUserAccountTable = "CREATE TABLE IF NOT EXISTS user_account ("
                + "user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT UNIQUE NOT NULL, "
                + "password_hash TEXT NOT NULL, "
                + "role TEXT NOT NULL, "
                + "full_name TEXT, "
                + "account_status TEXT)";

        // TABLE 3: Service Request
        String createServiceRequestTable = "CREATE TABLE IF NOT EXISTS service_request ("
                + "request_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "parishioner_id INTEGER NOT NULL, "
                + "service_type TEXT NOT NULL, "
                + "request_date TEXT, "
                + "schedule_date TEXT, "
                + "status TEXT, "
                + "amount REAL, "
                + "FOREIGN KEY (parishioner_id) REFERENCES parishioner(parishioner_id))";

        // TABLE 4: Document Request
        String createDocumentRequestTable = "CREATE TABLE IF NOT EXISTS document_request ("
                + "document_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "parishioner_id INTEGER NOT NULL, "
                + "document_type TEXT NOT NULL, "
                + "request_date TEXT, "
                + "status TEXT, "
                + "release_date TEXT, "
                + "FOREIGN KEY (parishioner_id) REFERENCES parishioner(parishioner_id))";

        // TABLE 5: Mass Schedule
        String createMassScheduleTable = "CREATE TABLE IF NOT EXISTS mass_schedule ("
                + "mass_schedule_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "parishioner_id INTEGER, "
                + "priest_id INTEGER, "
                + "altar_server_id INTEGER, "
                + "mass_type TEXT NOT NULL, "
                + "schedule_date TEXT, "
                + "schedule_time TEXT, "
                + "status TEXT, "
                + "FOREIGN KEY (parishioner_id) REFERENCES parishioner(parishioner_id))";

        // TABLE 6: Receipt
        String createReceiptTable = "CREATE TABLE IF NOT EXISTS receipt ("
                + "receipt_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "parishioner_id INTEGER, "
                + "request_id INTEGER, "
                + "transaction_date TEXT, "
                + "amount REAL, "
                + "payment_status TEXT, "
                + "FOREIGN KEY (parishioner_id) REFERENCES parishioner(parishioner_id), "
                + "FOREIGN KEY (request_id) REFERENCES service_request(request_id))";

        // TABLE 7: Activity_Logs
        String createActivityLogsTable = "CREATE TABLE IF NOT EXISTS activity_logs ("
                + "log_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "user_id INTEGER, "
                + "action TEXT NOT NULL, "
                + "details TEXT, "
                + "log_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY (user_id) REFERENCES user_account(user_id))";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createParishionerTable);
            stmt.execute(createUserAccountTable);
            stmt.execute(createServiceRequestTable);
            stmt.execute(createDocumentRequestTable);
            stmt.execute(createMassScheduleTable);
            stmt.execute(createReceiptTable);
            stmt.execute(createActivityLogsTable);

            // Safely add missing columns if the table already existed with an old schema
            try { stmt.execute("ALTER TABLE activity_logs ADD COLUMN user_id INTEGER;"); } catch (Exception ignored) {}
            try { stmt.execute("ALTER TABLE activity_logs ADD COLUMN action TEXT;"); } catch (Exception ignored) {}
            try { stmt.execute("ALTER TABLE activity_logs ADD COLUMN details TEXT;"); } catch (Exception ignored) {}
            try { stmt.execute("ALTER TABLE activity_logs ADD COLUMN log_timestamp DATETIME;"); } catch (Exception ignored) {}

            System.out.println("All database tables initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}