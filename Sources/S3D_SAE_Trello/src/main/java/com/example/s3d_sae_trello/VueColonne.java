package com.example.s3d_sae_trello;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class VueColonne extends VBox implements Observateur {

    BorderStroke borderStroke = new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT);

    private Border border = new Border(borderStroke);

    private ArrayList<CompositeTache> taches;
    private ModeleMenu modele;
    private int idColonne;
    private String nom;


    public VueColonne(ArrayList<CompositeTache> taches, ModeleMenu m, int id, String nom) {
        this.taches = taches;
        this.modele = m;
        this.idColonne = id;
        this.nom = nom;

        // création header de la colonne
        VBox vTitreCol = new VBox();
        BorderPane bpHeader = new BorderPane();
        Label lTitreCol = new Label(this.nom);
        MenuBar paramCol = new MenuBar();
        Menu menuParamCol = new Menu("...");
        MenuItem tUrgence = new MenuItem("Trier tâche par urgence");
        MenuItem tAlphabetique = new MenuItem("Trier tâche par ordre alphabétique");
        MenuItem tDate = new MenuItem("Trier tâche par date");
        menuParamCol.getItems().addAll(tUrgence, tAlphabetique, tDate);
        paramCol.getMenus().add(menuParamCol);
        Line lineCol = new Line(0, 0, 200, 0);

        bpHeader.setLeft(lTitreCol);
        bpHeader.setRight(paramCol);
        bpHeader.setPadding(new Insets(5));
        vTitreCol.getChildren().addAll(bpHeader, lineCol);
        vTitreCol.setSpacing(6);


        this.getChildren().addAll(vTitreCol);

        // -------Création des taches ------------
        for (CompositeTache ct : this.taches) {
            if (ct != null) {
                VBox tache = new VueTache(ct);
                this.getChildren().addAll(tache);
            }
        }
        //CompositeTache t = new Tache(7, "Tache 7", 2, 3);


        // Ajouter une tâche
        Button laddtache = new Button("+ Ajouter une tâche");
        laddtache.setOnAction(new ControleurAjoutTache(modele));

        this.getChildren().addAll(laddtache);
        this.setBorder(border);
        this.setPadding(new Insets(5));
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);
        this.setId("" + this.idColonne);
    }

    @Override
    public void actualiser(Sujet s) {

    }
}
