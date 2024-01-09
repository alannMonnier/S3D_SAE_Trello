package com.example.s3d_sae_trello;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

/**
 * Classe de test qui va tester l'ajout de sous tache
 */
class ModeleMenuTestAjouterSousTache {


    private ModeleMenu modeleMenu;
    private ColonneLigne colonneLigne1;
    private Tache tache1;
    private Tache sousTache1;
    private Tache sousTache2;

    @BeforeEach
    public void init(){
        modeleMenu = new ModeleMenu();
        colonneLigne1 = new ColonneLigne("ColonneLigne1", 1);
        tache1 = new Tache(1, "Tache 1", "Description 1", 2, 1, LocalDate.now());
        sousTache1 = new Tache(2, "Sous Tache 1", "Description 2", 2, 2, LocalDate.now());
        sousTache2 = new Tache(3, "Sous Tache 2", "Description 3", 2, 3, LocalDate.now());

        colonneLigne1.ajouterTache(tache1);

        modeleMenu.getColonneLignes().add(colonneLigne1);
    }

    @AfterEach
    public void after(){
        modeleMenu.getColonneLignes().clear();
    }

    /**
     * Valide l'ajout d'une sousTache dans une tache
     */
    @Test
    public void ajouterSousTache_Simple_OK() {
        modeleMenu.ajouterSousTache(0, 0, sousTache1);

        assertTrue(tache1.getSousTaches().contains(sousTache1));
    }

    /**
     * Valide l'ajout d'une sousTache dans une sous tache
     */
    @Test
    public void ajouterSousTache_DansSousTache_OK() {
        sousTache1.ajouterSousTache(sousTache2);
        modeleMenu.ajouterSousTache(0, 0, sousTache1);

        assertTrue(tache1.getSousTaches().contains(sousTache1));
        assertTrue(sousTache1.getSousTaches().contains(sousTache2));
    }
}