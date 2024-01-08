package com.example.s3d_sae_trello;

<<<<<<< HEAD
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VueArchive extends VBox implements Observateur {

    private ModeleMenu modele;

    public VueArchive(ModeleMenu modele) {
        this.modele = modele;
        this.modele.ajouterObservateur(this);
        creerAffichage();
    }

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

=======
public class VueArchive implements Observateur {
    @Override
    public void actualiser(Sujet s) {
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b

    }

    @Override
    public void actualiser(Sujet sujet) {
        creerAffichage();
    }
}