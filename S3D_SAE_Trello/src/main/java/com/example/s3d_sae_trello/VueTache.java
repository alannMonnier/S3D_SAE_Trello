package com.example.s3d_sae_trello;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class VueTache extends VBox implements Observateur {

    public VueTache(CompositeTache t, ModeleMenu modeleMenu, int idColonne){
        HBox hb1 = new HBox();
        Label text = new Label(t.getNom());
        MenuBar paramTache = new MenuBar();
        Menu menuParamTache = new Menu("...");
        MenuItem supprimer = new MenuItem("Supprimer tâche");
        MenuItem archiver = new MenuItem("Archiver");
        MenuItem creerDependance = new MenuItem("Ajouter dépendance");
        MenuItem deplacer = new MenuItem("Déplacer Tâche");
        MenuItem ajouterSousTache = new MenuItem("Ajouter sous tâche");
        ajouterSousTache.setOnAction(new ControleurSousTache(t, modeleMenu, idColonne));
        menuParamTache.getItems().addAll(supprimer, archiver, creerDependance, deplacer, ajouterSousTache);
        paramTache.getMenus().add(menuParamTache);
        Button bSelect = new Button("+");

        HBox hb2 = new HBox();
        Label nbHeures = new Label(""+t.getTempsEstime()+"h");

        HBox hb3 = new HBox();
        int urg = t.getDegreUrgence();
        Circle circle;
        if(urg == 1){
            circle = new Circle(10, Color.BLUE);
        } else if (urg == 2) {
            circle = new Circle(10, Color.ORANGE);
        }else{
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


        // Création sous tâches
        /**
        for()
         // Création de la sous tâche
         VBox soustache = new VBox();
         HBox hb1st = new HBox();
         Label textst = new Label("C'est le premier texte \nde la tâche 1");
         MenuBar paramTachest = new MenuBar();
         Menu menuParamTachest = new Menu("...");
         MenuItem supprimerst = new MenuItem("Supprimer tâche");
         MenuItem archiverst = new MenuItem("Archiver");
         MenuItem creerDependancest = new MenuItem("Ajouter dépendance");
         MenuItem deplacerst = new MenuItem("Déplacer Tâche");
         MenuItem ajouterSousTachest = new MenuItem("Ajouter sous tâche");
         menuParamTachest.getItems().addAll(supprimerst, archiverst, creerDependancest, deplacerst, ajouterSousTachest);
         paramTachest.getMenus().add(menuParamTachest);
         Button bSelectst = new Button("+");

         HBox hb2st = new HBox();
         Label categst = new Label("Catégorie");
         Label nbHeuresst = new Label("2h");

         HBox hb3st = new HBox();
         Circle circlest = new Circle(10, Color.BLUE);
         Label dfst = new Label("dd/mm/yyyy");
         Label usersst = new Label("AMB");
         Button bsoustachest = new Button("↓");


         hb1st.getChildren().addAll(textst, bSelectst, paramTachest);
         hb2st.getChildren().addAll(categst, nbHeuresst);
         hb3st.getChildren().addAll(circlest, dfst, usersst, bsoustachest);

         hb1st.setSpacing(10);
         hb2st.setSpacing(10);
         soustache.getChildren().addAll(hb1st,new Line(0, 0, 200, 0), hb2st, hb3st);
         soustache.setPadding(new Insets(5));
         soustache.setBorder(Border.stroke(Color.BLACK));
         soustache.setSpacing(5);

         tache.getChildren().addAll(hb1,new Line(0, 0, 200, 0), hb2, hb3, soustache);
         tache.setPadding(new Insets(5));
         tache.setBorder(Border.stroke(Color.BLACK));
         tache.setSpacing(5);
            */

        this.getChildren().addAll(hb1,new Line(0, 0, 200, 0), hb2, hb3);
        this.setPadding(new Insets(5));
        this.setBorder(Border.stroke(Color.BLACK));
        this.setSpacing(5);
    }

    @Override
    public void actualiser(Sujet s) {

    }
}
