package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class Main {
    public static void main(String[] args) {
        boolean continuar = true;

        try (MongoProvider provider = new MongoProvider();
            Scanner scanner = new Scanner(System.in)) {
            MongoCollection<Document> col = provider.alumnado();

            Document d = new Document("nombre", "Ramiro")
                .append("edad", "20")
                .append("ciclo", "DAM");
            col.insertOne(d);

            Document d2 = new Document("nombre", "Josefina")
                .append("edad", "22")
                .append("ciclo", "DAW");
            col.insertOne(d2);

            List<Document> sal = new ArrayList<>();
            col.find().into(sal);
            System.out.println(sal);

            long cont =col.deleteOne(Filters.eq("ciclo", "DAW")).getDeletedCount();
            System.out.println(cont);

             
                
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

       