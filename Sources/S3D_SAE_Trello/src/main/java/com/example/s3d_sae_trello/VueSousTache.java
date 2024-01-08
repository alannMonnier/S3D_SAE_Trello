package com.example.s3d_sae_trello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class VueSousTache extends Application {

    private ModeleMenu modeleMenu;
    private Tache tache;

    public VueSousTache(ModeleMenu modeleMenu, Tache tache){
        this.modeleMenu = modeleMenu;
        this.tache = tache;
    }


    @Override
    public void start(Stage stage) throws Exception {
        GridPane gp = new GridPane();

        Label l = new Label("Entrez le contenu de la sous tâche");
        TextField tf = new TextField("SousTache");

        Button b = new Button("Valider");
        b.setOnAction(actionEvent -> {
            String nom = tf.getText();
            SousTache st = new SousTache(tache.getId(), nom, tache.getDegreUrgence(), tache.getTempsEstime(), tache.getDateDebutReal(), tache.getIdSousTache());
            tache.ajouterSousTache(st);
            modeleMenu.notifierObservateurs();
            stage.close();
        });

        gp.add(l, 0, 0);
        gp.add(tf, 1, 0);
        gp.add(b, 0, 2);

        Scene scene = new Scene(gp, 400, 200);
        stage.setScene(scene);
        stage.show();
    }
}
