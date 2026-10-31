package com.napier.sem;

import java.sql.*;
import java.util.AbstractList;
import java.util.ArrayList;

public class App {

    /**
     * Connection to MySQL database.
     */
    public Connection con = null;

    public static void main(String[] args) {
        int i;
        System.out.println("Hello and welcome!");
        for (i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }

        // Create new Application
        App a = new App();

        // Connect to database
        // Prefer environment variables for DB in Docker; fallback to db:3306
        String dbHost = System.getenv().getOrDefault("DB_HOST", "localhost");
        String dbPort = System.getenv().getOrDefault("DB_PORT", "33060");

        if (args.length < 1) {
            // When running in docker-compose, use service hostname and port
            a.connect(dbHost + ":" + dbPort, 0);
        } else {
           // a.connect(args[0], Integer.parseInt(args[1]));
            a.connect(args[0] + ":" + args[1], 30000);
        }

        // Connection Safe Gaurd
        if (a.con == null) {
            System.out.println("Critical Error: Database connection could not be established. Exiting application.");
            System.exit(-1);
        }

        // Get Employee
        com.napier.sem.Employee empInstance = new com.napier.sem.Employee();

        Employee result = empInstance.getEmployee(a.con, 499524);
        if (result != null) {
            empInstance.displayEmployee(result);
        } else {
            System.out.println("Employee not found.");
        }

        // Extract employee salary information
        ArrayList<com.napier.sem.Employee> employees = empInstance.getAllSalaries(a.con);

        // Test the size of the returned data - should be 240124
        System.out.println(employees.size());

        empInstance.printSalaries(employees);

        // Disconnect from database
        a.disconnect();

    }

    /**
     * Connect to the MySQL database.
     */
    public void connect(String conString, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database (attempt " + i + ")...");
            try {
                // Wait a little for db to start
                Thread.sleep(5000);
                con = DriverManager.getConnection("jdbc:mysql://" + conString
                        + "/employees?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println("SQLState: " + sqle.getSQLState() + " Message: " + sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted");
            }
        }

    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

}