package com.example.s3d_sae_trello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ControleurDependance implements EventHandler<ActionEvent> {

    private ModeleMenu modeleMenu;
    private Tache t;
    public ControleurDependance(Tache t, ModeleMenu modeleMenu) {
        this.t = t;
        this.modeleMenu = modeleMenu;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        MenuItem mi = (MenuItem) actionEvent.getSource();
        // Ouvre une Fenetre ou on va récupérer la liste des tâches mères ou filles
        if(mi.getText().equals("Ajouter dépendance Mère")){
            try {
                new VueDependance(t, modeleMenu, modeleMenu.recupererToutesTachesSansMere(t), "mère").start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if(mi.getText().equals("Ajouter dépendance Fille")){
            try {
                new VueDependance(t, modeleMenu, modeleMenu.recupererToutesTachesSansFille(t), "fille").start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
