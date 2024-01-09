package com.example.s3d_sae_trello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Action ajout d'une soustache
 */
public class ControleurAjoutSousTache implements EventHandler<ActionEvent> {

    /**
     * Declarations attributs
     */
    private ModeleMenu modele;
    private int idColonne;
    private int idTacheMere;

    /**
     * Constructeur
     * @param m ModeleMenu
     * @param idColonne id de la colonne
     * @param idTacheMere id de la tache Mere
     */
    public ControleurAjoutSousTache(ModeleMenu m, int idColonne, int idTacheMere) {
        this.modele = m;
        this.idColonne = idColonne;
        this.idTacheMere = idTacheMere;
    }

    /**
     * Action au moment du click propose création de la tâche
     */
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
