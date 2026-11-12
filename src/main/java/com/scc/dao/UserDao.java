package com.scc.dao;

import com.scc.model.user;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public boolean addUser(user userObj) {
        String query = "INSERT INTO user_account (username, password_hash, role, account_status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userObj.username());
            pstmt.setString(2, userObj.password()); // Maps to password_hash
            pstmt.setString(3, userObj.roles());    // Maps to role
            pstmt.setString(4, userObj.status());   // Maps to account_status
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    public user getUserByUsername(String username) {
        String query = "SELECT * FROM user_account WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new user(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("role"),
                        rs.getString("account_status")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
        }
        return null;
    }

    public List<user> getAllUsers() {
        List<user> users = new ArrayList<>();
        String query = "SELECT * FROM user_account";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                users.add(new user(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("role"),
                        rs.getString("account_status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all users: " + e.getMessage());
        }
        return users;
    }

    public List<String> getAllLogs() {
        List<String> logs = new ArrayList<>();
        String query = "SELECT l.log_id, u.username, l.action, l.details, l.log_timestamp " +
                "FROM activity_logs l LEFT JOIN user_account u ON l.user_id = u.user_id " +
                "ORDER BY l.log_timestamp DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String logEntry = String.format("[%s] User: %s | Action: %s | Details: %s",
                        rs.getString("log_timestamp"),
                        rs.getString("username") != null ? rs.getString("username") : "System",
                        rs.getString("action"),
                        rs.getString("details")
                );
                logs.add(logEntry);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching activity logs: " + e.getMessage());
        }
        return logs;
    }
}