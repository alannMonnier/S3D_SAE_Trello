package com.example.s3d_sae_trello;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Lancement de l'application
 */
public class MainJavaFX extends Application {

    /**
     * Initialisation du modèle avec des données de base à chaque lancement
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();
    }

    /**
     * Lancemnt affichage de l'application
     */
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane racine = new BorderPane();

        ModeleMenu modele = new ModeleMenu();

        // Création du menu
        VBox vmenu = new VBox();
        Label menuTitle = new Label("TRELLO");
        menuTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;"); // Style du titre
        VBox vtitlemenu = new VBox(menuTitle);
        vtitlemenu.setAlignment(Pos.CENTER);
        vtitlemenu.setSpacing(20);

        HBox menuBar = new HBox();
        Button mtableau = new Button("Tableau tâche");
        Button mListe = new Button("Liste tâche");
        Button mGantt = new Button("Gantt");
        Button mArchive = new Button("Archive");

        // Styles des boutons
        String styleBoutons = "-fx-background-color: #8439FF; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 10 20;";
        String styleHover = "-fx-background-color: #C94DC7;";
        for (Button btn : new Button[]{mtableau, mListe, mGantt, mArchive}) {
            btn.setStyle(styleBoutons);
            btn.setOnMouseEntered(e -> btn.setStyle(styleBoutons + styleHover));
            btn.setOnMouseExited(e -> btn.setStyle(styleBoutons));
        }

        menuBar.getChildren().addAll(mtableau, mListe, mGantt, mArchive);
        menuBar.setPadding(new Insets(10));
        menuBar.setSpacing(20);

        mtableau.setOnAction(new ControleurActionMenu(modele));
        mListe.setOnAction(new ControleurActionMenu(modele));
        mGantt.setOnAction(new ControleurActionMenu(modele));
        mArchive.setOnAction(new ControleurActionMenu(modele));

        vmenu.getChildren().addAll(vtitlemenu, menuBar);

        racine.setTop(vmenu);

        GridPane gp = new VueBureau(modele);
        gp.setStyle("-fx-background-color: white;");
        modele.ajouterObservateur((Observateur) gp);

        gp.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gp.setHgap(25);
        gp.setVgap(40);


        // Utiliser un ScrollPane pour englober le GridPane
        ScrollPane scrollPane = new ScrollPane(gp);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        racine.setCenter(scrollPane);
        modele.recupererSauvegardeColonneLigne();
        modele.recupererSauvegardeArchive();
        modele.recupererDependance();

        stage.setOnCloseRequest(windowEvent -> {
            try {
                modele.sauvegarderDependance(); // Appel lors de la fermeture pour sauvegarder la TreeMap
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        double largeur = Screen.getPrimary().getBounds().getWidth();
        double hauteur = Screen.getPrimary().getBounds().getHeight();

        Scene scene = new Scene(racine, largeur - 10, hauteur - 60);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
