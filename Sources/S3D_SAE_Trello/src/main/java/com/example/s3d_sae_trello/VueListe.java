package com.example.s3d_sae_trello;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.util.ArrayList;

public class VueListe extends VBox implements Observateur {

    // Utilisation du style de bordure de la maquette
    private Border createElegantBorder() {
        // Choix de la couleur : Violet foncé
        Color borderColor = Color.web("#EAEAEA"); // Exemple de couleur violet foncé

        // Style de bordure (opté pour simple et solide)
        BorderStrokeStyle borderStyle = BorderStrokeStyle.SOLID;

        // Coins arrondis
        CornerRadii cornerRadii = new CornerRadii(5); // Ajustement de la valeur pour l'arrondi des coins

        // Largeur de bordure
        BorderWidths borderWidths = new BorderWidths(1); // Épaisseur de la bordure

        // Créer la bordure
        BorderStroke borderStroke = new BorderStroke(
                borderColor,
                borderStyle,
                cornerRadii,
                borderWidths
        );

        return new Border(borderStroke);
    }

    private Border border = createElegantBorder();

    private ArrayList<Tache> taches;
    private ModeleMenu modele;
    private int idColonne;
    private String nom;
    private Node node;
    private Accordion accordion;
    private int idColonneADeplace;
    private int idAncienneColonne;
    private Tache ancienneTache;

    // Constructeur de VueListe
    public VueListe(ArrayList<Tache> taches, ModeleMenu m, int id, String nom) {
        this.taches = taches;
        this.modele = m;
        this.idColonne = id;
        this.nom = nom;

        creerListe();
    }

    public void creerListe() {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setBorder(border);
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;");

        Label titreLabel = new Label(nom);
        titreLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #8439FF;");

        // Création du menu à côté du titre
        MenuBar paramCol = new MenuBar();
        Menu menuParamCol = new Menu("...");
        MenuItem tUrgence = new MenuItem("Trier tâche par urgence");
        MenuItem tAlphabetique = new MenuItem("Trier tâche par ordre alphabétique");
        MenuItem tDate = new MenuItem("Trier tâche par date");
        MenuItem tsuppr = new MenuItem("Supprimer cette liste");
        MenuItem tarchiv = new MenuItem("Archiver les tâches de cette liste");


        menuParamCol.getItems().addAll(tUrgence, tAlphabetique, tDate);
        paramCol.getMenus().add(menuParamCol);
        paramCol.setStyle("-fx-background-color: transparent;"); // Fond du MenuBar transparent

        // HBox pour combiner le titre et le menu
        HBox hboxTitleAndMenu = new HBox(10, titreLabel, paramCol);
        hboxTitleAndMenu.setAlignment(Pos.CENTER_LEFT);

        tUrgence.setOnAction(e -> modele.trierColonneLigne(idColonne, "urgence"));
        tAlphabetique.setOnAction(e -> modele.trierColonneLigne(idColonne, "alphabetique"));
        tDate.setOnAction(e -> modele.trierColonneLigne(idColonne, "date"));
        tsuppr.setOnAction(e -> {
            try {
                modele.supprimerColonneLigne(idColonne);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        tarchiv.setOnAction(e -> {
            try {
                modele.archiverToutesTaches(idColonne);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        this.getChildren().add(hboxTitleAndMenu);

        // -------Création des taches ------------

        for (int i = 0; i < this.taches.size(); i++) {
            if(this.taches.get(i) != null){
                if(i == this.taches.size() - 1){
                    TacheListeFX vtacheliste = new TacheListeFX(this.taches.get(i), modele, idColonne);
                    this.getChildren().add(vtacheliste);
                } else {
                    Separator separator = new Separator(Orientation.VERTICAL);
                    separator.setPrefHeight(15); // Hauteur du séparateur

                    HBox separatorBox = new HBox(separator);
                    separatorBox.setAlignment(Pos.CENTER_LEFT); // Caler le séparateur à droite
                    HBox.setHgrow(separatorBox, Priority.ALWAYS); // Pousser le séparateur vers la droite

                    TacheListeFX vtacheliste = new TacheListeFX(this.taches.get(i), modele, idColonne);
                    this.getChildren().addAll(vtacheliste, separatorBox);
                }
            }
        }

        // Bouton d'ajout de tâche avec un style moderne
        Button ajouterTacheBtn = new Button("+ Ajouter une tâche");
        ajouterTacheBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        ajouterTacheBtn.setOnAction(new ControleurAjoutTache(modele));
        this.getChildren().add(ajouterTacheBtn);
        this.setId("" + this.idColonne);
        this.setMinWidth(1850);



        System.out.println("VueListe créée avec le titre: " + nom);
    }

    @Override
    public void actualiser(Sujet s) {
        creerListe();
    }
}