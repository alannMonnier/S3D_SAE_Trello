package com.example.s3d_sae_trello;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Création de la tache en JavaFX
 */
public class CreationTacheFX extends Application {

    /**
     * Declaration attributs
     */
    private ModeleMenu modele;
    private int idColonne;

    //Utile pour l'ajout de sous tache à une tâche en la créant directement
    private Tache tache;

    /**
     * Constructeur
     * @param m ModeleMenu
     * @param idColonne id de la colonne
     */
    public CreationTacheFX(ModeleMenu m, int idColonne) {
        this.modele = m;
        this.idColonne = idColonne;
    }

    /**
     * Second Constructeur pour la creation de la sousTache
     * @param m ModeleMenu
     * @param idColonne id de la colonne
     * @param t tache mere
     */
    public CreationTacheFX(ModeleMenu m, int idColonne, Tache t) {
        this.modele = m;
        this.idColonne = idColonne;
        this.tache = t;
    }

    /**
     * Lancement de la partie graphique
     */
    @Override
    public void start(Stage stage) throws Exception {

        VBox vb = new VBox();
        GridPane gp = new GridPane();

        // Entrez nom tâche
        Label text = new Label("Entrez le nom correspondant à la tâche:");
        TextField tft = new TextField("Tache " + modele.getTacheCompositeNumId());

        // Entrez temps consacré à la tâche
        Label temps = new Label("Entrez le temps consacré à la tâche:");
        Spinner spinner = new Spinner<Integer>();
        SpinnerValueFactory svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 72, 1);
        spinner.setValueFactory(svf);


        // Entrez l'urgence de la tâche (Niveau 1, 2, 3)
        Label lUrg = new Label("Urgence de la tâche: ");
        VBox vUrg = new VBox();
        ToggleGroup turg = new ToggleGroup();
        RadioButton cN = new RadioButton("Normal");
        cN.setToggleGroup(turg);
        cN.setSelected(true);
        RadioButton cU = new RadioButton("Urgent");
        cU.setToggleGroup(turg);
        RadioButton cTU = new RadioButton("Très Urgent");
        cTU.setToggleGroup(turg);

        vUrg.getChildren().addAll(cN, cU, cTU);

        // Ajouter champ pour la date ou l'on doit réaliser la tâche
        Label tempsReal = new Label("Entrez la date à laquelle vous voulez commencer la tâche");
        DatePicker dp = new DatePicker(LocalDate.now());
        HBox hheure = new HBox();
        Label lHeure = new Label("Entrer l'heure: ");
        Spinner spinnerHeure = new Spinner<Integer>();
        SpinnerValueFactory svfh = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 1);
        spinnerHeure.setValueFactory(svfh);
        hheure.getChildren().addAll(lHeure, spinnerHeure);


        // Entrez la description de la tâche
        Label td = new Label("Entrez la description de la tâche:");
        TextArea ta = new TextArea("Description " + this.modele.getTacheCompositeNumId());

        // Bouton validé
        Button b = new Button("Valider");

        b.setOnAction(event -> {

            int idTache = modele.getTacheCompositeNumId();
            String nomTache = tft.getText();
            String descriptionTache = ta.getText();
            // On récupère l'urgence
            RadioButton urgence = (RadioButton) turg.getSelectedToggle();
            String urgenceTache = urgence.getText();
            int urg = 1;
            if (urgenceTache.equals("Normal")) {
                urg = 1;
            } else if (urgenceTache.equals("Urgent")) {
                urg = 2;
            } else {
                urg = 3;
            }
            int tempsTache = (int) spinner.getValue();
            LocalDate dateDebutReal = dp.getValue();

            boolean ajoutReussi = true;

        // Création d'une tâche normale si c'est depuis une colonne
            if(this.tache == null) {
                Tache tache = new Tache(idTache, nomTache, descriptionTache, urg, tempsTache, dateDebutReal, this.idColonne);
                try {
                    modele.ajouterCompositeTache(tache);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {

                // Création d'une sous-tâche si la date est valide
                SousTache st = new SousTache(idTache, nomTache, descriptionTache, urg, tempsTache, dateDebutReal, tache.getIdSousTache());
                // Vérification du nom
                if(!tache.nomSousTacheExiste(st)){
                    // Vérification de la date avant la création de la sous-tâche
                    if (!st.getDateDebutReal().isAfter(tache.getDateDebutReal())) {
                        if(tache.ajout_sous_tache_possible(st)){
                            tache.ajouterSousTache(st);
                            modele.setTacheCompositeNumId();
                            modele.notifierObservateurs();
                        } else{
                            Alert alert = Alerte.alerteDate("Erreur", "Le temps nécessaire à la réalisation de toutes les sous tâches est superieur à la tâche " + "\"" + tache.getNom() + "\"");
                            alert.showAndWait();
                            ajoutReussi = false;
                        }

                    } else {
                        // Affichage du message d'erreur si la date est invalide
                        Alert alert = Alerte.alerteDate("Erreur", "La date de début de la sous-tâche doit être antérieure à la date de début de la tâche " + "\"" + tache.getNom() + "\"");
                        alert.showAndWait();
                        ajoutReussi = false;
                    }
                } else{
                    // Affichage du message d'erreur s'il existe deja un nom identique
                    Alert alert = Alerte.alerteDate("Erreur", "Ce nom est déjà associé à une tâche.");
                    alert.showAndWait();
                    ajoutReussi = false;
                }
            }


            // Fermer la fenêtre seulement si l'ajout est réussi
            if (ajoutReussi) {
                stage.close();
            }
        });

        gp.add(text, 0, 0);
        gp.add(tft, 1, 0);
        gp.add(temps, 0, 2);
        gp.add(spinner, 1, 2);
        gp.add(td, 0, 3);
        gp.add(ta, 1, 3);
        gp.add(tempsReal, 0, 4);
        gp.add(dp, 1, 4);
        gp.add(hheure, 2, 4);

        gp.add(lUrg, 0, 6);
        gp.add(vUrg, 1, 6);
        gp.add(b, 1, 8);


        gp.setPadding(new Insets(10));
        gp.setHgap(10);
        gp.setVgap(10);


        HBox htitle = new HBox();
        Text title = new Text("Création d'une tâche");
        htitle.getChildren().addAll(title);
        htitle.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(htitle, gp);


        Scene scene = new Scene(vb, 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void lancerApp() throws Exception {
        this.start(new Stage());
    }
}
