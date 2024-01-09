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
public class VueColonne extends VBox implements Observateur {

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
    public VueColonne(ArrayList<Tache> taches, ModeleMenu m, int id, String nom) {
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
        MenuBar paramCol = new MenuBar();
        Menu menuParamCol = new Menu("...");
        MenuItem tUrgence = new MenuItem("Trier tâche par urgence");
        MenuItem tAlphabetique = new MenuItem("Trier tâche par ordre alphabétique");
        MenuItem tDate = new MenuItem("Trier tâche par date");
        MenuItem tsuppr = new MenuItem("Supprimer cette liste");
        MenuItem tarchiv = new MenuItem("Archiver les tâches de cette liste");
        menuParamCol.getItems().addAll(tUrgence, tAlphabetique, tDate, tsuppr, tarchiv);
        paramCol.getMenus().add(menuParamCol);
        Line lineCol = new Line(0, 0, 200, 0);

        bpHeader.setLeft(lTitreCol);
        bpHeader.setRight(paramCol);
        bpHeader.setPadding(new Insets(5));
        vTitreCol.getChildren().addAll(bpHeader, lineCol);
        vTitreCol.setSpacing(6);


        tUrgence.setOnAction(e -> modele.trierColonneLigne(idColonne, "urgence"));
        tAlphabetique.setOnAction(e -> modele.trierColonneLigne(idColonne, "alphabetique"));
        tDate.setOnAction(e -> modele.trierColonneLigne(idColonne, "date"));
        tsuppr.setOnAction(e -> modele.supprimerColonneLigne(idColonne));
        tarchiv.setOnAction(e -> modele.archiverToutesTaches(idColonne));

        this.getChildren().addAll(vTitreCol);

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
                    VueColonne vc = (VueColonne) dragEvent.getGestureSource();
                    VBox vb = (VBox) (vc).getChildren().get(0);
                    BorderPane bp = (BorderPane) (vb).getChildren().get(0);
                    Label l = (Label) (bp).getChildren().get(0);
                    String txt = l.getText();
                    idColonneADeplace = modele.recupererColonneLigneID(txt);
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                } else if (dragEvent.getGestureSource() != this && dragEvent.getDragboard().hasString()
                        && dragEvent.getGestureSource().getClass().toString().contains("VueTache")) {
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                    VueTache vt = (VueTache) dragEvent.getGestureSource();
                    VueColonne vb = (VueColonne) (vt).getParent();
                    VBox vb2 = (VBox) (vb).getChildren().get(0);
                    BorderPane bp = (BorderPane) (vb2).getChildren().get(0);
                    Label l = (Label) (bp).getChildren().get(0);
                    String txtCol = l.getText();
                    idAncienneColonne = modele.recupererColonneLigneID(txtCol);
                } else if (dragEvent.getGestureSource().getClass().toString().contains("VBox")) {
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                    VBox vmere = (VBox) dragEvent.getGestureSource();
                    while (!(vmere.getParent().getClass().toString().contains("VueTache"))) {
                        vmere = (VBox) vmere.getParent();
                    }
                    VueTache vt = (VueTache) vmere.getParent();


                    VueColonne vb = (VueColonne) (vt).getParent();
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
                    // Récupère le conteneur de la tâche
                    VBox hdepla = (VBox) dragEvent.getGestureSource();
                    String texteTacheDepl = ((Label) ((HBox) hdepla.getChildren().get(0)).getChildren().get(0)).getText();
                    Tache tDepl = modele.getColonneLignes().get(idAncienneColonne).getTache(texteTacheDepl);
                    modele.supprimerTache(idAncienneColonne, tDepl);
                    try {
                        modele.ajouterCompositeTache(tDepl);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (dragEvent.getGestureSource().getClass().toString().contains("VueColonne")) {
                    modele.echangerColonneLigne(idColonneADeplace, idColonne);
                } else if (dragEvent.getGestureSource().getClass().toString().contains("VBox")) {
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
