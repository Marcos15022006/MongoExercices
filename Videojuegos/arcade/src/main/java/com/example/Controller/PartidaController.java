package com.example.Controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Projections;


public class PartidaController {

    public void insertarPartida(Document documentillo) {
        try (MongoProvider provider = new MongoProvider()) {
            MongoCollection<Document> col = provider.partidas();
            col.insertOne(documentillo);
        } catch (Exception e) {
            System.err.println("Error al crear una partida: " + e.getMessage());
        }
    }


    // 1. Puntuación total por xogador
    public List<Document> puntuacionTotalPorXogador() {
        List<Document> sal = new ArrayList<>();
        try (MongoProvider provider = new MongoProvider()) {
            MongoCollection<Document> col = provider.partidas();
            col.aggregate(List.of(
                Aggregates.group("$Xogador", 
                    Accumulators.sum("puntuacion_total", "$Puntuacion"))
            )).into(sal);
        } catch (Exception e) {
            System.err.println("Error al obtener puntuación total por xogador: " + e.getMessage());
        }
        return sal;
    }

    // 2. Mellor partida de cada xogador
    public List<Document> mellorPartidaDeVadaXogador() {
        List<Document> sal = new ArrayList<>();
        try (MongoProvider provider = new MongoProvider()) {
            MongoCollection<Document> col = provider.partidas();
            col.aggregate(List.of(
                Aggregates.group("$Xogador", 
                    Accumulators.max("mellor_puntuacion", "$Puntuacion"))
            )).into(sal);
        } catch (Exception e) {
            System.err.println("Error al obtener mellor partida de cada xogador: " + e.getMessage());
        }
        return sal;
    }

    // 3. Partida máis curta por xogo
    public List<Document> partidaMaisCurta() {
        List<Document> sal = new ArrayList<>();
        try (MongoProvider provider = new MongoProvider()) {
            MongoCollection<Document> col = provider.partidas();
            col.aggregate(List.of(
                Aggregates.group("$Xogo", 
                    Accumulators.min("duracion_minima", "$Duracion"))
            )).into(sal);
        } catch (Exception e) {
            System.err.println("Error al obtener partida máis curta por xogo: " + e.getMessage());
        }
        return sal;
    }

    // 4. Ranking de xogadores
    public List<Document> rankingDeXogadores() {
        List<Document> sal = new ArrayList<>();
        try (MongoProvider provider = new MongoProvider()) {
            MongoCollection<Document> col = provider.partidas();
            col.aggregate(List.of(
                Aggregates.group("$Xogador", 
                    Accumulators.sum("puntuacion_total", "$Puntuacion")),
                Aggregates.sort(Sorts.descending("puntuacion_total"))
            )).into(sal);
        } catch (Exception e) {
            System.err.println("Error al obtener ranking de xogadores: " + e.getMessage());
        }
        return sal;
    }

    // 5. Listaxe simplificada de partidas
    public List<Document> listaxeSimplificada() {
        List<Document> sal = new ArrayList<>();
        try (MongoProvider provider = new MongoProvider()) {
            MongoCollection<Document> col = provider.partidas();
            col.aggregate(List.of(
                Aggregates.project(
                    Projections.fields(
                        Projections.excludeId(),
                        Projections.include("Xogador", "Xogo", "Puntuacion")
                    )
                )
            )).into(sal);
        } catch (Exception e) {
            System.err.println("Error al obtener listaxe simplificada de partidas: " + e.getMessage());
        }
        return sal;
    }

    /*public List<Document> listaxepartsimpl2(){
        List<Document> sal = new ArrayList<>();
        try (MongoProvider provider = new MongoProvider()) {
            MongoCollection<Document> col = provider.partidas();
            Bson projection = new Document("Xogador", 1)
            .append("Xogo",1)
            .append("Puntuacion", 1)
            .append("_id", 0);
            col.find().projection(projection).into(sal);
            } catch (Exception e) {
            System.err.println("Error al obtener listaxe simplificada de partidas: " + e.getMessage());
        }
        return sal;

    }
    */

    // 6. Xogos máis puntuables
    public List<Document> xogosMaisPuntuables() {
        List<Document> sal = new ArrayList<>();
        try (MongoProvider provider = new MongoProvider()) {
            MongoCollection<Document> col = provider.partidas();
            col.aggregate(List.of(
                Aggregates.group("$Xogo",
                    Accumulators.avg("puntuacion_media", "$Puntuacion")),
                Aggregates.sort(Sorts.descending("puntuacion_media"))
            )).into(sal);
        } catch (Exception e) {
            System.err.println("Error al obtener xogos máis puntuables: " + e.getMessage());
        }
        return sal;
    }

    public void insertarPartidas(){
        Document d1 = new Document("Xogador", "Mario")
            .append("Xogo", "Space Invaders")
            .append("Puntuacion", 1200)
            .append("Duracion", 15)
            .append("Nivel", 3);
        insertarPartida(d1);

        Document d2 = new Document("Xogador", "Luigi")
            .append("Xogo", "Pac-Man")
            .append("Puntuacion", 2500)
            .append("Duracion", 20)
            .append("Nivel", 5);
        insertarPartida(d2);

        Document d3 = new Document("Xogador", "Mario")
            .append("Xogo", "Pac-Man")
            .append("Puntuacion", 1800)
            .append("Duracion", 18)
            .append("Nivel", 4);
        insertarPartida(d3);

        Document d4 = new Document("Xogador", "Peach")
            .append("Xogo", "Space Invaders")
            .append("Puntuacion", 3000)
            .append("Duracion", 25)
            .append("Nivel", 6);
        insertarPartida(d4);

        Document d5 = new Document("Xogador", "Luigi")
            .append("Xogo", "Donkey Kong")
            .append("Puntuacion", 1500)
            .append("Duracion", 12)
            .append("Nivel", 3);
        insertarPartida(d5);

        Document d6 = new Document("Xogador", "Mario")
            .append("Xogo", "Space Invaders")
            .append("Puntuacion", 2200)
            .append("Duracion", 22)
            .append("Nivel", 5);
        insertarPartida(d6);

        Document d7 = new Document("Xogador", "Peach")
            .append("Xogo", "Pac-Man")
            .append("Puntuacion", 2800)
            .append("Duracion", 10)
            .append("Nivel", 6);
        insertarPartida(d7);

        Document d8 = new Document("Xogador", "Yoshi")
            .append("Xogo", "Donkey Kong")
            .append("Puntuacion", 1100)
            .append("Duracion", 8)
            .append("Nivel", 2);
        insertarPartida(d8);

        System.out.println("Partidas insertadas correctamente");
    }

}
