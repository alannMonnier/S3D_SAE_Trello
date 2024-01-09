package com.example.s3d_sae_trello;


import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class MainDependance {

    /**
     * Démonstration utilisation dépendance dans l'itération 1
     */
    public static void main(String[] args) throws IOException {
        /**
        // Création d'une tâche
        Tache tache = new Tache(7, "Tache 7", "Marie", "Helene", 3, 200, "description_tache7");

        // Création d'une tâcheMère
        Tache tacheMere = new Tache(5, "Tache 5", "Jill", "Marc", 1, 150, "description_tache5");

        // Création d'une tâche fille
        Tache tacheFille = new Tache(8, "Tache 8", "Marie", "Helene", 2, 400, "description_tache8");

        // Ajout de la dépendance Mère
        tache.ajouterDependanceMere(tacheMere);

        // Ajout de la dépendance Fille
        tache.ajouterDependanceFille(tacheFille);

        // Affiche les dépendances de la tache
        System.out.println(tache.afficherDependance());*/

        ModeleMenu modeleMenu = new ModeleMenu();

        modeleMenu.ajouterColonneLigne("Test", 1);

        // Ajouter des tâches
        Tache tache1 = new Tache(modeleMenu.getTacheCompositeNumId(), "Tache 1", "", 2, 1, LocalDate.now(), 0);
        modeleMenu.ajouterCompositeTache(tache1);
        Tache tache2 = new Tache(modeleMenu.getTacheCompositeNumId(), "Tache 2", "", 3, 2, LocalDate.now(), 0);
        modeleMenu.ajouterCompositeTache(tache2);
        Tache tache3 = new Tache(modeleMenu.getTacheCompositeNumId(), "Tache 3", "", 1, 3, LocalDate.now(), 0);
        modeleMenu.ajouterCompositeTache(tache3);
        Tache tache4 = new Tache(modeleMenu.getTacheCompositeNumId(), "Tache 4", "", 3, 1, LocalDate.now(), 0);
        modeleMenu.ajouterCompositeTache(tache4);
        Tache tache5 = new Tache(modeleMenu.getTacheCompositeNumId(), "Tache 5", "", 2, 2, LocalDate.now(), 0);
        modeleMenu.ajouterCompositeTache(tache5);

        ArrayList<Tache> meretache1 = new ArrayList<>();
        meretache1.add(tache2);
        meretache1.add(tache3);

        ArrayList<Tache> meretache2 = new ArrayList<>();
        meretache2.add(tache4);

        ArrayList<Tache> meretache3 = new ArrayList<>();
        meretache3.add(tache5);

        // Ajouter des dépendances
        modeleMenu.ajouterDependance(tache1, meretache1, "mere");
        modeleMenu.ajouterDependance(tache2, meretache2, "mere");
        modeleMenu.ajouterDependance(tache3, meretache3, "mere");

        /* Testez la méthode recupererPremieresMere
        for(Tache t : modeleMenu.recupererTachesSansMere()){
            System.out.println(t);

        for(Tache t : modeleMenu.getDependance().keySet()){
            for (Tache m : modeleMenu.getDependance().get(t))
            System.out.println(m);
        }*/

        DiagrammeGantt gantt = new DiagrammeGantt(modeleMenu);
        gantt.afficherGantt();
        gantt.parcourirMeres(tache1, 0);

    }
}
