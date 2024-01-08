package com.example.s3d_sae_trello;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class VueListe extends VBox implements Observateur {

    // Utilisation du style de bordure de la maquette

    private Border createElegantBorder() {
        // Choix de la couleur
        Color borderColor = Color.LIGHTGRAY;

        // Style de bordure (opté pour simple et solide pour l'instant)
        BorderStrokeStyle borderStyle = BorderStrokeStyle.SOLID;

        // Coins arrondis
        CornerRadii cornerRadii = new CornerRadii(5); // ajusteme,nt la valeur pour l'arrondi des coins

        // Largeur de bordure
        BorderWidths borderWidths = new BorderWidths(1); // Une bordure fine

        // Créer la bordure (pour éviter une erreur)
        BorderStroke borderStroke = new BorderStroke(
                borderColor,
                borderStyle,
                cornerRadii,
                borderWidths
        );

        // Appliquer la bordure à un objet Border pour l'utiliser plus tard
        return new Border(borderStroke);
    }

    private Border border = createElegantBorder();

    private ArrayList<Tache> taches;
    private ModeleMenu modele;
    private int idColonne;
    private String nom;
    private Node node;
    private Accordion accordion;

    // Constructeur de VueListe
    public VueListe(ArrayList<Tache> taches, ModeleMenu m, int id, String nom) {
        this.taches = taches;
        this.modele = m;
        this.idColonne = id;
        this.nom = nom;

        creerListe();
    }

    public VueListe(ModeleMenu m, String nom, int id) {
        this.modele = m;
        //this.modele.ajouterObservateur(this);
        this.nom = nom;
        this.taches = new ArrayList<>();
        accordion = new Accordion();
        this.idColonne = id;

        creerListe();
    }

    public void creerListe() {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setBorder(createElegantBorder());
        this.setPadding(new Insets(5, 10, 5, 10));
        this.setSpacing(20);

        Label titreLabel = new Label(nom);
        titreLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");


        Button ajouterTacheBtn = new Button("+ Ajouter une tâche");
        ajouterTacheBtn.setOnAction(new ControleurAjoutTache(modele));

        this.getChildren().add(titreLabel);

        // -------Création des taches ------------

        for (int i = 0; i < this.taches.size(); i++) {
            if(this.taches.get(i) != null){
                if(i == this.taches.size() - 1){
                    VueTacheListe vtacheliste = new VueTacheListe(this.taches.get(i), modele, idColonne);
                    this.getChildren().add(vtacheliste);
                } else {
                    Separator separator = new Separator(Orientation.VERTICAL);
                    separator.setPrefHeight(15); // Hauteur du séparateur, à ajuster

                    HBox separatorBox = new HBox(separator);
                    separatorBox.setAlignment(Pos.CENTER_LEFT); // Caler le séparateur à droite
                    HBox.setHgrow(separatorBox, Priority.ALWAYS); // Pousser le séparateur vers la droite

                    VueTacheListe vtacheliste = new VueTacheListe(this.taches.get(i), modele, idColonne);
                    this.getChildren().addAll(vtacheliste, separatorBox);
                }
            }
        }

        this.getChildren().add(ajouterTacheBtn);
        this.setId("" + this.idColonne);

        System.out.println("VueListe créée avec le titre: " + nom);
    }

    @Override
    public void actualiser(Sujet s) {
        creerListe();
    }
}