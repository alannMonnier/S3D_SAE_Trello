package com.example.s3d_sae_trello;

import javafx.scene.layout.HBox;


public class VueGantt extends HBox implements Observateur{

    private ModeleMenu modeleMenu;
    public VueGantt(ModeleMenu modele){
        this.modeleMenu = modele;
    }



    @Override
    public void actualiser(Sujet s) {

    }
}
