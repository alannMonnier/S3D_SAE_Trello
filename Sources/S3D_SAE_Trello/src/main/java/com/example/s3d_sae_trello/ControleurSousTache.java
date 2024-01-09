package com.example.s3d_sae_trello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * Action création sousTache
 */
public class ControleurSousTache implements EventHandler<ActionEvent> {

    /**
     * Declaration attributs
     */
    private ModeleMenu modele;
    private Tache ct;
    private int idColonne;

    /**
     * Constructeur
     * @param t soustache
     * @param modeleMenu ModeleMenu
     * @param idColonne id de la colonne à ajouter la soustache
     */
    public ControleurSousTache(Tache t, ModeleMenu modeleMenu, int idColonne){
        this.ct = t;
        this.idColonne = idColonne;
        this.modele = modeleMenu;
    }


    /**
     * Création d'une sousTache
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            new CreationTacheFX(this.modele, this.idColonne, this.ct).lancerApp();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
