package com.example.s3d_sae_trello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CreationColonneFX extends Application {

    private ModeleMenu modele;
    private int idColonne;

    public CreationColonneFX(ModeleMenu modele, int idColonne){
        this.modele = modele;
        this.idColonne = idColonne;
    }

    @Override
    public void start(Stage stage) throws Exception {
        GridPane gp = new GridPane();

        TextField tf = new TextField();
        Label l = new Label("Entrez le nom de la colonne");


        Button b = new Button("Valider");
        b.setOnAction(actionEvent -> {
            String nomColonne = tf.getText();
            this.modele.ajouterColonneLigne(nomColonne, this.idColonne);

            stage.close();
        });




        gp.add(l, 0, 0);
        gp.add(tf,1, 0);
        gp.add(b, 0, 1);

        Scene scene = new Scene(gp);
        stage.setScene(scene);
        stage.show();
    }
}
