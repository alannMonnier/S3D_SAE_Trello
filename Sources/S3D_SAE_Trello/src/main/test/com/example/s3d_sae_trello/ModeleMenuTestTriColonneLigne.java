package com.example.s3d_sae_trello;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe qui teste les différents moyens de trier une liste
 */
class ModeleMenuTestTriColonneLigne {

    private ModeleMenu modeleMenu;
    private ColonneLigne colonneLigne1;
    private Tache tache1;
    private Tache tache2;
    private Tache tache3;

    @BeforeEach
    public void init(){
        modeleMenu = new ModeleMenu();
        colonneLigne1 = new ColonneLigne("ColonneLigne1", 1);

        tache1 = new Tache(1, "Tache Z", "Description 1", 3, 1, LocalDate.of(1008, 2, 10));
        tache2 = new Tache(2, "Tache A", "Description 2", 2, 2, LocalDate.now());
        tache3 = new Tache(3, "Tache B", "Description 3", 1, 3, LocalDate.of(2000, 5, 20));

        colonneLigne1.ajouterTache(tache1);
        colonneLigne1.ajouterTache(tache2);
        colonneLigne1.ajouterTache(tache3);

        modeleMenu.getColonneLignes().add(colonneLigne1);

    }

    @AfterEach
    public void after(){
        modeleMenu.getColonneLignes().clear();
    }

    /**
     * Valide le tri par date
     */
    @Test
    void trierColonneLigneParDate_OK() {
        modeleMenu.trierColonneLigne(0, "date");

        ArrayList<Tache> tachesVerif = new ArrayList<>();
        tachesVerif.add(tache2);
        tachesVerif.add(tache3);
        tachesVerif.add(tache1);



        int i = 0;
        for (Tache tt : modeleMenu.getColonneLignes().get(0).getTacheList()){
            assertEquals(tt, tachesVerif.get(i), "Pas la bonne tache attendu");
            i++;
        }
    }

    /**
     * Valide tri par urgence
     */
    @Test
    public void trierColonneLigneParUrgence_OK() {

        modeleMenu.trierColonneLigne(0, "urgence");

        ArrayList<Tache> tachesVerif = new ArrayList<>();
        tachesVerif.add(tache1);
        tachesVerif.add(tache2);
        tachesVerif.add(tache3);


        int i = 0;
        for (Tache tt : modeleMenu.getColonneLignes().get(0).getTacheList()){
            assertEquals(tt, tachesVerif.get(i), "Pas la bonne tache attendu");
            i++;
        }
    }

    /**
     * Valide le tri par ordre alphabetique
     */
    @Test
    public void trierColonneLigneOrdreAlphabetique_OK() {
        modeleMenu.trierColonneLigne(0, "alphabetique");

        ArrayList<Tache> tachesVerif = new ArrayList<>();
        tachesVerif.add(tache2);
        tachesVerif.add(tache3);
        tachesVerif.add(tache1);


        int i = 0;
        for (Tache tt : modeleMenu.getColonneLignes().get(0).getTacheList()){
            assertEquals(tt, tachesVerif.get(i), "Pas la bonne tache attendu");
            i++;
        }
    }
}