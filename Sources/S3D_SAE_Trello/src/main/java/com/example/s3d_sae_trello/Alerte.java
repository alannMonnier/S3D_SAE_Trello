package com.example.s3d_sae_trello;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerte {

    /**
     * Génère une alerte de type erreur concernant une date invalide pour une sous-tâche.
     *
     * @return une instance de Alert prête à être affichée.
     */
    public static Alert alerteDate(String titre_alerte, String description) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titre_alerte);
        alert.setHeaderText(null);
        alert.setContentText(description);
        return alert;
    }

}
