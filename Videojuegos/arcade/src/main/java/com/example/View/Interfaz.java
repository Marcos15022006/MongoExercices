package com.example.View;

import java.util.List;

import org.bson.Document;

import com.example.Controller.PartidaController;

public class Interfaz {

    PartidaController partidasController = new PartidaController();

    public void iniciar(){
        partidasController.insertarPartidas();

        
        List<Document> resultados = partidasController.puntuacionTotalPorXogador();
        System.out.println("\n=== 1. Puntuación total por xogador ===");
        for (Document doc : resultados) {
            System.out.println("Xogador: " + doc.get("_id") + ", Puntuación total: " + doc.get("puntuacion_total"));
        }
        System.out.println("===================================================");

        
        List<Document> resultados2 = partidasController.mellorPartidaDeVadaXogador();
        System.out.println("\n=== 2. Mellor partida de cada xogador ===");
        for (Document doc : resultados2) {
            System.out.println("Xogador: " + doc.get("_id") + ", Mellor puntuación: " + doc.get("mellor_puntuacion"));
        }

        
        List<Document> resultados3 = partidasController.partidaMaisCurtaPorXogo();
        System.out.println("\n=== 3. Partida máis curta por xogo ===");
        for (Document doc : resultados3) {
            System.out.println("Xogo: " + doc.get("_id") + ", Duración mínima: " + doc.get("duracion_minima") + " min");
        }
        System.out.println("===================================================");

        
        List<Document> resultados4 = partidasController.rankingDeXogadores();
        System.out.println("\n=== 4. Ranking de xogadores ===");
        int posicion = 1;
        for (Document doc : resultados4) {
            System.out.println(posicion + ". Xogador: " + doc.get("_id") + ", Puntuación total: " + doc.get("puntuacion_total"));
            posicion++;
        }
        System.out.println("===================================================");

        
        List<Document> resultados5 = partidasController.listaxeSimplificadaDePartidas();
        System.out.println("\n=== 5. Listaxe simplificada de partidas ===");
        for (Document doc : resultados5) {
            System.out.println("Xogador: " + doc.get("Xogador") + ", Xogo: " + doc.get("Xogo") + ", Puntuación: " + doc.get("Puntuacion"));
        }
        System.out.println("===================================================");

        
        List<Document> resultados6 = partidasController.xogosMaisPuntuables();
        System.out.println("\n=== 6. Xogos máis puntuables ===");
        for (Document doc : resultados6) {
            System.out.println("Xogo: " + doc.get("_id") + ", Puntuación media: " + String.format("%.2f", doc.get("puntuacion_media")));
        }
        System.out.println("===================================================");
    }

    

}
