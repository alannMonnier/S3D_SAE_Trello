package com.example.s3d_sae_trello;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Affichage des dépendances
 */
public class DependanceFX extends Application {
    private Tache t;
    private ModeleMenu modeleMenu;
    private ArrayList<Tache> tachesSelectionnee;
    private ArrayList<Tache> tachesAjouterDependance;
    private String type;

    /**
     * Constructeur
     * @param t tache
     * @param modeleMenu ModeleMenu
     * @param taches taches à ajouter dépendance
     * @param type type de dépandance mere ou fille
     */
    public DependanceFX(Tache t, ModeleMenu modeleMenu, ArrayList<Tache> taches, String type) {
        this.t = t;
        this.modeleMenu = modeleMenu;
        this.tachesSelectionnee = new ArrayList<>();
        this.tachesAjouterDependance = taches;
        this.type = type;
    }


    @Override
    public void start(Stage stage) throws Exception {
        GridPane gp = new GridPane();

        int colonne = 0;
        int ligne = 1;


        String s = tachesAjouterDependance.size()==0 ? "Pas de dépendance possible à ajouter" : "Tâches auxquelles ajoutées un dépendance ";
        gp.add(new Label(s), 0, 0);
        for (Tache tache: this.tachesAjouterDependance){
            VueTache vbTache = new VueTache(tache, modeleMenu, 0);
            vbTache.setBackground(Background.fill(Color.WHITE));
            vbTache.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    VueTache vt = ((VueTache) mouseEvent.getSource());
                    Tache t = vt.getTacheCourante();
                    if(vt.getBackground().equals(Background.fill(Color.RED))){
                        vt.setBackground(Background.fill(Color.WHITE));
                        modeleMenu.supprimerTacheListeTacheDependance(t);
                    }
                    else{
                        vt.setBackground(Background.fill(Color.RED));
                        modeleMenu.ajouterTacheListeTacheDependance(t);
                    }
                }
            });

            gp.add(vbTache, colonne, ligne);
            gp.setVgap(10);
            gp.setHgap(10);


            if(colonne % 2 == 0){
                colonne++;
            }
            else{
                colonne--;
                ligne++;
            }
        }

        Button bValider = new Button("Valider");
        bValider.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tachesSelectionnee = modeleMenu.getTachesAjouterDependance();
                modeleMenu.ajouterDependance(t, tachesSelectionnee, type);
                stage.close();
            }
        });
        gp.add(bValider, colonne, ligne);

        Scene scene = new Scene(gp, 600, 350);
        stage.setScene(scene);
        stage.show();
    }
}
