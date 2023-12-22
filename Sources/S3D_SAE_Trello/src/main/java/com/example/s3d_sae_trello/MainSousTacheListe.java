package com.example.s3d_sae_trello;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainSousTacheListe extends Application {

    private Border createElegantBorder() {
        // Choix de la couleur
        Color borderColor = Color.LIGHTGRAY;

        // Style de bordure (opté pour simple et solide pour l'instant)
        BorderStrokeStyle borderStyle = BorderStrokeStyle.SOLID;

        // Coins arrondis
        CornerRadii cornerRadii = new CornerRadii(5); // ajuster la valeur pour l'arrondi des coins

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

    BorderStroke borderStroke = new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT);

    private Border border = createElegantBorder();

    @Override
    public void start(Stage stage) {
        BorderPane racine = new BorderPane();

        HBox menuHeader = new HBox();
        menuHeader.setAlignment(Pos.CENTER);
        menuHeader.setSpacing(20);
        Label labelTableau = new Label("Tableau");
        Label labelListe = new Label("Liste");
        Label labelGantt = new Label("Gantt");
        menuHeader.getChildren().addAll(labelTableau, labelListe, labelGantt);
        racine.setTop(menuHeader);


        VBox listeTaches = new VBox();
        listeTaches.setSpacing(10);
        listeTaches.setPadding(new Insets(10));
        listeTaches.setAlignment(Pos.CENTER);

        Accordion accordion = new Accordion();

        // Création des listes de tâches avec leurs sous-tâches -- TEST AVEC DES TACHES PRE REMPLIES
        Node listeAfaire = creerTache("À faire", "Tâche 1", new ArrayList<>()); // Pas de sous-tâches pour À faire
        Node listeEnCours = creerTache("En cours", "Tâche 2", Arrays.asList("Sous-tâche 2.1", "Sous-tâche 2.2")); // Deux sous-tâches pour En cours
        Node listeTerminee = creerTache("Terminé", "Tâche 3", Collections.singletonList("Sous-tâche 3.1")); // Une sous-tâche pour Terminé

        // Ajout des listes de tâches à l'accordion -- systeme de liste descendante



        // vérification pour décider comment l'objet doit être ajouté à l'interface utilisateur

        // si pas = TiltedPane on ajoute cet objet directement à listeTaches qui est un VBox avec listeTaches.getChildren().add(listeAfaire).


        if (listeAfaire instanceof TitledPane) {
            accordion.getPanes().add((TitledPane) listeAfaire);
        } else {
            listeTaches.getChildren().add(listeAfaire);
        }

        if (listeEnCours instanceof TitledPane) {
            accordion.getPanes().add((TitledPane) listeEnCours);
        } else {
            listeTaches.getChildren().add(listeEnCours);
        }

        if (listeTerminee instanceof TitledPane) {
            accordion.getPanes().add((TitledPane) listeTerminee);
        } else {
            listeTaches.getChildren().add(listeTerminee);
        }

        // Ajout de l'accordion à la liste principale des tâches
        listeTaches.getChildren().add(accordion);

        // Configuration finale de la scène et affichage
        racine.setCenter(listeTaches);
        Scene scene = new Scene(racine, 1000, 800);
        stage.setScene(scene);
        stage.show();
    }

    private Node creerTache(String titreListe, String titre, List<String> nomsSousTaches) {
        VBox contenuTache = new VBox();
        contenuTache.setBorder(border);
        contenuTache.setPadding(new Insets(5));
        contenuTache.setSpacing(5);

        // Créer et styler le titre de la liste
        Label labelListTitle = new Label(titreListe);
        labelListTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        labelListTitle.setPadding(new Insets(0, 0, 15, 0));

        // Créer et styler le titre de la tâche
        Label labelTitre = new Label(titre);
        labelTitre.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        labelTitre.setPadding(new Insets(0, 0, 5, 0));

        // Ajouter les détails de la tâche
        Label labelDescription = new Label("Description de la tâche");
        Label labelCategorie = new Label("Catégorie: Travail");
        Label labelDuree = new Label("Durée estimée: 2 heures");
        Label labelDate = new Label("Date d'échéance: 20/12/2023");

        // Ajouter les contrôles au contenu de la tâche principale
        contenuTache.getChildren().addAll(labelListTitle, labelTitre, labelDescription, labelCategorie, labelDuree, labelDate);

        VBox sousTachesConteneur = new VBox();
        sousTachesConteneur.setSpacing(5);
        creerSousTaches(nomsSousTaches, sousTachesConteneur, 0); // on commence sans indentation

        if (!nomsSousTaches.isEmpty()) {
            TitledPane tacheAvecSousTaches = new TitledPane(null, sousTachesConteneur);
            tacheAvecSousTaches.setCollapsible(true);
            tacheAvecSousTaches.setExpanded(false);
            contenuTache.getChildren().add(tacheAvecSousTaches);
        }

        return contenuTache;
    }

    private void creerSousTaches(List<String> nomsSousTaches, VBox conteneur, int niveauIndentation) {
        int indentation = niveauIndentation + 20; // indentation de 20 pixels par niveau
        for (String nomSousTache : nomsSousTaches) {
            VBox sousTache = creerDetailTache(nomSousTache, indentation);
            conteneur.getChildren().add(sousTache);
            indentation += 20;
        }
    }

    private VBox creerDetailTache(String nomSousTache, int indentation) {
        VBox detailTache = new VBox();
        detailTache.setPadding(new Insets(5, 5, 5, indentation));
        detailTache.setSpacing(5);
        detailTache.setBorder(border);

        Label titreSousTache = new Label(nomSousTache);
        titreSousTache.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

        // Ajouter les mêmes détails que pour la tâche principale
        detailTache.getChildren().addAll(
                titreSousTache,
                new Label("Description de la sous-tâche"),
                new Label("Catégorie: Travail"),
                new Label("Durée estimée: 1 heure"),
                new Label("Date d'échéance: 21/12/2023")
        );

        return detailTache;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
