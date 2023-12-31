package com.example.s3d_sae_trello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Action engendrée sur les boutons du Menu
 */
public class ControleurActionMenu implements EventHandler<ActionEvent> {

    /**
     * Declarations attributs
     */
    private ModeleMenu modele;

    /**
     * Constructeur
     * @param modele ModeleMenu
     */
    public ControleurActionMenu(ModeleMenu modele) {
        this.modele = modele;
    }

    /**
     * Action a réalisé au click sur le bouton
     */
    public void handle(ActionEvent actionEvent) {
        // Récupérer nom menuBar cliqué puis changer type affichage avec methode setTypeVue
        Button b = (Button) actionEvent.getSource();
        switch ( b.getText() ){
            case "Gantt":
                this.modele.setTypeVue("Gantt");
                break;
            case "Liste tâche":
                this.modele.setTypeVue("Liste");
                break;
            case "Archive":
                this.modele.setTypeVue("Archive");
                break;
            default:
                this.modele.setTypeVue("Colonne");
                break;
        }
    }
}
