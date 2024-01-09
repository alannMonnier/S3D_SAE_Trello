package com.example.s3d_sae_trello;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

import java.util.ArrayList;

public class VueListe extends VBox implements Observateur {

    // Utilisation du style de bordure de la maquette

    private Border createElegantBorder() {
        // Choix de la couleur
        Color borderColor = Color.LIGHTGRAY;

        // Style de bordure (opté pour simple et solide pour l'instant)
        BorderStrokeStyle borderStyle = BorderStrokeStyle.SOLID;

        // Coins arrondis
        CornerRadii cornerRadii = new CornerRadii(5); // ajusteme,nt la valeur pour l'arrondi des coins

        // Largeur de bordure
        BorderWidths borderWidths = new BorderWidths(1); // Une bordure fine

        // Créer la bordure (pour éviter une erreur)
        BorderStroke borderStroke = new BorderStroke(
                borderColor,
                borderStyle,
                cornerRadii,
                borderWidths
        );

        // Appliquer la bordure à un objet Border pour l'utiliser plus tard
        return new Border(borderStroke);
    }

    private Border border = createElegantBorder();

    private ArrayList<Tache> taches;
    private ModeleMenu modele;
    private int idColonne;
    private String nom;
    private Node node;
    private Accordion accordion;
    private int idColonneADeplace;
    private int idAncienneColonne;
    private Tache ancienneTache;

    // Constructeur de VueListe
    public VueListe(ArrayList<Tache> taches, ModeleMenu m, int id, String nom) {
        this.taches = taches;
        this.modele = m;
        this.idColonne = id;
        this.nom = nom;

        creerListe();
    }

    public VueListe(ModeleMenu m, String nom, int id) {
        this.modele = m;
        //this.modele.ajouterObservateur(this);
        this.nom = nom;
        this.taches = new ArrayList<>();
        accordion = new Accordion();
        this.idColonne = id;

        creerListe();
    }

    public void creerListe() {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setBorder(createElegantBorder());
        this.setPadding(new Insets(5, 10, 5, 10));
        this.setSpacing(20);

        Label titreLabel = new Label(nom);
        titreLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");



        // -------Création des taches ------------

        for (int i = 0; i < this.taches.size(); i++) {
            if(this.taches.get(i) != null){
                if(i == this.taches.size() - 1){
                    VueTacheListe vtacheliste = new VueTacheListe(this.taches.get(i), modele, idColonne);
                    this.getChildren().add(vtacheliste);
                } else {
                    Separator separator = new Separator(Orientation.VERTICAL);
                    separator.setPrefHeight(15); // Hauteur du séparateur, à ajuster

                    HBox separatorBox = new HBox(separator);
                    separatorBox.setAlignment(Pos.CENTER_LEFT); // Caler le séparateur à droite
                    HBox.setHgrow(separatorBox, Priority.ALWAYS); // Pousser le séparateur vers la droite

                    VueTacheListe vtacheliste = new VueTacheListe(this.taches.get(i), modele, idColonne);
                    this.getChildren().addAll(vtacheliste, separatorBox);
                }
            }
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

        // On survol la VBox de la tâche à l'endroit où on l'a placé
        this.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getGestureSource().getClass().toString().contains("VueListe")) {
                    VueListe vl = (VueListe) dragEvent.getGestureSource();
                    VBox vb = (VBox) (vl).getChildren().get(0);
                    BorderPane bp = (BorderPane) (vb).getChildren().get(0);
                    Label l = (Label) (bp).getChildren().get(0);
                    String txt = l.getText();
                    idColonneADeplace = modele.recupererColonneLigneID(txt);
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                } else if (dragEvent.getGestureSource() != this && dragEvent.getDragboard().hasString()
                        && dragEvent.getGestureSource().getClass().toString().contains("VueTacheListe")) {
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                    VueTacheListe vtl = (VueTacheListe) dragEvent.getGestureSource();
                    VueListe vl = (VueListe) (vtl).getParent();
                    VBox vb2 = (VBox) (vl).getChildren().get(0);
                    BorderPane bp = (BorderPane) (vb2).getChildren().get(0);
                    Label l = (Label) (bp).getChildren().get(0);
                    String txtCol = l.getText();
                    idAncienneColonne = modele.recupererColonneLigneID(txtCol);
                } else if (dragEvent.getGestureSource().getClass().toString().contains("VBox")) {
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                    VBox vmere = (VBox) dragEvent.getGestureSource();
                    while (!(vmere.getParent().getClass().toString().contains("VueTacheListe"))) {
                        vmere = (VBox) vmere.getParent();
                    }
                    VueTacheListe vtl = (VueTacheListe) vmere.getParent();


                    VueListe vb = (VueListe) (vtl).getParent();
                    VBox vb2 = (VBox) (vb).getChildren().get(0);
                    BorderPane bp = (BorderPane) (vb2).getChildren().get(0);
                    Label l = (Label) (bp).getChildren().get(0);
                    String txtCol = l.getText();
                    // Récupère l'ancienne colonne de la sous tache
                    idAncienneColonne = modele.recupererColonneLigneID(txtCol);

                    // Récupère le texte marqué sur la tâche mère
                    HBox hb = (HBox) vtl.getChildren().get(0);
                    Label lT = (Label) hb.getChildren().get(0);
                    String txtTache = lT.getText();

                    // Récupère l'id de la tâche
                    ancienneTache = modele.recupererTache(idAncienneColonne, txtTache);
                }
                dragEvent.consume();
            }
        });


        // Action lorsqu'on dépose la tâche à un autre endroit
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                Dragboard db = dragEvent.getDragboard();
                boolean success = false;
                if (db.hasString() && dragEvent.getGestureSource().getClass().toString().contains("VueTacheListe")) {
                    // Récupère le conteneur de la tâche
                    VBox hdepla = (VBox) dragEvent.getGestureSource();
                    String texteTacheDepl = ((Label) ((HBox) hdepla.getChildren().get(0)).getChildren().get(0)).getText();
                    Tache tDepl = modele.getColonneLignes().get(idAncienneColonne).getTache(texteTacheDepl);
                    modele.supprimerTache(idAncienneColonne, tDepl);
                    modele.ajouterCompositeTache(idColonne, tDepl);
                } else if (dragEvent.getGestureSource().getClass().toString().contains("VueListe")) {
                    modele.echangerColonneLigne(idColonneADeplace, idColonne);
                } else if (dragEvent.getGestureSource().getClass().toString().contains("VBox")) {
                    // Récupère la sous tâche que l'on déplace dans une tâche
                    VBox vb = (VBox) dragEvent.getGestureSource();
                    Label l = (Label) vb.getChildren().get(0);
                    String txtSousTache = l.getText();
                    // Supprimer l'ancienne sous taches
                    Tache st = modele.supprimerSousTache(ancienneTache, txtSousTache);

                    // Ajouter la sous tâche à la colonne
                    modele.ajouterCompositeTache(idColonne, st);
                }
                dragEvent.setDropCompleted(success);
                dragEvent.consume();
            }
        });

        Button ajouterTacheBtn = new Button("+ Ajouter une tâche");
        ajouterTacheBtn.setOnAction(new ControleurAjoutTache(modele));

        this.getChildren().add(titreLabel);

        this.getChildren().add(ajouterTacheBtn);
        this.setId("" + this.idColonne);


        System.out.println("VueListe créée avec le titre: " + nom);
    }

    @Override
    public void actualiser(Sujet s) {
        creerListe();
    }
}