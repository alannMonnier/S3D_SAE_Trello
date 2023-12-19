package com.example.s3d_sae_trello;

import javafx.scene.layout.VBox;

public class VueListe extends VBox implements Observateur{

    private ModeleMenu modele;

    public VueListe(){
    }

    @Override
    public void actualiser(Sujet s) {

        ModeleMenu modele = (ModeleMenu) s;

    }
}
