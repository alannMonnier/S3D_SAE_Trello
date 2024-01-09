package com.example.s3d_sae_trello;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class VueTacheListe extends HBox implements Observateur{

    private ModeleMenu modele;
    private int id;
    private int ancienId;
    private Tache tache;
    private int idAncienneColonne;
    private Tache ancienneTache;
    private Tache tacheCourante;


    private Border createElegantBorderRouge() {
        Color borderColor = Color.RED;
        BorderStrokeStyle borderStyle = BorderStrokeStyle.SOLID;
        // Coins arrondis
        CornerRadii cornerRadii = new CornerRadii(5); // ajuster la valeur pour l'arrondi des coins
        // Largeur de bordure
        BorderWidths borderWidths = new BorderWidths(3); // bordure fine

        // créer la bordure (pour éviter l erreur)
        BorderStroke borderStroke = new BorderStroke(
                borderColor,
                borderStyle,
                cornerRadii,
                borderWidths
        );
        return new Border(borderStroke);
    }

    private Border borderRED = createElegantBorderRouge();


    private Border createElegantBorderBleu() {
        Color borderColor = Color.BLUE;
        BorderStrokeStyle borderStyle = BorderStrokeStyle.SOLID;
        // Coins arrondis
        CornerRadii cornerRadii = new CornerRadii(5); // ajuster la valeur pour l'arrondi des coins
        // Largeur de bordure
        BorderWidths borderWidths = new BorderWidths(3); // bordure fine

        // créer la bordure (pour éviter l erreur)
        BorderStroke borderStroke = new BorderStroke(
                borderColor,
                borderStyle,
                cornerRadii,
                borderWidths
        );
        return new Border(borderStroke);
    }

    private Border borderBleu = createElegantBorderBleu();

    private Border createElegantBorder() {
        Color borderColor = Color.LIGHTGRAY;
        BorderStrokeStyle borderStyle = BorderStrokeStyle.SOLID;
        // Coins arrondis
        CornerRadii cornerRadii = new CornerRadii(5); // ajuster la valeur pour l'arrondi des coins
        // Largeur de bordure
        BorderWidths borderWidths = new BorderWidths(1); // bordure fine

        // créer la bordure (pour éviter l erreur)
        BorderStroke borderStroke = new BorderStroke(
                borderColor,
                borderStyle,
                cornerRadii,
                borderWidths
        );
        return new Border(borderStroke);
    }

    private Border border = createElegantBorder();


    public VueTacheListe(Tache t, ModeleMenu m, int ident) {
        modele = m;
        id = ident;
        ancienId = -999;
        ancienneTache = null;
        tache = t;

        HBox hbooox = new HBox();
        hbooox.setAlignment(Pos.CENTER_LEFT);
        hbooox.setPadding(new Insets(5, 10, 5, 10));
        hbooox.setSpacing(10);
        hbooox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-background-color: white;");
        hbooox.setBorder(borderBleu);

        VBox vBox = new VBox();
        vBox.setSpacing(10);

        Accordion accordion = new Accordion();

        TitledPane titledPaneSousTaches = new TitledPane();
        titledPaneSousTaches.setText("Sous-tâches");

        // Creer une VBox pour contenir les éléments à l'intérieur du TitledPane
        VBox vBox_sous_taches = new VBox();
        //vBox_sous_taches.setBorder(borderRED);
        vBox_sous_taches.setSpacing(40);
        vBox_sous_taches.setPadding(new Insets(-1));

        ArrayList<HBox> liste_SousTaches = this.sousTache(tache, this);
        int decalage = 0;

        System.out.println("entree liste..");
        System.out.println(liste_SousTaches.size());

        for (HBox sousTache : liste_SousTaches){
            vBox_sous_taches.getChildren().add(sousTache);
            vBox_sous_taches.setMargin(sousTache, new Insets(0, 0, 0, 35)); // Ajoute une marge de 20 pixels à gauche du label

            //sousTache.setBorder(borderBleu);
        }

        titledPaneSousTaches.setContent(vBox_sous_taches);
        titledPaneSousTaches.setExpanded(true);

        Label titreTache = new Label(tache.getNom());
        titreTache.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Separator separator1 = new Separator(Orientation.VERTICAL);
        separator1.setPrefHeight(20); // Hauteur du séparateur

        Label tempsEstime = new Label(tache.getTempsEstime() + "h");

        Separator separator2 = new Separator(Orientation.VERTICAL);
        separator2.setPrefHeight(20); // Hauteur du séparateur

        Label dateDuJour = new Label(tache.getDateDebutReal().toString());

        Circle urgenceCircle = new Circle(5, getColorByUrgency(tache.getDegreUrgence()));

        MenuButton menuButtonPlus = new MenuButton("...");
        MenuItem supprimer = new MenuItem("Supprimer tâche");
        MenuItem creerDependance = new MenuItem("Ajouter dépendance");
        MenuItem ajouterSousTache = new MenuItem("Ajouter sous tâche");

        // Ajouter les actions aux éléments de menu
        supprimer.setOnAction(e -> modele.supprimerTache(id, tache));
        ajouterSousTache.setOnAction(new ControleurSousTache(tache, modele, id));

        if (modele.getTypeVue().equals("Archive")) {
            MenuItem desarchiver = new MenuItem("Enlever des archives");
            desarchiver.setOnAction(e -> modele.desarchiverTache(tache));
            menuButtonPlus.getItems().addAll(supprimer, creerDependance, ajouterSousTache, desarchiver);
        } else {
            MenuItem archiver = new MenuItem("Archiver");
            MenuItem deplacer = new MenuItem("Déplacer Tâche");
            archiver.setOnAction(e -> modele.archiverTache(id, tache));
            deplacer.setOnAction(e -> {/* Logique pour déplacer la tâche */});
            menuButtonPlus.getItems().addAll(supprimer, creerDependance, deplacer, ajouterSousTache, archiver);
        }

        Button boutonDetails = new Button("Détails");
        boutonDetails.setOnAction(e -> afficherDetails(tache));

        // utiliser un Pane vide et HBox comme avant pour aligner les boutons à droite
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox buttonBox = new HBox(menuButtonPlus, boutonDetails);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setSpacing(10);


        HBox.setHgrow(vBox, Priority.ALWAYS);  // pour permettre à vBox de s'étendre horizontalement jusqu'au bout

        vBox.getChildren().addAll(hbooox, titledPaneSousTaches);

        hbooox.getChildren().addAll(titreTache, separator1, tempsEstime, separator2, dateDuJour, urgenceCircle, spacer, buttonBox);

        this.getChildren().add(vBox);

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

        // Permet de déposer la tâche dans cette zone précise
        this.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != this && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            }
        });


        // On survol la VBox de la tâche à l'endroit où on l'a placé
        this.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getGestureSource().getClass().toString().contains("VBox")) {
                    VBox vmere = (VBox) dragEvent.getGestureSource();
                    while (!(vmere.getParent().getClass().toString().contains("VueTacheListe"))) {
                        vmere = (VBox) vmere.getParent();
                    }
                    VueTacheListe vt = (VueTacheListe) vmere.getParent();

                    VueListe vl = (VueListe) (vt).getParent();
                    VBox vb2 = (VBox) (vl).getChildren().get(0);
                    BorderPane bp = (BorderPane) (vb2).getChildren().get(0);
                    Label l = (Label) (bp).getChildren().get(0);
                    String txtCol = l.getText();
                    // Récupère l'ancienne colonne de la sous tache
                    idAncienneColonne = modele.recupererColonneLigneID(txtCol);

                    // Récupère le texte marqué sur la tâche mère
                    HBox hb = (HBox) vt.getChildren().get(0);
                    Label lT = (Label) hb.getChildren().get(0);
                    String txtTache = lT.getText();


                    // Récupère l'id de la tâche
                    ancienneTache = modele.recupererTache(idAncienneColonne, txtTache);
                } else if (dragEvent.getGestureSource() != this && dragEvent.getDragboard().hasString()) {
                    VueTacheListe vt = (VueTacheListe) dragEvent.getGestureSource();
                    VueListe vl = (VueListe) (vt).getParent();
                    VBox vb2 = (VBox) (vl).getChildren().get(0);
                    BorderPane bp = (BorderPane) (vb2).getChildren().get(0);
                    Label l = (Label) (bp).getChildren().get(0);
                    String txtCol = l.getText();
                    idAncienneColonne = modele.recupererColonneLigneID(txtCol);
                }
                dragEvent.consume();
            }
        });



       /* this.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    // Récupérer le nom de la tâche déplacée à partir des données du Dragboard
                    String nomTache = db.getString();

                    // Trouver la tâche à déplacer dans le modèle
                    Tache tacheADeplacer = modele.recupererTache(id, nomTache);
                    if (tacheADeplacer != null) {
                        // ID de la colonne cible (ici, 'id' est l'ID de la colonne actuelle dans VueTacheListe)
                        int targetColonneId = id;

                        // Appeler la méthode du modèle pour déplacer la tâche
                        modele.deplacerCompositeTache(id, targetColonneId, tacheADeplacer);

                        success = true; // Marquer l'opération comme réussie
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });*/

        // Action lorsqu'on dépose la tâche à un autre endroit
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                Dragboard db = dragEvent.getDragboard();
                boolean success = false;

                if (db.hasString() && dragEvent.getGestureSource().getClass().toString().contains("VueTacheListe")) {
                    // Récupère le conteneur de la tâche
                    VueTache hdepla = (VueTache) dragEvent.getGestureSource();
                    // Récupère le texte déplacé
                    String texteTacheDepl = ((Label) ((HBox) hdepla.getChildren().get(0)).getChildren().get(0)).getText();

                    // Vérifie si la tâche source est différente de la tâche cible
                    if (!texteTacheDepl.equals(tacheCourante.getNom())) {
                        // Récupère la tâche ou on a déplacé
                        Tache tache = modele.getColonneLignes().get(idAncienneColonne).getTache(texteTacheDepl);
                        t.ajouterSousTache(tache);
                        success = true;
                        // supprime la vbox contenant la tâche
                        modele.supprimerTache(idAncienneColonne, tache);
                    }
                }

                dragEvent.setDropCompleted(success);
                dragEvent.consume();
            }
        });


        this.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                // Nettoyage après le glissement
                event.consume();
            }
        });
    }



    private Color getColorByUrgency(int urgency) {
        switch (urgency) {
            case 1:
                return Color.GREEN;
            case 2:
                return Color.ORANGE;
            case 3:
                return Color.RED;
            default:
                return Color.BLACK;
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
        urgenceLabel.setTextFill(getColorByUrgency(t.getDegreUrgence())); // Couleur de l'urgence
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

        // Ajout d'une marge à gauche pour chaque Label pour un meilleur alignement
        for (Label label : new Label[]{nomLabel, dateLabel, urgenceLabel, tempsEstimeLabel, realiseLabel, dateDebutRealLabel, descriptionLabel}) {
            VBox.setMargin(label, new Insets(0, 0, 0, 10));
            label.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");
        }

        // Ajout de tous les éléments à la VBox
        detailVBox.getChildren().addAll(
                titleLabel, nomLabel, dateLabel, urgenceLabel, tempsEstimeLabel, realiseLabel,
                dateDebutRealLabel, descriptionLabel
        );

        Scene detailScene = new Scene(detailVBox, 400, 500); // Taille ajustée pour mieux s'adapter au contenu
        detailStage.setScene(detailScene);
        detailStage.showAndWait(); // showAndWait pour bloquer les autres fenêtres jusqu'à ce que celle-ci soit fermée
    }




    // Méthode pour créer et afficher les sous-tâches
    private ArrayList<HBox> sousTache(Tache tache, HBox vboxParent) {
        ArrayList<HBox> tabSousTaches = new ArrayList<>();
        for (Tache sousTache : tache.getSousTaches()) {
            // Créer une nouvelle instance de VueTacheListe pour chaque sous-tâche
            VueTacheListe vueSousTache = new VueTacheListe(sousTache, modele, id);

            // Ajouter la VueTacheListe à la liste de sous-tâches
            tabSousTaches.add(vueSousTache);

            // Récursion pour ajouter des sous-tâches de sous-tâches
            sousTache(sousTache, vueSousTache);
        }
        return tabSousTaches;
    }


    @Override
    public void actualiser(Sujet s) {

    }
}
