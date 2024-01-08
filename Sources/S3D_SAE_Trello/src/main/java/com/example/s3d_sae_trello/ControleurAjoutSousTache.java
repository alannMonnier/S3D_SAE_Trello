package com.example.s3d_sae_trello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


public class ControleurAjoutSousTache implements EventHandler<ActionEvent> {
    private ModeleMenu modele;
    private int idColonne;
    private int idTacheMere;

    public ControleurAjoutSousTache(ModeleMenu m, int idColonne, int idTacheMere) {
        this.modele = m;
        this.idColonne = idColonne;
        this.idTacheMere = idTacheMere;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            // Ici, vous utilisez les attributs idColonne et idTacheMere qui ont été initialisés lors de la création du contrôleur.
            new CreationSousTacheFX(this.modele, this.idColonne, this.idTacheMere).lancerApp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
