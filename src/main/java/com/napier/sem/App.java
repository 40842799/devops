package com.napier.sem;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class App {
    public static void main(String[] args) {
        int i;
        System.out.println("Hello and welcome!");
        for (i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }
        // Connect to MongoDB on local system - we're using port 27000
        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27000");
            // Get a database - will create when we use it
            MongoDatabase database = mongoClient.getDatabase("mydb");
            // Get a collection from the database
            MongoCollection<Document> collection = database.getCollection("test");
            // Create a document to store
            Document doc = new Document("name", "40842799")
                    .append("class", "DevOps")
                    .append("year", "2026")
                    .append("result", new Document("CW", 95).append("EX", 85));
            // Add document to collection
            collection.insertOne(doc);
            // Check document in collection
            Document myDoc = collection.find().first();
            System.out.println(myDoc.toJson());
        } catch (Exception e) {
            System.out.println("Error caught: " + e.getMessage());
            //throw new RuntimeException(e);
        }

    }
}

