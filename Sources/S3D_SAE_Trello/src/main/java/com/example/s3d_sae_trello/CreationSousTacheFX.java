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

import java.time.LocalDate;

/**
 * Création de la SousTache en javaFX
 */
public class CreationSousTacheFX extends Application {

    /**
     * Declaration des attributs
     */
    private ModeleMenu modele;
    private int idColonne;
    private int id_tache_mere;

    /**
     * Constructeur
     * @param m ModeleMenu
     * @param idColonne id de la colonne
     * @param id_tache_mere id de la tache mere
     */
    public CreationSousTacheFX(ModeleMenu m, int idColonne, int id_tache_mere) {
        this.modele = m;
        this.idColonne = idColonne;
        this.id_tache_mere = id_tache_mere;
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

            // Collect data from input fields
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

            // Création de la tâche
            Tache tache = new Tache(idTache, nomTache, descriptionTache, urg, tempsTache, dateDebutReal);

            modele.ajouterSousTache(this.idColonne, id_tache_mere, tache);

            // On ferme la page
            stage.close();
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
