package com.example.s3d_sae_trello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ControleurActionMenu implements EventHandler<ActionEvent> {


    private ModeleMenu modele;

    public ControleurActionMenu(ModeleMenu modele) {
        this.modele = modele;
    }


    public void handle(ActionEvent actionEvent) {
        // Récupérer nom menuBar cliqué puis changer type affichage avec methode setTypeVue
        Button b = (Button) actionEvent.getSource();
<<<<<<< HEAD
        switch ( b.getText() ){
=======
        System.out.println(b.getText());
        switch (b.getText()) {
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b
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
