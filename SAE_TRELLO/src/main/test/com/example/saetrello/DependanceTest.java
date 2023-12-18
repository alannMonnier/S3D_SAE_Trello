package com.example.saetrello;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DependanceTest {

    CompositeTache t;
    CompositeTache tMere1 = new Tache(1, "Tache Mere 1", 2, 3);
    CompositeTache tMere2 = new Tache(2,"Tache Mere 2", 2, 4);
    CompositeTache tMere3 = new SousTache(3,"Tache Mere 3", 2, 5);
    CompositeTache tFille1 = new SousTache(4,"Tache Fille 1", 1, 3);
    CompositeTache tFille2 = new Tache(5,"Tache Fille 2", 2, 2);
    CompositeTache tFille3 = new Tache(6, "Tache Fille 3", 3, 6);

    @BeforeEach
    public void init(){
        // Création d'une tâche
        t = new Tache(7, "Tache 7", 2, 3);
        // Ajout des tâches mères
        t.ajouterDependanceMere(tMere1);
        t.ajouterDependanceMere(tMere2);
        t.ajouterDependanceMere(tMere3);
        // Ajout des tâches filles
        t.ajouterDependanceFille(tFille1);
        t.ajouterDependanceFille(tFille2);
        t.ajouterDependanceFille(tFille3);
    }


        /**
         * Test que les dépendances Mères se sont bien ajoutées
         */
    @org.junit.jupiter.api.Test
    void testAddDependanceMere_OK() {
        // Récupère les tâches mères ajoutées précédemment et on vérifie qu'elles sont toutes présentes
        // dans le bon ordre
        Dependance d = t.getDependance();
        ArrayList<CompositeTache> tachesMeres = d.getTachesMere();

        ArrayList<CompositeTache> tacheVerif = new ArrayList<>();
        tacheVerif.add(new SousTache(3,"Tache Mere 3", 2, 5));
        tacheVerif.add(new Tache(2, "Tache Mere 2", 2, 4));
        tacheVerif.add(new Tache(1, "Tache Mere 1", 2, 3));

        // Vérification
        for (int i=0; i< tacheVerif.size(); i++){
            String errorMessage = "Les tâches ou sous tâches " + tacheVerif.get(i).getNom() + " " + tachesMeres.get(i).getNom() + "devraient être identiques";
            assertEquals(tacheVerif.get(i), tachesMeres.get(i), errorMessage );
        }
    }

    /**
     * Test que les dépendances Filles se sont bien ajoutées
     */
    @org.junit.jupiter.api.Test
    void testAddDependanceFille_OK() {
        // Récupère les tâches filles ajoutées précédemment et on vérifie qu'elles sont toutes présentes
        // dans le bon ordre
        Dependance d = t.getDependance();
        ArrayList<CompositeTache> tachesFilles = d.getTachesFille();

        ArrayList<CompositeTache> tacheVerif = new ArrayList<>();
        tacheVerif.add(new Tache(6,"Tache Fille 3", 3, 6));
        tacheVerif.add(new Tache(5,"Tache Fille 2", 2, 2));
        tacheVerif.add(new SousTache(4,"Tache Fille 1", 1, 3));


        // Vérification
        for (int i=0; i< tacheVerif.size(); i++){
            String errorMessage = "Les tâches ou sous tâches " + tacheVerif.get(i).getNom() + " " + tachesFilles.get(i).getNom() + "devraient être identiques";
            assertEquals(tacheVerif.get(i), tachesFilles.get(i), errorMessage );
        }
    }
}