package com.napier.sem;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.sql.*;

public class App {

    /**
     * Connection to MySQL database.
     */
    Connection con = null;

    public static void main(String[] args) {
        int i;
        System.out.println("Hello and welcome!");
        for (i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }

        // Create new Application
        App a = new App();

        // Connect to database
        if (args.length < 1) {
            //local
            a.connect("localhost:33060", 0);
        } else {
            //docker parameters passed from Dockerfile
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        // Get Employee
        Employee emp = new Employee();
        Employee result = emp.getEmployee(a.con, 499524);
        emp.displayEmployee(result);

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
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                // con = DriverManager.getConnection("jdbc:mysql://localhost:33060/employees", "root", "example");
                con = DriverManager.getConnection("jdbc:mysql://" + conString
                        + "/employees?allowPublicKeyRetrieval=true&useSSL"
                        + "=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
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