package com.scc.model;

public record user(int id, String username, String password, String roles, String status) {

    public enum userRole {
        ADMIN,
        PRIEST,
        PARISH_OFFICER,
        ALTAR_SERVER,
        CUSTOMER
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - status: %s", id, username, roles, status);
    }
}