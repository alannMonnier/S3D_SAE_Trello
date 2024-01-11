package com.example.s3d_sae_trello;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Gestion affichage colonne
 */
public class ColonneFX extends VBox {

    /**
     * Declaration attributs
     */
    private ArrayList<Tache> taches;
    private ModeleMenu modele;
    private int idColonne;
    private String nom;
    private int idAncienneColonne;
    private int idColonneADeplace;
    private Tache ancienneTache;


    BorderStroke borderStroke = new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID,
            CornerRadii.EMPTY,
            BorderWidths.DEFAULT);

    private Border border = new Border(borderStroke);


    /**
     * Constructeur
     * @param taches taches de la colonne
     * @param m ModeleMenu
     * @param id id de la colonne
     * @param nom nom de la colonne
     */
    public ColonneFX(ArrayList<Tache> taches, ModeleMenu m, int id, String nom) {
        this.taches = taches;
        this.modele = m;
        this.idColonne = id;
        this.nom = nom;
        this.idAncienneColonne = -1;
        this.idColonneADeplace = -1;
        this.ancienneTache = null;

        // création header de la colonne
        VBox vTitreCol = new VBox();
        BorderPane bpHeader = new BorderPane();
        Label lTitreCol = new Label(this.nom);
        lTitreCol.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #8439FF;"); // Texte en violet
        MenuBar paramCol = new MenuBar();
        Menu menuParamCol = new Menu("...");
        MenuItem tUrgence = new MenuItem("Trier tâche par urgence");
        MenuItem tAlphabetique = new MenuItem("Trier tâche par ordre alphabétique");
        MenuItem tDate = new MenuItem("Trier tâche par date");
        MenuItem tsuppr = new MenuItem("Supprimer cette liste");
        MenuItem tarchiv = new MenuItem("Archiver les tâches de cette liste");
        menuParamCol.getItems().addAll(tUrgence, tAlphabetique, tDate, tsuppr, tarchiv);
        paramCol.getMenus().add(menuParamCol);
        paramCol.setStyle("-fx-background-color: transparent;"); // Fond du MenuBar transparent

        Line lineCol = new Line(0, 0, 200, 0);
        lineCol.setStroke(Color.web("#957DAD")); // Couleur de la ligne assortie au texte
        lineCol.setStrokeWidth(1.5);

        bpHeader.setLeft(lTitreCol);
        bpHeader.setRight(paramCol);
        bpHeader.setPadding(new Insets(5));
        bpHeader.setStyle("-fx-background-color: transparent; -fx-padding: 10; -fx-border-radius: 5;");


        vTitreCol.getChildren().addAll(bpHeader, lineCol);
        vTitreCol.setSpacing(6);


        tUrgence.setOnAction(e -> modele.trierColonneLigne(idColonne, "urgence"));
        tAlphabetique.setOnAction(e -> modele.trierColonneLigne(idColonne, "alphabetique"));
        tDate.setOnAction(e -> modele.trierColonneLigne(idColonne, "date"));
        tsuppr.setOnAction(e -> {
            try {
                modele.supprimerColonneLigne(idColonne);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        tarchiv.setOnAction(e -> {
            try {
                modele.archiverToutesTaches(idColonne);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        this.getChildren().addAll(vTitreCol);
        this.setMinWidth(250);
        this.setStyle("-fx-background-color: #F2F2F2;");


        // -------Création des taches ------------

        for (Tache ct : this.taches) {
            if (ct != null) {
                VueTache vbTache = new VueTache(ct, modele, id);
                this.getChildren().addAll(vbTache);
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
                if (dragEvent.getGestureSource().getClass().toString().contains("VueColonne")) {
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                    ColonneFX vc = (ColonneFX) dragEvent.getGestureSource();
                    idColonneADeplace = vc.idColonne;
                }

                else if (dragEvent.getGestureSource() != this && dragEvent.getDragboard().hasString()
                  && dragEvent.getGestureSource().getClass().toString().contains("VueTache")) {

                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                    VueTache vt = (VueTache) dragEvent.getGestureSource();
                    idAncienneColonne = vt.getTacheCourante().getIdcolonne();
                    ancienneTache = vt.getTacheCourante();
                }

                else if (dragEvent.getGestureSource().getClass().toString().contains("VBox")) {
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                    VBox vmere = (VBox) dragEvent.getGestureSource();
                    while (!(vmere.getParent().getClass().toString().contains("VueTache"))) {
                        vmere = (VBox) vmere.getParent();
                    }
                    VueTache vt = (VueTache) vmere.getParent();


                    ColonneFX vb = (ColonneFX) (vt).getParent();
                    VBox vb2 = (VBox) (vb).getChildren().get(0);
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
                if (db.hasString() && dragEvent.getGestureSource().getClass().toString().contains("VueTache")) {
                    modele.getColonneLignes().get(idAncienneColonne).getTacheList().remove(ancienneTache);
                    ancienneTache.setIdcolonne(id);
                    try {
                        modele.ajouterCompositeTache(ancienneTache);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (dragEvent.getGestureSource().getClass().toString().contains("VueColonne")) {
                    modele.echangerColonneLigne(idColonneADeplace, idColonne);
                }

                else if (dragEvent.getGestureSource().getClass().toString().contains("VBox")) {
                    // Récupère la sous tâche que l'on déplace dans une tâche
                    VBox vb = (VBox) dragEvent.getGestureSource();
                    Label l = (Label) vb.getChildren().get(0);
                    String txtSousTache = l.getText();
                    // Supprimer l'ancienne sous taches
                    Tache st = modele.supprimerSousTache(ancienneTache, txtSousTache);

                    // Ajouter la sous tâche à la colonne
                    try {
                        modele.ajouterCompositeTache(st);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                dragEvent.setDropCompleted(success);
                dragEvent.consume();
            }
        });


        // Ajouter une tâche
        Button laddtache = new Button("+ Ajouter une tâche");

// Style CSS pour le bouton
        String styleBouton = "-fx-background-color: #4CAF50; " +  // Couleur de fond verte
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 10 20; " +
                "-fx-font-size: 14px;";

        laddtache.setStyle(styleBouton);

// Effet de survol pour le bouton
        laddtache.setOnMouseEntered(e -> laddtache.setStyle(styleBouton + "-fx-background-color: #66BB6A;")); // Couleur légèrement plus claire au survol
        laddtache.setOnMouseExited(e -> laddtache.setStyle(styleBouton));

        laddtache.setOnAction(new ControleurAjoutTache(modele));

        this.getChildren().addAll(laddtache);
// finition d'un padding plus large
        this.setPadding(new Insets(10, 8, 10, 8)); // Haut, Droit, Bas, Gauche
        this.setSpacing(20);
        this.setAlignment(Pos.TOP_CENTER);
        this.setId("" + this.idColonne);

    }
}
