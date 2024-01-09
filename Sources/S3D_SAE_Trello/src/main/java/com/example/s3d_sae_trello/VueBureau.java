package com.example.s3d_sae_trello;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Gestion affichage bureau
 */
public class VueBureau extends VBox implements Observateur {

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

        this.getChildren().addAll(hvide);
        this.setPadding(new Insets(5));
        this.setSpacing(30);
    }

    @Override
    public void actualiser(Sujet s) {
        this.getChildren().clear();

        switch (modele.getTypeVue()) {
            case "Gantt":
                VueGantt vg = new VueGantt(modele);
                break;
            case "Liste":
                // Création des listes du trello
                for (int i = 0; i < modele.getNbColonnes(); i++) {
                    // obtenir le titre et les taches de chaque colonne à partir du modèle
                    ColonneLigne cl = modele.getColonneLignes().get(i);
                    VueListe v = new VueListe(cl.getTacheList(), modele, i, cl.getNom());
                    this.getChildren().add(v);
                }

                // Ajout du bouton pour ajouter une nouvelle colonne
                // Style du bouton pour ajouter une nouvelle colonne
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
                this.getChildren().add(btnAjouterColonne);
                this.setPadding(new Insets(5));
                this.setSpacing(10); // Ajuster l'espacement selon le besoin

                break;

            case "Archive":
                VueArchive vueArchive = new VueArchive(modele);
                this.getChildren().add(vueArchive);
                break;
            default:
                // Création des colonnes du trello
                for (int i = 0; i < modele.getNbColonnes(); i++) {
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
                hvide.setBorder(border);
                hvide.setPadding(new Insets(5));
                hvide.setId("" + this.id);

                this.getChildren().addAll(hvide);
                this.setPadding(new Insets(5));
                this.setSpacing(30);
                break;
        }

    }
}
