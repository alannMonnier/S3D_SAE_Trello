package com.example.s3d_sae_trello;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class ModeleMenuTestAjoutDependance {


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
     * Valide l'ajout des dépendances
     */
    @Test
    public void ajouterDependanceMere_OK() {
        ArrayList<Tache> tacheMere = new ArrayList<>();
        tacheMere.add(tache2);
        tacheMere.add(tache3);

        modeleMenu.ajouterDependance(tache1, tacheMere, "mere");

        ArrayList<Tache> tacheVerif = new ArrayList<>();
        tacheVerif.add(tache3);
        tacheVerif.add(tache2);

        int i = 0;
        for (Tache tMere : modeleMenu.getDependance().keySet()){
            assertEquals(tMere, tacheVerif.get(i));
            assertTrue(modeleMenu.getDependance().get(tMere).contains(tache1));
            i++;
        }
    }
}