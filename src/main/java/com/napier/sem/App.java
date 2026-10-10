package com.napier.sem;

// Fixed legacy import to match version 5.1.0 specification

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.sql.*;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }

        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        // Connection to the database
        Connection con = null;
        int retries = 100;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                // Wait a bit
                Thread.sleep(10000);
                // Exit for loop
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }

        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }

        // Corrected instantiation approach: use MongoClients.create factory pattern
        // Connection string URI format maps smoothly across your development environments
        try (MongoClient mongoClient = MongoClients.create("mongodb://mongo-dbserver:27017")) {

            // Get a database - will create when we use it
            MongoDatabase database = mongoClient.getDatabase("mydb");

            // Get a collection from the database
            MongoCollection<Document> collection = database.getCollection("test");

            // Create a document to store
            Document doc = new Document("name", "Hitesh Thakore")
                    .append("class", "DevOps")
                    .append("year", "2026")
                    .append("result", new Document("CW", 95).append("EX", 85));

            // Add document to collection
            collection.insertOne(doc);

            // Check document in collection
            // Document myDoc = collection.find().first();
            Document myDoc = collection.find(new Document("name", "Hitesh Thakore")).first();
            if (myDoc != null) {
                System.out.println(myDoc.toJson());
            } else {
                System.out.println("No document found in collection.");
            }
        } // The try-with-resources statement automatically closes the client resource cleanly
    }
}
