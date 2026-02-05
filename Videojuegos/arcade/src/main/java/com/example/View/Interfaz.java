package com.example.View;

import java.util.List;

import org.bson.Document;

import com.example.Controller.PartidaController;

public class Interfaz {

    PartidaController partidasController = new PartidaController();

    public void iniciar(){
        insertarPartidas();

        
        List<Document> resultados = partidasController.puntuacionTotalPorXogador();
        System.out.println("\n=== 1. Puntuación total por xogador ===");
        for (Document doc : resultados) {
            System.out.println("Xogador: " + doc.get("_id") + 
                             ", Puntuación total: " + doc.get("puntuacion_total"));
        }
        System.out.println("===================================================");

        
        List<Document> resultados2 = partidasController.mellorPartidaDeVadaXogador();
        System.out.println("\n=== 2. Mellor partida de cada xogador ===");
        for (Document doc : resultados2) {
            System.out.println("Xogador: " + doc.get("_id") + 
                             ", Mellor puntuación: " + doc.get("mellor_puntuacion"));
        }

        
        List<Document> resultados3 = partidasController.partidaMaisCurtaPorXogo();
        System.out.println("\n=== 3. Partida máis curta por xogo ===");
        for (Document doc : resultados3) {
            System.out.println("Xogo: " + doc.get("_id") + 
                             ", Duración mínima: " + doc.get("duracion_minima") + " min");
        }
        System.out.println("===================================================");

        
        List<Document> resultados4 = partidasController.rankingDeXogadores();
        System.out.println("\n=== 4. Ranking de xogadores ===");
        int posicion = 1;
        for (Document doc : resultados4) {
            System.out.println(posicion + ". Xogador: " + doc.get("_id") + 
                             ", Puntuación total: " + doc.get("puntuacion_total"));
            posicion++;
        }
        System.out.println("===================================================");

        
        List<Document> resultados5 = partidasController.listaxeSimplificadaDePartidas();
        System.out.println("\n=== 5. Listaxe simplificada de partidas ===");
        for (Document doc : resultados5) {
            System.out.println("Xogador: " + doc.get("Xogador") + 
                             ", Xogo: " + doc.get("Xogo") + 
                             ", Puntuación: " + doc.get("Puntuacion"));
        }
        System.out.println("===================================================");

        
        List<Document> resultados6 = partidasController.xogosMaisPuntuables();
        System.out.println("\n=== 6. Xogos máis puntuables ===");
        for (Document doc : resultados6) {
            System.out.println("Xogo: " + doc.get("_id") + 
                             ", Puntuación media: " + String.format("%.2f", doc.get("puntuacion_media")));
        }
        System.out.println("===================================================");
    }

    public void insertarPartidas(){
        Document d1 = new Document("Xogador", "Mario")
            .append("Xogo", "Space Invaders")
            .append("Puntuacion", 1200)
            .append("Duracion", 15)
            .append("Nivel", 3);
        partidasController.insertarPartida(d1);

        Document d2 = new Document("Xogador", "Luigi")
            .append("Xogo", "Pac-Man")
            .append("Puntuacion", 2500)
            .append("Duracion", 20)
            .append("Nivel", 5);
        partidasController.insertarPartida(d2);

        Document d3 = new Document("Xogador", "Mario")
            .append("Xogo", "Pac-Man")
            .append("Puntuacion", 1800)
            .append("Duracion", 18)
            .append("Nivel", 4);
        partidasController.insertarPartida(d3);

        Document d4 = new Document("Xogador", "Peach")
            .append("Xogo", "Space Invaders")
            .append("Puntuacion", 3000)
            .append("Duracion", 25)
            .append("Nivel", 6);
        partidasController.insertarPartida(d4);

        Document d5 = new Document("Xogador", "Luigi")
            .append("Xogo", "Donkey Kong")
            .append("Puntuacion", 1500)
            .append("Duracion", 12)
            .append("Nivel", 3);
        partidasController.insertarPartida(d5);

        Document d6 = new Document("Xogador", "Mario")
            .append("Xogo", "Space Invaders")
            .append("Puntuacion", 2200)
            .append("Duracion", 22)
            .append("Nivel", 5);
        partidasController.insertarPartida(d6);

        Document d7 = new Document("Xogador", "Peach")
            .append("Xogo", "Pac-Man")
            .append("Puntuacion", 2800)
            .append("Duracion", 10)
            .append("Nivel", 6);
        partidasController.insertarPartida(d7);

        Document d8 = new Document("Xogador", "Yoshi")
            .append("Xogo", "Donkey Kong")
            .append("Puntuacion", 1100)
            .append("Duracion", 8)
            .append("Nivel", 2);
        partidasController.insertarPartida(d8);

        System.out.println("Partidas insertadas correctamente");
    }

}
