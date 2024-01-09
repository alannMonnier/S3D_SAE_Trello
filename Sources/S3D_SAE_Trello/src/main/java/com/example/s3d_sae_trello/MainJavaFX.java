package com.example.s3d_sae_trello;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MainJavaFX extends Application {

    /**
     * Initilisation du modèle avec des données de base à chaque lancement
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane racine = new BorderPane();
        ModeleMenu modele = new ModeleMenu();

        // Création du menu
        VBox vmenu = new VBox();
        Label menuTitle = new Label("TRELLO");
        VBox vtitlemenu = new VBox(menuTitle);
        vtitlemenu.setAlignment(Pos.CENTER);
        vtitlemenu.setSpacing(20);

        HBox menuBar = new HBox();
        Button mtableau = new Button("Tableau tâche");
        Button mListe = new Button("Liste tâche");
        Button mGantt = new Button("Gantt");
        Button mArchive = new Button("Archive");
        Button mSauvegarder = new Button("Sauvegarder");
        Button mCharger = new Button("Charger Sauvegarde");


        menuBar.getChildren().addAll(mtableau, mListe, mGantt, mArchive, mSauvegarder, mCharger);
        menuBar.setPadding(new Insets(10));
        menuBar.setSpacing(20);

        mtableau.setOnAction(new ControleurActionMenu(modele));
        mListe.setOnAction(new ControleurActionMenu(modele));
        mGantt.setOnAction(new ControleurActionMenu(modele));
        mArchive.setOnAction(new ControleurActionMenu(modele));
        mSauvegarder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Récupère les tâches sélectionnée
                ArrayList<Tache> tacheSelectionee = modele.getTacheSelectionee();

                // Sauvegarde les taches et sous taches sélectionnées dans un fichier en sérialisant
                try {
                    modele.sauvegarderTaches(tacheSelectionee);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Supprime les tâches à sauvegarder
                modele.supprimerListeTaches(tacheSelectionee);
            }
        });

        mCharger.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Map<Tache, Integer> tacheSelectionee = modele.recupererSauvegarde();
                    // Ajouter les Taches aux bons endroits
                    modele.ajouterMapTache(tacheSelectionee);
                }
                catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        vmenu.getChildren().addAll(vtitlemenu, menuBar);
        racine.setTop(vmenu);


        HBox hCOL = new VueBureau(modele);
        modele.ajouterObservateur((Observateur) hCOL);
        racine.setCenter(hCOL);


        Scene scene = new Scene(racine, 1000, 600);
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
