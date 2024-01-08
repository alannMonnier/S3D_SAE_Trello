package com.example.s3d_sae_trello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ControleurActionMenu implements EventHandler<ActionEvent> {


    private ModeleMenu modele;

    public ControleurActionMenu(ModeleMenu modele){
        this.modele = modele;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        // Récupérer nom menuBar cliqué puis changer type affichage avec methode setTypeVue
        Button b = (Button) actionEvent.getSource();
        System.out.println(b.getText());
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
