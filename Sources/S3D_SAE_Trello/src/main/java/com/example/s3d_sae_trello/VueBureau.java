package com.example.s3d_sae_trello;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Gestion affichage bureau
 */
public class VueBureau extends GridPane implements Observateur {

    /**
     * Declarations attributs
     */
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
    public VueBureau(ModeleMenu modele) {
        this.modele = modele;
        this.id = 0;

        HBox hvide = new HBox();
        BorderPane bp = new BorderPane();
        Button btn = new Button("+ Ajouter une nouvelle colonne");
        bp.setCenter(btn);
        btn.setOnMouseClicked(new ControleurColonneLigne(modele, this.id));
        hvide.getChildren().addAll(bp);
        hvide.setBorder(border);
        hvide.setPadding(new Insets(5));
        hvide.setId("" + this.id);

        this.add(hvide, 0, 0);
        this.setPadding(new Insets(5));
        this.setAlignment(Pos.CENTER);
    }

    @Override
    public void actualiser(Sujet s) {
        this.getChildren().clear();
        this.setAlignment(Pos.CENTER);


        switch (modele.getTypeVue()) {
            case "Gantt":
                GanttFX vg = null;
                try {
                    vg = new GanttFX(modele);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.add(vg, 0, 0);
                break;
            case "Liste":
                // Création des listes du trello
                int i = 0;
                while( i < modele.getNbColonnes() ) {
                    ColonneLigne cl = modele.getColonneLignes().get(i);
                    VueListe v = new VueListe(cl.getTacheList(), modele, i, cl.getNom());
                    //this.getChildren().add(v);
                    this.add(v, 0, i);
                    i++;
                }

                // Ajout du bouton pour ajouter une nouvelle colonne
                Button btnAjouterColonne = new Button("+ Ajouter une nouvelle colonne");
                btnAjouterColonne.setOnMouseClicked(new ControleurColonneLigne(modele, this.id));

                // Application d'une bordure subtile et moderne
                BorderStroke borderStroke = new BorderStroke(
                        Color.DARKGRAY,
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(5),
                        new BorderWidths(1)
                );
                btnAjouterColonne.setBorder(new Border(borderStroke));

                // Réglage des marges internes du bouton pour un aspect plus spacieux
                btnAjouterColonne.setPadding(new Insets(10, 20, 10, 20));

                // Application de styles CSS pour un look moderne et épuré
                btnAjouterColonne.setStyle(
                        "-fx-font-size: 14px; " +           // taille de la police
                                "-fx-font-family: 'Arial'; " +      // police
                                "-fx-background-color: #F0F0F0; " + // Couleur de fond
                                "-fx-text-fill: #333333; " +        // Couleur du texte
                                "-fx-cursor: hand; ");              // Curseur en forme de main au survol

                // ajout du bouton à la VBox
                //this.getChildren().add(btnAjouterColonne);
                this.add(btnAjouterColonne, 0, i);
                this.setPadding(new Insets(5));
                break;

            case "Archive":
                VueArchive vueArchive = new VueArchive(modele);
                //this.getChildren().add(vueArchive);
                this.add(vueArchive, 0, 0);
                break;

            default:
                // Création des colonnes du trello
                int j = 0;
                while (j < modele.getNbColonnes()) {
                    ColonneLigne cl = modele.getColonneLignes().get(j);
                    VBox col = new ColonneFX(cl.getTacheList(), modele, j, cl.getNom());
                    this.add(col, j , 0);
                    this.id++;
                    j++;
                }

                HBox hvide = new HBox();
                BorderPane bp = new BorderPane();
                Button btn = new Button("+ Ajouter une nouvelle colonne");
                bp.setCenter(btn);
                btn.setOnMouseClicked(new ControleurColonneLigne(modele, this.id));
                hvide.getChildren().addAll(bp);
                hvide.setBorder(border);
                hvide.setPadding(new Insets(5));
                hvide.setId("" + this.id);

                this.add(hvide, j, 0);
                this.setPadding(new Insets(5));
                break;
        }
    }
}
