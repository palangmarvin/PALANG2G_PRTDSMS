package com.scc;

import com.scc.dao.DatabaseInitializer;
import com.scc.ui.Authentication;

public class Main {

    public static void main(String[] args) {
        // Initialize all SQLite database tables
        DatabaseInitializer.initialize();

        System.out.println("Welcome to the Parish Management System!");

        // Launch the authentication menu loop
        Authentication.showAuthMenu();
    }
}