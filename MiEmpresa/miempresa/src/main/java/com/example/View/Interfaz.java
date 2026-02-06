package com.example.View;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.example.Controller.EmpleadoController;
import com.example.Controller.MongoProvider;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

public class Interfaz {
    
    EmpleadoController empleadosController = new EmpleadoController();

    public void iniciar(){
        insertarEmpleados();

        // Buscar empleados del departamento 10
        Bson filtro = Filters.eq("dep", 10);
        List<Document> empleadosDep10 = empleadosController.buscarEmpleado(filtro);
        System.out.println("------------------------------------------------");
        System.out.println("Empleados del departamento 10:");
        for (Document doc : empleadosDep10) {
            System.out.println(doc);
        }
        System.out.println("------------------------------------------------");

        // Busca los empleados del departamento 10 y 20
        Bson filtroDosDeps = Filters.or(Filters.eq("dep", 10), Filters.eq("dep", 20));
        List<Document> empleadosDosDeps = empleadosController.buscarEmpleado(filtroDosDeps);
        System.out.println("------------------------------------------------");
        System.out.println("Empleados de los departamentos 10 y 20:");
        for (Document doc : empleadosDosDeps) {
            System.out.println(doc);
        }
        System.out.println("------------------------------------------------"); 
        
        
        //Obtén los empleados con salario > 1300 y oficio Profesora.
        List<Document> empleadosSalarioProfesora = empleadosController.buscarEmpleado(
            Filters.and(Filters.gt("salario", 1300), Filters.eq("oficio", "Profesora"))
        );

        System.out.println("------------------------------------------------");
        System.out.println("Empleados con salario > 1300 y oficio Profesora:");
        for (Document doc : empleadosSalarioProfesora) {
            System.out.println(doc);
        }
        System.out.println("------------------------------------------------");

        //Sube el salario de los analistas en 100€, a todos los analistas.
        System.out.println("------------------------------------------------");
        System.out.println("Subir el salario de los analistas en 100€");
        System.out.println("------------------------------------------------");
        Bson filtroMod = Filters.eq("oficio", "Analista");
        Bson actualizacion = Updates.inc("salario", 100);
        empleadosController.modificarEmpleado(filtroMod, actualizacion);
        System.out.println("------------------------------------------------");
        System.out.println("Salario de los analistas actualizado.");
        System.out.println("------------------------------------------------");

        //Decrementa la comisión existente en 20€.
        System.out.println("------------------------------------------------");
        System.out.println("Decrementa la comisión existente en 20€");
        System.out.println("------------------------------------------------");
        Bson filtroComision = Filters.exists("comisión", true);
        Bson actualizacionComision = Updates.inc("comisión", -20);
        empleadosController.modificarEmpleado(filtroComision, actualizacionComision);
        System.out.println("------------------------------------------------");
        System.out.println("Comisión actualizada.");
        System.out.println("------------------------------------------------");

        //Visualiza la media de salario.
        System.out.println("------------------------------------------------");
        System.out.println("Calcular media salario");
        System.out.println("------------------------------------------------");
        int sumaSalarios = 0;
        int contadorEmpleados = 0;
        for (Document doc : empleadosController.buscarEmpleado(Filters.empty())) {
            sumaSalarios += doc.getInteger("salario");
            contadorEmpleados++;
        }
        int mediaSalario = sumaSalarios / contadorEmpleados;
        System.out.println("------------------------------------------------");
        System.out.println("Media de salario:" + mediaSalario);
        System.out.println("------------------------------------------------");
        

        //Visualiza por departamento el número de empleados, el salario medio y el máximo salario.
        System.out.println("------------------------------------------------");
        System.out.println("Estadísticas por departamento:");
        System.out.println("------------------------------------------------");
        for (int dep = 10; dep <= 30; dep += 10) {
            Bson filtroDep = Filters.eq("dep", dep);
            int sumaSal = 0;
            int maxSal = 0;
            int contadorEmp = 0;

            for (Document doc : empleadosController.buscarEmpleado(filtroDep)) {
                int salario = doc.getInteger("salario");
                sumaSal += salario;
                if (salario > maxSal) {
                    maxSal = salario;
                }
                contadorEmp++;
            }

            int mediaSalarioDep = sumaSal / contadorEmp;
            System.out.println("------------------------------------------------");
            System.out.println("Departamento "+dep+": Empleados="+contadorEmp+", Salario Medio="+mediaSalarioDep+", Máximo Salario="+maxSal);
            System.out.println("------------------------------------------------");
            
        }
        System.out.println("------------------------------------------------");




        

        //Visualiza el nombre del empleado que tiene el máximo salario.
        System.out.println("------------------------------------------------");
        System.out.println("Empleado forrado");
        System.out.println("------------------------------------------------");
        int max = 0;
        String nombreRicachon = "";
        for (Document doc : empleadosController.buscarEmpleado(Filters.empty())) {
            int salario = doc.getInteger("salario");
            if (salario > max) {
                max = salario;
                nombreRicachon = doc.getString("nombre");
            }
        }
        System.out.println("------------------------------------------------");
        System.out.println("Nombre: " + nombreRicachon + ", Salario: " + max);
        System.out.println("------------------------------------------------");


    }

    public void insertarEmpleados(){
         Document d = new Document("Emp_no", 1)
            .append("nombre", "Juan")
            .append("dep", 10)
            .append("salario", 1000)
            .append("fechaAlta", "10/10/1999");

        empleadosController.insertarEmpleado(d);

        Document d2 = new Document("nombre", "Alicia")
            .append("Emp_no", 2)
            .append("dep", 10)
            .append("salario", 1400)
            .append("fechaAlta", "07/08/2000")
            .append("oficio", "Profesora");

        empleadosController.insertarEmpleado(d2);

        Document d3 = new Document("nombre", "María Jesus")
            .append("Emp_no", 3)
            .append("dep", 20)
            .append("salario", 1500)
            .append("fechaAlta", "05/01/2005")
            .append("oficio", "Analista")
            .append("comisión", 100);

        empleadosController.insertarEmpleado(d3);

        Document d4 = new Document("nombre", "Alberto")
            .append("Emp_no", 4)
            .append("dep", 20)
            .append("salario", 1100)
            .append("fechaAlta", "15/11/2001");

        empleadosController.insertarEmpleado(d4);

         Document d5 = new Document("nombre", "Fernando")
            .append("Emp_no", 5)
            .append("dep", 30)
            .append("salario", 1400)
            .append("fechaAlta", "20/11/1999")
            .append("oficio", "Analista")
            .append("comisión", 200);

        empleadosController.insertarEmpleado(d5);
    }

    public List<Document> getEmpleado(){
            List<Document> lista = new ArrayList<>();
            List<Bson> pipeline = List.of(
                Aggregates.group("$dep",
                    Accumulators.sum("numEmpleados",1),
                    Accumulators.avg("salarioMedio","$salario"),
                    Accumulators.max("salarioMaximo","$salario")
                    

                ),
                Aggregates.sort(Sorts.ascending("dep"))
            );
             try (MongoProvider provider = new MongoProvider()) {
            MongoCollection<Document> col = provider.empleados();
            col.aggregate(pipeline).into(lista);
            } catch (Exception e) {
            System.err.println("Error al buscar un empleado: " + e.getMessage());
            }
            return lista;
        }
}
