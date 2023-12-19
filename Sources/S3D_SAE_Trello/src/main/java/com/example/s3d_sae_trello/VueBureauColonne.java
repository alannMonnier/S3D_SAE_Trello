package com.example.s3d_sae_trello;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class VueBureauColonne extends HBox implements Observateur {

    private ModeleMenu modele;
    private int id;
    public VueBureauColonne(ModeleMenu modele){
        this.modele = modele;
        this.id = 0;

        HBox hvide = new HBox();
        BorderPane bp = new BorderPane();
        Label lbp = new Label("+ Ajouter une nouvelle colonne");
        bp.setCenter(lbp);
        lbp.setOnMouseClicked(new ControleurColonneLigne(modele, this.id));
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

        /**
        Node n;
        switch (modele.getTypeVue()){
            case "Gantt":
                n = new VueGantt();
                System.out.println("la");
                break;
            case "Liste":
                System.out.println("lz");
                n = new VueBureauLigne(modele);
                break;
            default:
                System.out.println("li");
                n = new VueBureauColonne(modele);
                break;
        }*/

        // Création des colonnes du trello
        for(int i=0; i< modele.getNbColonnes(); i++){
            // Création de la première colonne du Trello
            ColonneLigne cl = modele.getColonneLignes().get(i);
            VBox col = new VueColonne(cl.getTacheList(), modele, i, cl.getNom());
            //modele.ajouterObservateur((Observateur) col);
            this.getChildren().addAll(col);
            this.id++;
        }


        HBox hvide = new HBox();
        BorderPane bp = new BorderPane();
        Label lbp = new Label("+ Ajouter une nouvelle colonne");
        bp.setCenter(lbp);
        lbp.setOnMouseClicked(new ControleurColonneLigne(modele, this.id));
        hvide.getChildren().addAll(bp);
        hvide.setBorder(Border.stroke(Color.BLACK));
        hvide.setPadding(new Insets(5));
        hvide.setId(""+this.id);

        this.getChildren().addAll(hvide);
        this.setPadding(new Insets(5));
        this.setSpacing(30);
    }
}
