package com.example.s3d_sae_trello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class ControleurSousTache implements EventHandler<ActionEvent> {

    private ModeleMenu modele;
    private Tache ct;
    private int idColonne;

    public ControleurSousTache(Tache t, ModeleMenu modeleMenu, int idColonne){
        this.ct = t;
        this.idColonne = idColonne;
        this.modele = modeleMenu;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            new VueSousTache(modele, ct).start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
