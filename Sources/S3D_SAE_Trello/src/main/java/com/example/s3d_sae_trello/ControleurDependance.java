package com.example.s3d_sae_trello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Action ajout dépendance
 */
public class ControleurDependance implements EventHandler<ActionEvent> {

    /**
     * Declaration attributs
     */
    private ModeleMenu modeleMenu;
    private Tache t;

    /**
     * Constructeur
     * @param t Tache ou l'on va ajouter dépendance
     * @param modeleMenu ModeleMenu
     */
    public ControleurDependance(Tache t, ModeleMenu modeleMenu) {
        this.t = t;
        this.modeleMenu = modeleMenu;
    }

    /**
     * Ajout de dépendance fille ou mere lorsqu'on clique sur le bouton
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        MenuItem mi = (MenuItem) actionEvent.getSource();
        // Ouvre une Fenetre ou on va récupérer la liste des tâches mères ou filles
        if(mi.getText().equals("Ajouter dépendance Mère")){
            try {
                new DependanceFX(t, modeleMenu, modeleMenu.recupererToutesTachesSansMere(t), "mère").start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if(mi.getText().equals("Ajouter dépendance Fille")){
            try {
                new DependanceFX(t, modeleMenu, modeleMenu.recupererToutesTachesSansFille(t), "fille").start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
