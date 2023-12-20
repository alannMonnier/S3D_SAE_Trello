package com.example.s3d_sae_trello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControleurAjoutTache implements EventHandler<ActionEvent> {

    private ModeleMenu modele;

    public ControleurAjoutTache(ModeleMenu m) {
        this.modele = m;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Button b = (Button) actionEvent.getSource();
        try {
            //CompositeTache tache = new CreationTacheFX(this.modele).lancerApp();
            new CreationTacheFX(this.modele, Integer.parseInt(b.getParent().getId())).lancerApp();
            //modele.ajouterCompositeTache(Integer.parseInt(b.getParent().getId()), tache);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
