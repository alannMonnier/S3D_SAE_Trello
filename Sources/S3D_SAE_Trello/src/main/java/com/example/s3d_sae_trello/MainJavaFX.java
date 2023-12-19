package com.example.s3d_sae_trello;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MainJavaFX extends Application {

    /**
     * Initilisation du modèle avec des données de base à chaque lancement
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

        MenuBar menuBar = new MenuBar();
        Menu mtableau = new Menu("Tableau tâche");
        Menu mListe = new Menu("Liste tâche");
        Menu mGantt = new Menu("Gantt");

        menuBar.getMenus().addAll(mtableau, mListe, mGantt);

        vmenu.getChildren().addAll(vtitlemenu, menuBar);
        racine.setTop(vmenu);

        HBox hCOL = new VueTableau(modele);
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
