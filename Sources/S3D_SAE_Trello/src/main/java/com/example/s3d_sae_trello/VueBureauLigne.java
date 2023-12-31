package com.example.s3d_sae_trello;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Affichage en BureauLigne
 */
public class VueBureauLigne extends HBox implements Observateur {

    BorderStroke borderStroke = new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT);

    private Border border = new Border(borderStroke);

    private ModeleMenu modele;
    private int id;

    /**
     * Constructeur
     * @param modele ModeleMenu
     */
    public VueBureauLigne(ModeleMenu modele) {
        this.modele = modele;
        this.id = 0;

        HBox hvide = new HBox();
        BorderPane bp = new BorderPane();
        Label lbp = new Label("+ Ajouter une nouvelle colonne");
        bp.setCenter(lbp);
        lbp.setOnMouseClicked(new ControleurColonneLigne(modele, this.id));
        hvide.getChildren().addAll(bp);
        hvide.setBorder(border);
        hvide.setPadding(new Insets(5));
        hvide.setId("" + this.id);

        this.getChildren().addAll(hvide);
        this.setPadding(new Insets(5));
        this.setSpacing(30);
    }

    @Override
    public void actualiser(Sujet s) {
        this.getChildren().clear();
        // Création des colonnes du trello
        for (int i = 0; i < modele.getNbColonnes(); i++) {
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
        hvide.setBorder(border);
        hvide.setPadding(new Insets(5));
        hvide.setId("" + this.id);

        this.getChildren().addAll(hvide);
        this.setPadding(new Insets(5));
        this.setSpacing(30);
    }
}
