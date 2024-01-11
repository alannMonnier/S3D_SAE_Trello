package com.example.s3d_sae_trello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;

import javafx.scene.effect.DropShadow;
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
    // Couleur hexadécimale pour le violet clair (par exemple, "#C8A2C8")
    Color lightVioletColor = Color.web("#C8A2C8");

    BorderStroke borderStroke = new BorderStroke(
            lightVioletColor,              // Utilisation de la couleur violet clair
            BorderStrokeStyle.SOLID,       // Style de bordure solide
            CornerRadii.EMPTY,             // Coins non arrondis
            BorderWidths.DEFAULT           // Largeur de bordure par défaut
    );

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

        // Style général
        this.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;");


        // Créer l'effet d'ombre
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setRadius(2.0);
        //dropShadow.setOffsetX(1.0);
        dropShadow.setOffsetY(1.0);

        this.setEffect(dropShadow);


        Label text = new Label(t.getNom());
        text.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #854684;");
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
            archiver.setOnAction(e -> {
                try {
                    modeleMenu.archiverTache(idColonne, t);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
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

        supprimer.setOnAction(e -> {
            try {
                modeleMenu.supprimerTache(idColonne, t);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        ajouterSousTache.setOnAction(new ControleurSousTache(t, modeleMenu, idColonne));


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

        Circle circle = new Circle(5); // Taille réduite pour un look plus subtil
        circle.setFill(getUrgencyColor(t.getDegreUrgence()));

        HBox hb1 = new HBox(10, text, bSelect, paramTache);
        HBox hb2 = new HBox(10, new Label("Temps estimé: " + t.getTempsEstime() + "h"));
        HBox hb3 = new HBox(10, circle, df, bsoustache, details);

        // Configuration du style des boutons
        String buttonStyle = "-fx-background-radius: 5; -fx-padding: 5 10;";
        bSelect.setStyle(buttonStyle);
        bsoustache.setStyle(buttonStyle);
        details.setStyle(buttonStyle);

        this.getChildren().addAll(hb1, hb2, hb3);

        this.setStyle("-fx-background-color: white;");

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
                        try {
                            modeleMenu.supprimerTache(idAncienneColonne, tacheEnMouvement);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
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
       // this.setBorder(border);
        this.setSpacing(5);

    }

    private Color getUrgencyColor(int urgencyLevel) {
        switch (urgencyLevel) {
            case 1: return Color.GREEN;
            case 2: return Color.ORANGE;
            default: return Color.RED;
        }
    }

    private void afficherDetails(Tache t) {
        Stage detailStage = new Stage();
        detailStage.initModality(Modality.APPLICATION_MODAL);
        detailStage.initStyle(StageStyle.UTILITY);
        detailStage.setTitle("Détails de la tâche");

        VBox detailVBox = new VBox();
        detailVBox.setPadding(new Insets(20));
        detailVBox.setSpacing(10); // Espacement entre les composants
        detailVBox.setStyle("-fx-background-color: #FAFAFA;"); // Couleur de fond

        Label titleLabel = new Label("Détails de la tâche");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-underline: true; -fx-text-fill: #333333;"); // Style du titre

        Label nomLabel = new Label("Nom: " + t.getNom());
        nomLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #666666;"); // Style

        Label dateLabel = new Label("Date: " + t.getDateDebutReal());
        Label urgenceLabel = new Label("Degré d'urgence : " + t.getDegreUrgence());
        urgenceLabel.setTextFill(getUrgencyColor(t.getDegreUrgence())); // Couleur de l'urgence
        Label tempsEstimeLabel = new Label("Temps estimé : " + t.getTempsEstime() + "h");

        String realise = "";

        if (t.getTacheRealise()){
            realise = "oui";
        } else{
            realise = "non";
        }
        Label realiseLabel = new Label("Réalisée : " + realise);
        Label dateDebutRealLabel = new Label("Date début réalisation : " + t.getDateDebutReal());
        Label descriptionLabel = new Label("Description : " + t.getDescription());
        descriptionLabel.setWrapText(true); // Permet au texte de revenir à la ligne

        for (Label label : new Label[]{nomLabel, dateLabel, urgenceLabel, tempsEstimeLabel, realiseLabel, dateDebutRealLabel, descriptionLabel}) {
            VBox.setMargin(label, new Insets(0, 0, 0, 10));
            label.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");
        }

        // Ajout de tous les éléments à la VBox
        detailVBox.getChildren().addAll(
                titleLabel, nomLabel, dateLabel, urgenceLabel, tempsEstimeLabel, realiseLabel,
                dateDebutRealLabel, descriptionLabel
        );

        Scene detailScene = new Scene(detailVBox, 400, 500); // Taille ajustée
        detailStage.setScene(detailScene);
        detailStage.showAndWait(); // showAndWait
    }



    @Override
    public void actualiser(Sujet s) {
    }

    public void vBoxSousTache(Tache st, VBox vb){
        for (Tache sousTache : st.getSousTaches()){
            VBox vsousTache =  new VBox();
            vsousTache.setBorder(border);
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

            Circle circle = new Circle(5); // Taille réduite
            circle.setFill(getUrgencyColor(st.getDegreUrgence()));

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
