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

public class CreationTacheFX extends Application {

    private ModeleMenu modele;
    private int idColonne;

    public CreationTacheFX(ModeleMenu m, int idColonne){
        this.modele = m;
        this.idColonne = idColonne;
    }

    @Override
    public void start(Stage stage) throws Exception {

        VBox vb = new VBox();

        GridPane gp = new GridPane();

        // Entrez nom tâche
        Label text = new Label("Entrez le nom correspondant à la tâche:");
        TextField tft = new TextField();

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
        cU.setToggleGroup(turg);

        vUrg.getChildren().addAll(cN, cU, cTU);



        // Entrez la description de la tâche
        Label td = new Label("Entrez la description de la tâche:");
        TextArea ta = new TextArea();


        // Ajouter le nom des utilisateurs
        VBox vlu = new VBox();
        Label lu = new Label("Ajoutez le nom de l'utilisateur:");
        HBox hlu = new HBox();
        Label nom = new Label("Nom");
        TextField tf_nom = new TextField();
        Label prenom = new Label("Prénom");
        TextField tf_prenom = new TextField();
        hlu.getChildren().addAll(nom, tf_nom, prenom, tf_prenom);
        vlu.getChildren().addAll(hlu);
        hlu.setSpacing(10);

        // Indiquer si la tâche est parente ou fille
        HBox hDep = new HBox();
        VBox v = new VBox();
        Label lDep = new Label("Indiquez la dépendance de la tâche crée:");
        ToggleGroup tg = new ToggleGroup();
        RadioButton c = new RadioButton("Parente");
        c.setToggleGroup(tg);
        RadioButton c1 = new RadioButton("Fille");
        c1.setToggleGroup(tg);
        RadioButton cNon = new RadioButton("Aucune dépendance");
        cNon.setToggleGroup(tg);
        cNon.setSelected(true);
        v.getChildren().addAll(lDep, c, c1, cNon);
        hDep.getChildren().addAll(v);



        // Bouton validé
        Button b = new Button("Valider");

        b.setOnAction(event -> {

            // Collect data from input fields
            String nomTache = tft.getText();
            int tempsTache = (int) spinner.getValue();
            String descriptionTache = ta.getText();
            //String nomUtilisateur = tf_nom.getText();
            //String prenomUtilisateur = tf_prenom.getText();

            // On récupère l'urgence
            RadioButton urgence = (RadioButton) turg.getSelectedToggle();
            String urgenceTache = urgence.getText();
            int urg = 1;
            if(urgenceTache.equals("Normal")){
                urg = 1;
            } else if (urgenceTache.equals("Urgent")) {
                urg = 2;
            }else{
                urg = 3;
            }

            // Get the selected dependency
            RadioButton dependance = (RadioButton) tg.getSelectedToggle();
            String dependencyType = dependance.getText();

            //compotache = new Tache(0, nomTache, urg, tempsTache, descriptionTache);
            modele.ajouterCompositeTache(this.idColonne, new Tache(modele.getTacheCompositeNumId(), nomTache, urg, tempsTache, descriptionTache));
            // On ferme la page
            stage.close();
        });

        gp.add(text, 0, 0);
        gp.add(tft, 1, 0);
        gp.add(temps, 0, 2);
        gp.add(spinner, 1, 2);
        gp.add(td, 0, 3);
        gp.add(ta, 1, 3);
        gp.add(lu, 0, 4);
        gp.add(vlu, 1, 4);
        gp.add(hDep, 1, 5);
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


        Scene scene = new Scene(vb, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void lancerApp() throws Exception {
        this.start(new Stage());
        //return this.compotache;
    }
}
