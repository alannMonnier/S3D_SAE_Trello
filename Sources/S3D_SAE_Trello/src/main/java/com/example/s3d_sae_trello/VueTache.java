package com.example.s3d_sae_trello;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class VueTache extends VBox implements Observateur {

    BorderStroke borderStroke = new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT);

    private Border border = new Border(borderStroke);

    public VueTache(CompositeTache t) {
        HBox hb1 = new HBox();
        Label text = new Label(t.getNom());
        MenuBar paramTache = new MenuBar();
        Menu menuParamTache = new Menu("...");
        MenuItem supprimer = new MenuItem("Supprimer tâche");
        MenuItem archiver = new MenuItem("Archiver");
        MenuItem creerDependance = new MenuItem("Ajouter dépendance");
        MenuItem deplacer = new MenuItem("Déplacer Tâche");
        MenuItem ajouterSousTache = new MenuItem("Ajouter sous tâche");
        menuParamTache.getItems().addAll(supprimer, archiver, creerDependance, deplacer, ajouterSousTache);
        paramTache.getMenus().add(menuParamTache);
        Button bSelect = new Button("+");

        HBox hb2 = new HBox();
        Label nbHeures = new Label("" + t.getTempsEstime() + "");

        HBox hb3 = new HBox();
        int urg = t.getDegreUrgence();
        Circle circle;
        if (urg == 1) {
            circle = new Circle(10, Color.BLUE);
        } else if (urg == 2) {
            circle = new Circle(10, Color.ORANGE);
        } else {
            circle = new Circle(10, Color.RED);
        }
        Label df = new Label(t.getDate().toString());
        Button bsoustache = new Button("↓");


        hb1.getChildren().addAll(text, bSelect, paramTache);
        hb2.getChildren().addAll(nbHeures);
        hb3.getChildren().addAll(circle, df, bsoustache);

        hb1.setSpacing(10);
        hb2.setSpacing(10);
        hb3.setSpacing(10);


        this.getChildren().addAll(hb1, new Line(0, 0, 200, 0), hb2, hb3);
        this.setPadding(new Insets(5));
        this.setBorder(border);
        this.setSpacing(5);
    }

    @Override
    public void actualiser(Sujet s) {

    }
}
