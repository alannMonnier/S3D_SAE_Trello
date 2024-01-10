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
    private int y1 = 250;
    private final double largueur = Screen.getPrimary().getBounds().getWidth();
    private final double longueur = Screen.getPrimary().getBounds().getHeight();
    private Canvas canvas = new Canvas(largueur, longueur);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    public GanttFX(ModeleMenu modele) throws IOException {
        this.modeleMenu = modele;


        // Récupère les taches les plus au fond
        ArrayList<Tache> m = modeleMenu.recupererTacheFinal();
        ArrayList<ArrayList<Tache>> tachesmere = modeleMenu.recupererListTachesMere(m);

        LocalDate dateDebut = tachesmere.get(0).get(0).getDateDebutReal();
        LocalDate dateFin = tachesmere.get(tachesmere.size()-1).get(tachesmere.get(tachesmere.size()-1).size()-1).getDateDebutReal();

        int longueurTache = 60;
        int hauteurTache = 40;

        // Création de la barre de temps
        int ecart = x1;
        System.out.println(tachesmere.size());
        int decalage = 0;
        for (int nb=0; nb<tachesmere.size(); nb++){
            LocalDate date = tachesmere.get(nb).get(0).getDateDebutReal();
            gc.fillText(String.valueOf(date), ecart, hauteurTache+5+decalage, x1+longueurTache+5*tachesmere.size());
            gc.strokeLine(ecart, 18, ecart, 15);
            ecart += longueurTache+5;
            decalage+=2;
        }
        gc.strokeLine(x1, 18, x1+(longueurTache+5)*tachesmere.size(), 18);

        // Gestion des coordonnées pour créer les liens sous forme de ligne
        Map<Coordonnees, Tache> coords = new HashMap<>();
        for (int i = tachesmere.size()-1 ; i > -1; i--){
            for (Tache tt : tachesmere.get(i)){
                if(modeleMenu.getDependance().containsKey(tt)){
                    coords.put(new Coordonnees(x1+longueurTache, y1+10), tt);
                }
                int bonus = 0;
                for (Coordonnees coordonnees : coords.keySet()){
                    if(modeleMenu.getDependance().get(coords.get(coordonnees)).contains(tt)){
                        gc.setFill(Color.RED);
                        gc.strokeLine(coordonnees.getX(), coordonnees.getY(), x1, y1+bonus);
                        bonus+= 25;
                        break;
                    }
                }
                gc.strokeRect(x1, y1, longueurTache, hauteurTache);
                gc.fillText(tt.getNom(),x1+6, y1+15);
                y1+= hauteurTache+5;
            }
            x1 += longueurTache+5;
            y1 -= hauteurTache+5 + (hauteurTache+5/tachesmere.size());
        }

        this.getChildren().addAll(canvas);
    }
}