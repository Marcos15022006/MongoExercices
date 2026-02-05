package com.example.Controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;


public class EmpleadoController {

    public void insertarEmpleado(Document documentillo) {
        try (MongoProvider provider = new MongoProvider()) {
            MongoCollection<Document> col = provider.empleados();
            col.insertOne(documentillo);
        } catch (Exception e) {
            System.err.println("Error al crear un empleado: " + e.getMessage());
        }
    }

    public long eliminarEmpleado(int numEmpl){
        long cont=0;
        try (MongoProvider provider = new MongoProvider()) {
            MongoCollection<Document> col = provider.empleados();
            cont =col.deleteOne(Filters.eq("Emp_no", numEmpl)).getDeletedCount();
        } catch (Exception e) {
            System.err.println("Error al eliminar un empleado: " + e.getMessage());
        }
        return cont;
    }

    public List<Document> buscarEmpleado(Bson filtro){
        List<Document> sal = new ArrayList<>();
        try (MongoProvider provider = new MongoProvider()) {
            MongoCollection<Document> col = provider.empleados();
            col.find(filtro).into(sal);
        } catch (Exception e) {
            System.err.println("Error al buscar un empleado: " + e.getMessage());
        }
        return sal;
    }

    public void modificarEmpleado(Bson doc1, Bson doc2){
        try (MongoProvider provider = new MongoProvider()) {
            MongoCollection<Document> col = provider.empleados();
            col.updateMany(doc1, doc2);
        } catch (Exception e) {
            System.err.println("Error al modificar un empleado: " + e.getMessage());
        }
    }

}
