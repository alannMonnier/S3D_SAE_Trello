package com.example.s3d_sae_trello;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.ArrayList;

/**
 * Main qui nous permet de tester les dependances / Diagramme de gantt
 */

public class MainDependance extends Application {

    private ModeleMenu modeleMenu;
    private DiagrammeGantt diagrammeGantt;
    private int x1 = 0;
    private int y1 = 0;
    private final double largueur = Screen.getPrimary().getBounds().getWidth();
    private final double longueur = Screen.getPrimary().getBounds().getHeight();
    private Canvas canvas = new Canvas(largueur, longueur);
    private GraphicsContext gc = canvas.getGraphicsContext2D();


    @Override
    public void init() throws Exception {
        modeleMenu = new ModeleMenu();

        modeleMenu.ajouterColonneLigne("Test", 1);

        // Ajouter des tâches
        Tache tache1 = new Tache(modeleMenu.getTacheCompositeNumId(), "Tache 1", "", 2, 1, LocalDate.of(2020, 10, 1), 0);
        modeleMenu.ajouterCompositeTache(tache1);
        Tache tache2 = new Tache(modeleMenu.getTacheCompositeNumId(), "Tache 2", "", 3, 2, LocalDate.of(2020, 9, 1), 0);
        modeleMenu.ajouterCompositeTache(tache2);
        Tache tache3 = new Tache(modeleMenu.getTacheCompositeNumId(), "Tache 3", "", 1, 3, LocalDate.of(2020, 9, 3), 0);
        modeleMenu.ajouterCompositeTache(tache3);
        Tache tache4 = new Tache(modeleMenu.getTacheCompositeNumId(), "Tache 4", "", 3, 1, LocalDate.of(2020, 8, 1), 0);
        modeleMenu.ajouterCompositeTache(tache4);
        Tache tache5 = new Tache(modeleMenu.getTacheCompositeNumId(), "Tache 5", "", 2, 2, LocalDate.of(2020, 8, 4), 0);
        modeleMenu.ajouterCompositeTache(tache5);

        ArrayList<Tache> meretache1 = new ArrayList<>();
        meretache1.add(tache2);
        meretache1.add(tache3);

        ArrayList<Tache> meretache2 = new ArrayList<>();
        meretache2.add(tache4);

        ArrayList<Tache> meretache3 = new ArrayList<>();
        meretache3.add(tache5);

        // Ajouter des dépendances
        modeleMenu.ajouterDependance(tache1, meretache1, "mere");
        modeleMenu.ajouterDependance(tache2, meretache2, "mere");
        modeleMenu.ajouterDependance(tache3, meretache3, "mere");


    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane p = new Pane();

        // Récupère les taches les plus au fond
        ArrayList<Tache> m = new ArrayList<>();
        m = modeleMenu.recupererTacheFinal();

        ArrayList<ArrayList<Tache>> tachesmere = modeleMenu.recupererListTachesMere(m);

        // Gestion des coordonnées pour créer les liens sous forme de ligne
        Map<Coordonnees, Tache> coords = new HashMap<>();
        for (int i = tachesmere.size() - 1; i > -1; i--) {
            for (Tache tt : tachesmere.get(i)) {
                System.out.println("yyyyyyyyyyyyyyyy");
                System.out.println(tt.getNom());
                if (modeleMenu.getDependance().containsKey(tt)) {
                    coords.put(new Coordonnees(x1 + 50, y1 + 10), tt);
                }
                for (Coordonnees coordonnees : coords.keySet()) {
                    System.out.println(coords.get(coordonnees).getNom());
                    System.out.println(modeleMenu.getDependance().get(coords.get(coordonnees)).contains(tt));
                    if (modeleMenu.getDependance().get(coords.get(coordonnees)).contains(tt)) {
                        gc.strokeLine(coordonnees.getX(), coordonnees.getY(), x1, y1);
                        break;
                    }
                }
                System.out.println("-------------");
                for (int j = tachesmere.size() - 1; j > -1; j--) {
                    for (Tache ta : tachesmere.get(j)) {
                        gc.strokeRect(x1, y1, 50, 20);
                        gc.fillText(ta.getNom(), x1 + 6, y1 + 15);
                        y1 += 25;
                    }
                    x1 += 55;
                    y1 -= 25 + (25 / tachesmere.size());
                }
                p.getChildren().addAll(canvas);

                Scene scene = new Scene(p, Screen.getPrimary().getBounds().getWidth() - 10, Screen.getPrimary().getBounds().getHeight() - 10);
                stage.setScene(scene);
                stage.show();
            }
        }
    }


    public void afficherHierarchie(Tache tacheFinal){
        // Récupère les taches les plus au fond
        ArrayList<Tache> tachemere = modeleMenu.recupererTacheMere(tacheFinal);
        for (Tache tt: tachemere){
            System.out.println(tt.getNom());
            afficherHierarchie(tt);
            gc.strokeRect(x1, y1, 50, 20);
            gc.fillText(tt.getNom(),x1+6, y1+15);
            //x1+= 55;
            y1+=25;
        }
    }

    /**
     * Démonstration utilisation dépendance dans l'itération 1
     */
    public static void main(String[] args) throws IOException {
        Application.launch(args);
    }


}
