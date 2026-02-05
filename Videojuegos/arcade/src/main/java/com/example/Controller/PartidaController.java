package com.example.Controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

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
    public List<Document> partidaMaisCurtaPorXogo() {
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
    public List<Document> listaxeSimplificadaDePartidas() {
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

}
