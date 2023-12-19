package com.example.s3d_sae_trello;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControleurColonneLigne implements EventHandler<MouseEvent> {

    private ModeleMenu modeleMenu;
    private int idColonne;

    public ControleurColonneLigne(ModeleMenu modele, int idColonne){
        this.modeleMenu = modele;
        this.idColonne = idColonne;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        System.out.println();
        try {
            new CreationColonneFX(this.modeleMenu, this.idColonne).start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
