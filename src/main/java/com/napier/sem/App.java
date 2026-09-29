package com.napier.sem;

// Fixed legacy import to match version 5.1.0 specification
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }

        // Corrected instantiation approach: use MongoClients.create factory pattern
        // Connection string URI format maps smoothly across your development environments
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27000")) {

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
