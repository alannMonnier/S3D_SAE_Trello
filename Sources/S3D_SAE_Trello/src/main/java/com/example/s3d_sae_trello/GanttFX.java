package com.example.s3d_sae_trello;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class GanttFX extends HBox{

    private ModeleMenu modeleMenu;
    private int x1 = 200;
    private int y1 = 200;
    private final double largueur = Screen.getPrimary().getBounds().getWidth();
    private final double longueur = Screen.getPrimary().getBounds().getHeight();
    private Canvas canvas = new Canvas(largueur, longueur);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    public GanttFX(ModeleMenu modele) throws IOException {
        this.modeleMenu = modele;


        ArrayList<Tache> meretache1 = new ArrayList<>();
        meretache1.add(modele.getColonneLignes().get(0).getTache("Tache 3"));

        ArrayList<Tache> meretache2 = new ArrayList<>();
        meretache2.add(modele.getColonneLignes().get(1).getTache("Tache 4"));



        // Ajouter des dépendances
        modeleMenu.ajouterDependance(modele.getColonneLignes().get(0).getTache("Tache 0"), meretache1, "mere");
        modeleMenu.ajouterDependance(modele.getColonneLignes().get(0).getTache("Tache 3"), meretache2, "mere");



        // Récupère les taches les plus au fond
        ArrayList<Tache> m = modeleMenu.recupererTacheFinal();
        ArrayList<ArrayList<Tache>> tachesmere = modeleMenu.recupererListTachesMere(m);

        LocalDate dateDebut = tachesmere.get(0).get(0).getDateDebutReal();
        LocalDate dateFin = tachesmere.get(tachesmere.size()-1).get(tachesmere.get(tachesmere.size()-1).size()-1).getDateDebutReal();


        // Création de la barre de temps
        gc.fillText(String.valueOf(dateDebut), x1, 18, x1+25*tachesmere.size());
        gc.strokeLine(x1, 10, x1+25*tachesmere.size(), 10);

        // Gestion des coordonnées pour créer les liens sous forme de ligne
        Map<Coordonnees, Tache> coords = new HashMap<>();
        for (int i = tachesmere.size()-1 ; i > -1; i--){
            for (Tache tt : tachesmere.get(i)){
                if(modeleMenu.getDependance().containsKey(tt)){
                    coords.put(new Coordonnees(x1+50, y1+10), tt);
                }
                for (Coordonnees coordonnees : coords.keySet()){
                    if(modeleMenu.getDependance().get(coords.get(coordonnees)).contains(tt)){
                        gc.setFill(Color.RED);
                        gc.strokeLine(coordonnees.getX(), coordonnees.getY(), x1, y1);
                        break;
                    }
                }
                gc.strokeRect(x1, y1, 50, 20);
                gc.fillText(tt.getNom(),x1+6, y1+15);
                y1+= 25;
            }
            x1 += 55;
            y1 -= 25 + (25/tachesmere.size());
        }

        this.getChildren().addAll(canvas);
    }
}
