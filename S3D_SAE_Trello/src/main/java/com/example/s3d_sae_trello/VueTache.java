package com.example.s3d_sae_trello;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VueTache extends VBox implements Observateur {

    public VueTache(CompositeTache t, ModeleMenu modeleMenu, int idColonne){
        HBox hb1 = new HBox();
        Label text = new Label(t.getNom());
        MenuBar paramTache = new MenuBar();
        Menu menuParamTache = new Menu("...");
        MenuItem supprimer = new MenuItem("Supprimer tâche");
        MenuItem creerDependance = new MenuItem("Ajouter dépendance");
        MenuItem ajouterSousTache = new MenuItem("Ajouter sous tâche");
        if(modeleMenu.getTypeVue().equals("Archive")){
            MenuItem desarchiver = new MenuItem("Enlever des archives");
            desarchiver.setOnAction(e -> modeleMenu.desarchiverTache(t));
            menuParamTache.getItems().addAll(supprimer, creerDependance, ajouterSousTache, desarchiver);
        }else{
            MenuItem archiver = new MenuItem("Archiver");
            MenuItem deplacer = new MenuItem("Déplacer Tâche");
            archiver.setOnAction(e -> modeleMenu.archiverTache(idColonne, t));
            deplacer.setOnAction(e -> DeplacerTacheFX.afficher(t, modeleMenu, idColonne));
            menuParamTache.getItems().addAll(supprimer, creerDependance, deplacer, ajouterSousTache, archiver);
        }

        paramTache.getMenus().add(menuParamTache);
        Button bSelect = new Button("+");

        supprimer.setOnAction(e -> modeleMenu.supprimerTache(idColonne, t));
        ajouterSousTache.setOnAction(new ControleurSousTache(t, modeleMenu, idColonne));

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
        Button details = new Button("Détails");

        details.setOnAction(e -> afficherDetails(t));


        hb1.getChildren().addAll(text, bSelect, paramTache);
        hb2.getChildren().addAll(nbHeures);
        hb3.getChildren().addAll(circle, df, bsoustache, details);

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

    private void afficherDetails(CompositeTache t) {
        Stage detailStage = new Stage();
        //Permet d'obliger l'utilisateur à choisir avant de changer de fenetre
        detailStage.initModality(Modality.APPLICATION_MODAL);
        detailStage.initStyle(StageStyle.UTILITY);
        detailStage.setTitle("Détails de la tâche");

        VBox detail = new VBox();
        detail.setPadding(new Insets(10));

        Label titleLabel = new Label("Détails de la tâche");
        Label nomLabel = new Label("Nom: " + t.getNom());
        Label dateLabel = new Label("Date: " + t.getDate());
        Label urgenceLabel = new Label("Degré d'urgence: " + t.getDegreUrgence());
        Label tempsEstimeLabel = new Label("Temps estimé: " + t.getTempsEstime() + "h");
        Label realiseLabel = new Label("Réalisée: " + t.getTacheRealise());
        Label dateDebutRealLabel = new Label("Date début réalisation: " + t.getDateDebutReal());
        Label descriptionLabel = new Label("Description: " + t.getDescription());

        detail.getChildren().addAll(
                titleLabel, nomLabel, dateLabel, urgenceLabel, tempsEstimeLabel, realiseLabel,
                dateDebutRealLabel, descriptionLabel
        );

        Scene detailScene = new Scene(detail, 300, 400);
        detailStage.setScene(detailScene);
        detailStage.show();
    }

    @Override
    public void actualiser(Sujet s) {

    }
}
