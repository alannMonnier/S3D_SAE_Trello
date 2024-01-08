package com.example.s3d_sae_trello;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class VueBureau extends HBox implements Observateur {

    private ModeleMenu modele;
    private int id;
    public VueBureau(ModeleMenu modele){
        this.modele = modele;
        this.id = 0;

        HBox hvide = new HBox();
        BorderPane bp = new BorderPane();
        Button btn = new Button("+ Ajouter une nouvelle colonne");
        bp.setCenter(btn);
        btn.setOnMouseClicked(new ControleurColonneLigne(modele, this.id));
        hvide.getChildren().addAll(bp);
        hvide.setBorder(Border.stroke(Color.BLACK));
        hvide.setPadding(new Insets(5));
        hvide.setId(""+this.id);

        this.getChildren().addAll(hvide);
        this.setPadding(new Insets(5));
        this.setSpacing(30);
    }

    @Override
    public void actualiser(Sujet s) {
        this.getChildren().clear();

        switch (modele.getTypeVue()){
            case "Gantt":
                VueGantt vg = new VueGantt(modele);

                break;
            case "Liste":

                //n = new VueBureauLigne(modele);
                break;
            case "Archive":
                VueArchive vueArchive = new VueArchive(modele);
                this.getChildren().add(vueArchive);
                break;
            default:
                // Création des colonnes du trello
                for(int i=0; i< modele.getNbColonnes(); i++){
                    // Création de la première colonne du Trello
                    ColonneLigne cl = modele.getColonneLignes().get(i);
                    VBox col = new VueColonne(cl.getTacheList(), modele, i, cl.getNom());
                    this.getChildren().addAll(col);
                    this.id++;
                }

                HBox hvide = new HBox();
                BorderPane bp = new BorderPane();
                Button btn = new Button("+ Ajouter une nouvelle colonne");
                bp.setCenter(btn);
                btn.setOnMouseClicked(new ControleurColonneLigne(modele, this.id));
                hvide.getChildren().addAll(bp);
                hvide.setBorder(Border.stroke(Color.BLACK));
                hvide.setPadding(new Insets(5));
                hvide.setId(""+this.id);

                this.getChildren().addAll(hvide);
                this.setPadding(new Insets(5));
                this.setSpacing(30);
                break;
        }
    }
}
