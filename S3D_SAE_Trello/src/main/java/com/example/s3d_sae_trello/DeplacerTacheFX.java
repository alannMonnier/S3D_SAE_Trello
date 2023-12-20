package com.example.s3d_sae_trello;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeplacerTacheFX {

    public static void afficher(CompositeTache t, ModeleMenu modeleMenu, int idColonne) {
        Stage stage = new Stage();
        //Permet d'obliger l'utilisateur à choisir avant de changer de fenetre
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Choisir la colonne de destination");

        VBox vbox = new VBox();
        ComboBox<String> colonnesComboBox = new ComboBox<>();
        ObservableList<String> colonnes = FXCollections.observableArrayList();
        for (ColonneLigne colonneLigne : modeleMenu.getColonneLignes()) {
            colonnes.add(colonneLigne.getNom());
        }
        colonnesComboBox.setItems(colonnes);

        Button deplacerButton = new Button("Déplacer");

        deplacerButton.setOnAction(e -> {
            String selectcol = colonnesComboBox.getValue();
            if (selectcol != null) {
                int nouvellecolonne = colonnes.indexOf(selectcol);
                modeleMenu.deplacerCompositeTache(idColonne, nouvellecolonne, t);
                stage.close();
            }
        });

        vbox.getChildren().addAll(colonnesComboBox, deplacerButton);

        Scene scene = new Scene(vbox, 300, 150);
        stage.setScene(scene);
        stage.show();
    }
}