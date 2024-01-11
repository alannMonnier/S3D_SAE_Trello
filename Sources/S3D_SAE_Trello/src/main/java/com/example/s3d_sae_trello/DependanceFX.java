package com.example.s3d_sae_trello;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
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
        ScrollPane scrollPane = new ScrollPane();
        GridPane gp = new GridPane();
        scrollPane.setContent(gp);

        int colonne = 0;
        int ligne = 1;


        String s = tachesAjouterDependance.size() == 0 ? "Pas de dépendance possible à ajouter" : "Tâches auxquelles ajoutées une dépendance ";
        gp.add(new Label(s), 0, 0);

        for (Tache tache : this.tachesAjouterDependance) {
            VueTache vbTache = new VueTache(tache, modeleMenu, 0);

            // Styles de fond pour les états sélectionné (bleu pâle) et non sélectionné (blanc)
            BackgroundFill backgroundFillBlanc = new BackgroundFill(Color.WHITE, new CornerRadii(5), Insets.EMPTY);
            BackgroundFill backgroundFillSelection = new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), Insets.EMPTY);

            // Effet d'ombre
            DropShadow shadow = new DropShadow();
            shadow.setColor(Color.GRAY);
            shadow.setRadius(5);
            shadow.setOffsetX(2);
            shadow.setOffsetY(2);

            vbTache.setBackground(new Background(backgroundFillBlanc));
            vbTache.setEffect(shadow);

            vbTache.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    VueTache vt = ((VueTache) mouseEvent.getSource());
                    Tache t = vt.getTacheCourante();
                    if (vt.getBackground().getFills().get(0).getFill().equals(Color.LIGHTBLUE)) {
                        vt.setBackground(new Background(backgroundFillBlanc));
                        vt.setScaleX(1.0);
                        vt.setScaleY(1.0);
                        modeleMenu.supprimerTacheListeTacheDependance(t);
                    } else {
                        vt.setBackground(new Background(backgroundFillSelection));
                        vt.setScaleX(1.1);
                        vt.setScaleY(1.1);
                        modeleMenu.ajouterTacheListeTacheDependance(t);
                    }
                }
            });

            vbTache.setOnMouseEntered(e -> vbTache.setEffect(new DropShadow(10, Color.BLACK))); // Effet au survol
            vbTache.setOnMouseExited(e -> vbTache.setEffect(shadow)); // Effet par défaut


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
        double largeur = Screen.getPrimary().getBounds().getWidth();
        double hauteur = Screen.getPrimary().getBounds().getHeight();

        Scene scene = new Scene(scrollPane, largeur-10, hauteur-60);
        stage.setScene(scene);
        stage.show();
    }
}
