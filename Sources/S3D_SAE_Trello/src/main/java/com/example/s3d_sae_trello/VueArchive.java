package com.example.s3d_sae_trello;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Affichage VueArchive
 */
public class VueArchive extends VBox implements Observateur {

    /**
     * Declaration attributs
     */
    private ModeleMenu modele;

    /**
     * Constructeur
     * @param modele ModeleMenu
     */
    public VueArchive(ModeleMenu modele) {
        this.modele = modele;
        creerAffichage();
    }

    /**
     * Creer l'affichage
     */
    private void creerAffichage() {
        this.getChildren().clear();

        Label titre = new Label("Archive");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox taches = new VBox(10);
        taches.setAlignment(Pos.TOP_LEFT);

        for (Tache tache : modele.getArchive().getTacheList()) {
            VBox vueTache = new VueTache(tache, this.modele, -1);
            taches.getChildren().add(vueTache);
        }

        this.getChildren().addAll(titre, taches);
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(10));
        this.setSpacing(10);

    }

    @Override
    public void actualiser(Sujet sujet) {
        creerAffichage();
    }
}