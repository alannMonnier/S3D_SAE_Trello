package com.example.s3d_sae_trello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ControleurSousTache implements EventHandler<ActionEvent> {

    private ModeleMenu modele;
    private CompositeTache ct;
    private int idColonne;

    public ControleurSousTache(CompositeTache t, ModeleMenu modeleMenu, int idColonne){
        this.ct = t;
        this.idColonne = idColonne;
        this.modele = modeleMenu;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            new CreationTacheFX(this.modele, idColonne).lancerApp();
            ct.ajouterSousTache(this.modele.getColonneLignes().get(idColonne).getCompositeTache(modele.getTacheCompositeNumId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
