package com.example.s3d_sae_trello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;

import javafx.scene.input.*;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class VueTache extends VBox implements Observateur {

    BorderStroke borderStroke = new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT);

    private Border border = new Border(borderStroke);

    private ModeleMenu modeleMenu;
    private int idColonne;
    private int idAncienneColonne;
    private Tache ancienneTache;
    private Tache tacheCourante;
    private Tache tacheEnMouvement;
    private Tache sousTacheEnMouvement;


    public VueTache(Tache t, ModeleMenu modeleMenu, int idColonne) {

        this.modeleMenu = modeleMenu;
        this.idColonne = idColonne;
        this.idAncienneColonne = -1;
        this.ancienneTache = null;
        this.tacheCourante = t;


        HBox hb1 = new HBox();
        Label text = new Label(t.getNom());
        MenuBar paramTache = new MenuBar();
        Menu menuParamTache = new Menu("...");
        MenuItem supprimer = new MenuItem("Supprimer tâche");
        MenuItem creerDependanceMere = new MenuItem("Ajouter dépendance Mère");
        MenuItem creerDependanceFille = new MenuItem("Ajouter dépendance Fille");
        MenuItem ajouterSousTache = new MenuItem("Ajouter sous tâche");

        if (modeleMenu.getTypeVue().equals("Archive")) {
            MenuItem desarchiver = new MenuItem("Enlever des archives");
            desarchiver.setOnAction(e -> {
                try {
                    modeleMenu.desarchiverTache(t);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            menuParamTache.getItems().addAll(supprimer, creerDependanceMere, creerDependanceFille, ajouterSousTache, desarchiver);
        } else {
            MenuItem archiver = new MenuItem("Archiver");
            MenuItem deplacer = new MenuItem("Déplacer Tâche");
            archiver.setOnAction(e -> modeleMenu.archiverTache(idColonne, t));
            deplacer.setOnAction(e -> DeplacerTacheFX.afficher(t, modeleMenu, idColonne));
            menuParamTache.getItems().addAll(supprimer, creerDependanceMere, creerDependanceFille, deplacer, ajouterSousTache, archiver);
        }
        creerDependanceFille.setOnAction(new ControleurDependance(t, modeleMenu));
        creerDependanceMere.setOnAction(new ControleurDependance(t, modeleMenu));
        paramTache.getMenus().add(menuParamTache);

        String select = "+";
        if (t.isEstSelectionne()) {
            select = "✓";
        }
        Button bSelect = new Button(select);
        bSelect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                t.setEstSelectionne(!t.isEstSelectionne());
                if(t.isEstSelectionne()){
                    modeleMenu.ajouterTacheSelectionee(t);
                }
                else{
                    modeleMenu.supprimerTacheSelectionee(t);
                }
                modeleMenu.notifierObservateurs();
            }
        });

        supprimer.setOnAction(e -> modeleMenu.supprimerTache(idColonne, t));
        ajouterSousTache.setOnAction(new ControleurSousTache(t, modeleMenu, idColonne));

        HBox hb2 = new HBox();
        Label nbHeures = new Label("" + t.getTempsEstime() + "h");

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

        Label df = new Label(t.getDateDebutReal().toString());
        Button bsoustache = new Button("↓");
        bsoustache.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                t.setAfficherSousTache(!t.isAfficherSousTache());
                modeleMenu.notifierObservateurs();
            }
        });

        Button details = new Button("Détails");

        details.setOnAction(e -> afficherDetails(t));
        hb1.getChildren().addAll(text, bSelect, paramTache);
        hb2.getChildren().addAll(nbHeures);
        hb3.getChildren().addAll(circle, df, bsoustache, details);

        hb1.setSpacing(10);
        hb2.setSpacing(10);
        hb3.setSpacing(10);


        this.getChildren().addAll(hb1, new Line(0, 0, 200, 0), hb2, hb3);


        if (t.isAfficherSousTache()) {
            // Création sous tâches
            this.vBoxSousTache(t, this);
        }


        // Récupère l'objet à déplacer ainsi qu'une image qui apparaît au déplacement
        this.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Dragboard db = startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putImage(snapshot(new SnapshotParameters(), null));
                content.putString("White");
                db.setContent(content);
                mouseEvent.consume();
            }
        });


        // La tache peut être drag sur une autre tache et accepte de recevoir un transfert
        this.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if ((dragEvent.getGestureSource() != this && dragEvent.getDragboard().hasString())) {
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                }
                dragEvent.consume();
            }
        });


        // Survole d'une Tache
        this.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getGestureSource().getClass().toString().contains("VBox")) {
                    VBox vmere = (VBox) dragEvent.getGestureSource();
                    while (!(vmere.getParent().getClass().toString().contains("VueTache"))) {
                        vmere = (VBox) vmere.getParent();
                    }
                    VueTache vt = (VueTache) vmere.getParent();
                    ancienneTache = vt.tacheCourante;
                }

                else if (dragEvent.getGestureSource() != this && dragEvent.getDragboard().hasString()
                  && dragEvent.getGestureSource().getClass().toString().contains("VueTache")) {

                    VueTache vt = (VueTache) dragEvent.getGestureSource();
                    idAncienneColonne = vt.tacheCourante.getIdcolonne();
                    tacheEnMouvement = vt.tacheCourante;
                }
                dragEvent.consume();
            }
        });


        // Action lorsqu'on dépose l'élément sur une tache
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                Dragboard db = dragEvent.getDragboard();
                boolean success = false;
                if (db.hasString() && dragEvent.getGestureSource().getClass().toString().contains("VueTache")) {
                    if(!tacheEnMouvement.getNom().equals(tacheCourante.getNom())){
                        tacheCourante.ajouterSousTache(tacheEnMouvement);
                        modeleMenu.supprimerTache(idAncienneColonne, tacheEnMouvement);
                    }
                }
                else if(dragEvent.getGestureSource().getClass().toString().contains("VBox")){

                    VBox vb = (VBox) dragEvent.getGestureSource();
                    String nomSousTache = vb.getId();
                    Tache sousTacheCourante = ancienneTache.recupererSousTache(nomSousTache);
                    ancienneTache.getSousTaches().remove(sousTacheCourante);
                    tacheCourante.ajouterSousTache(sousTacheCourante);
                    modeleMenu.notifierObservateurs();
                }
                dragEvent.setDropCompleted(success);
                dragEvent.consume();
            }
        });

        this.setPadding(new Insets(5));
        this.setBorder(border);
        this.setSpacing(5);

    }

    private void afficherDetails(Tache t) {
        Stage detailStage = new Stage();
        //Permet d'obliger l'utilisateur à choisir avant de changer de fenetre
        detailStage.initModality(Modality.APPLICATION_MODAL);
        detailStage.initStyle(StageStyle.UTILITY);
        detailStage.setTitle("Détails de la tâche");

        VBox detail = new VBox();
        detail.setPadding(new Insets(10));

        Label titleLabel = new Label("Détails de la tâche");
        Label nomLabel = new Label("Nom: " + t.getNom());
        Label dateLabel = new Label("Date: " + t.getDateDebutReal());
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

    public void vBoxSousTache(Tache st, VBox vb){
        for (Tache sousTache : st.getSousTaches()){
            VBox vsousTache =  new VBox();
            vsousTache.setId(sousTache.getNom());
            Label l = new Label(sousTache.getNom());

            // Création du menu
            MenuBar paramTache = new MenuBar();
            Menu menuParamTache = new Menu("...");
            MenuItem supprimer = new MenuItem("Supprimer tâche");
            MenuItem archiver = new MenuItem("Archiver");
            MenuItem creerDependanceMere = new MenuItem("Ajouter dépendance Mère");
            MenuItem creerDependanceFille = new MenuItem("Ajouter dépendance Fille");
            MenuItem deplacer = new MenuItem("Déplacer Tâche");
            MenuItem ajouterSousTache = new MenuItem("Ajouter sous tâche");

            // Ajout des actions aux items du menu
            ajouterSousTache.setOnAction(new ControleurSousTache(sousTache, this.modeleMenu, this.idColonne));
            creerDependanceFille.setOnAction(new ControleurDependance(sousTache, modeleMenu));
            creerDependanceMere.setOnAction(new ControleurDependance(sousTache, modeleMenu));

            // Ajout des items au menu
            menuParamTache.getItems().addAll(supprimer, archiver, creerDependanceMere, creerDependanceFille, deplacer, ajouterSousTache);
            paramTache.getMenus().add(menuParamTache);

            // Bouton pour afficher/masquer les sous-tâches
            Button bsoustache = new Button("↓");
            bsoustache.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    sousTache.setAfficherSousTache(!sousTache.isAfficherSousTache());
                    modeleMenu.notifierObservateurs();
                }
            });

            Circle circle;
            if (sousTache.getDegreUrgence() == 1) {
                circle = new Circle(10, Color.BLUE);
            } else if (sousTache.getDegreUrgence() == 2) {
                circle = new Circle(10, Color.ORANGE);
            } else {
                circle = new Circle(10, Color.RED);
            }

            // Bouton pour afficher les détails
            Button details = new Button("Détails");
            details.setOnAction(e -> afficherDetails(sousTache));

            String select = "+";
            if (sousTache.isEstSelectionne()) {
                select = "✓";
            }
            Button bSelect = new Button(select);
            bSelect.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    sousTache.setEstSelectionne(!sousTache.isEstSelectionne());
                    if(sousTache.isEstSelectionne()){
                        modeleMenu.ajouterTacheSelectionee(sousTache);
                    }
                    else{
                        modeleMenu.supprimerTacheSelectionee(sousTache);
                    }

                    modeleMenu.notifierObservateurs();
                }
            });

            HBox hb = new HBox();
            hb.setSpacing(10);
            hb.getChildren().addAll(paramTache, circle, bsoustache, details, bSelect);

            vsousTache.getChildren().addAll(l, hb);
            vsousTache.setPadding(new Insets(5));
            vsousTache.setBorder(border);
            vsousTache.setSpacing(5);

            vb.getChildren().addAll(vsousTache);

            if (sousTache.isAfficherSousTache()) {
                vBoxSousTache(sousTache, vsousTache);
            }

            // Gestion du glisser-déposer
            vsousTache.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Dragboard db = vsousTache.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage(vsousTache.snapshot(new SnapshotParameters(), null));
                    content.putString("White");
                    db.setContent(content);
                    mouseEvent.consume();
                }
            });

            vsousTache.setOnDragOver(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent dragEvent) {
                    if (dragEvent.getGestureSource() != vsousTache && dragEvent.getDragboard().hasString()) {
                        dragEvent.acceptTransferModes(TransferMode.MOVE);
                        VBox vmere = (VBox) dragEvent.getGestureSource();
                        while (!(vmere.getParent().getClass().toString().contains("VueTache"))) {
                            vmere = (VBox) vmere.getParent();
                        }
                        VueTache vt = (VueTache) vmere.getParent();
                        ancienneTache = vt.tacheCourante;
                    }
                    dragEvent.consume();
                }
            });

            vsousTache.setOnDragDropped(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent dragEvent) {
                    Dragboard db = dragEvent.getDragboard();
                    boolean success = false;
                    if (db.hasString()) {
                        VBox vb = (VBox) dragEvent.getGestureSource();
                        String nomSousTache = vb.getId();
                        while (!(vb.getParent().getClass().toString().contains("VueTache"))) {
                            vb = (VBox) vb.getParent();
                        }
                        VueTache vt = (VueTache) vb.getParent();


                        Tache sousTacheCourante = ancienneTache.recupererSousTache(nomSousTache);
                        ancienneTache.supprimerSousTaches(sousTacheCourante);
                        sousTache.ajouterSousTache(sousTacheCourante);

                        modeleMenu.notifierObservateurs();
                    }
                    dragEvent.setDropCompleted(success);
                    dragEvent.consume();
                }
            });
        }
    }

    public Tache getTacheCourante() {
        return tacheCourante;
    }


}
