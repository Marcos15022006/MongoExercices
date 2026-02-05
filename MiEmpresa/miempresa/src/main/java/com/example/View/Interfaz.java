package com.example.View;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.example.Controller.EmpleadoController;
import com.mongodb.client.model.Filters;
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
        /*filtro = Filters.eq("dep", 10);
        System.out.println("Empleados del departamento 10 ");
        System.out.println("------------------------------------------------");
        for (Document doc : empleadosDep10) {
            System.out.println(doc);
        }
        System.out.println("------------------------------------------------");
        
        filtro = Filters.eq("dep", 20);
        List<Document> empleadosDep20 = empleadosController.buscarEmpleado(filtro);
        System.out.println("------------------------------------------------");
        System.out.println("Empleados del departamento 20 ");
        for (Document doc : empleadosDep20) {
            System.out.println(doc);
        }
        System.out.println("------------------------------------------------");
        */

        //Las dos conssultas en una sola.
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
        System.out.println("Subiendo el salario de los analistas en 100€...");
        System.out.println("------------------------------------------------");
        Bson filtroMod = Filters.eq("oficio", "Analista");
        Bson actualizacion = Updates.inc("salario", 100);
        empleadosController.modificarEmpleado(filtroMod, actualizacion);
        System.out.println("------------------------------------------------");
        System.out.println("Salario de los analistas actualizado.");
        System.out.println("------------------------------------------------");

        //Decrementa la comisión existente en 20€.
        System.out.println("------------------------------------------------");
        System.out.println("Decrementando la comisión existente en 20€...");
        System.out.println("------------------------------------------------");
        Bson filtroComision = Filters.exists("comisión", true);
        Bson actualizacionComision = Updates.inc("comisión", -20);
        empleadosController.modificarEmpleado(filtroComision, actualizacionComision);
        System.out.println("------------------------------------------------");
        System.out.println("Comisión actualizada.");
        System.out.println("------------------------------------------------");

        //Visualiza la media de salario.
        System.out.println("------------------------------------------------");
        System.out.println("Calculando la media de salario...");
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
            int sumaSalariosDep = 0;
            int maxSalarioDep = 0;
            int contadorEmpleadosDep = 0;

            for (Document doc : empleadosController.buscarEmpleado(filtroDep)) {
                int salario = doc.getInteger("salario");
                sumaSalariosDep += salario;
                if (salario > maxSalarioDep) {
                    maxSalarioDep = salario;
                }
                contadorEmpleadosDep++;
            }

            int mediaSalarioDep = sumaSalariosDep / contadorEmpleadosDep;
            System.out.println("------------------------------------------------");
            System.out.println("Departamento "+dep+": Empleados="+contadorEmpleadosDep+", Salario Medio="+mediaSalarioDep+", Máximo Salario="+maxSalarioDep);
            System.out.println("------------------------------------------------");
            
        }
        System.out.println("------------------------------------------------");

        //Visualiza el nombre del empleado que tiene el máximo salario.
        System.out.println("------------------------------------------------");
        System.out.println("Empleado con el máximo salario:");
        System.out.println("------------------------------------------------");
        int maxSalario = 0;
        String nombreMaxSalario = "";
        for (Document doc : empleadosController.buscarEmpleado(Filters.empty())) {
            int salario = doc.getInteger("salario");
            if (salario > maxSalario) {
                maxSalario = salario;
                nombreMaxSalario = doc.getString("nombre");
            }
        }
        System.out.println("------------------------------------------------");
        System.out.println("Nombre: " + nombreMaxSalario + ", Salario: " + maxSalario);
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

}
