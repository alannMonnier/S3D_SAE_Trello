package com.example.s3d_sae_trello;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe qui teste les différents déplacements
 */
class ModeleMenuTestDeplacements {

    private ModeleMenu modeleMenu;
    private ColonneLigne colonneLigne1;
    private ColonneLigne colonneLigne2;
    private ColonneLigne colonneLigne3;
    private Tache tache1;
    private Tache tache2;

    @BeforeEach
    public void init(){
        modeleMenu = new ModeleMenu();
        colonneLigne1 = new ColonneLigne("ColonneLigne1", 1);
        colonneLigne2 = new ColonneLigne("ColonneLigne2", 2);
        colonneLigne3 = new ColonneLigne("ColonneLigne3", 3);
        tache1 = new Tache(1, "Tache 1", "Description 1", 2, 1, LocalDate.now());
        tache2 = new Tache(2, "Tache 2", "Description 2", 2, 2, LocalDate.now());

        colonneLigne1.ajouterTache(tache1);
        colonneLigne2.ajouterTache(tache2);
        modeleMenu.getColonneLignes().add(colonneLigne1);
        modeleMenu.getColonneLignes().add(colonneLigne2);
        modeleMenu.getColonneLignes().add(colonneLigne3);
    }

    @AfterEach
    public void after(){
        modeleMenu.getColonneLignes().clear();
    }

    /**
     * Méthode qui test le déplacement d'une tâche d'une colonneLigne à une autre
     */
    @Test
    public void deplacerCompositeTache() {
        modeleMenu.deplacerCompositeTache(0, 2, tache1);

        ColonneLigne cl = modeleMenu.getColonneLignes().get(2);
        assertTrue(cl.tachelist.contains(tache1));

        cl = modeleMenu.getColonneLignes().get(0);
        assertFalse(cl.tachelist.contains(tache1));
    }

    /**
     * Méthode qui test que l'échange de deux colonnesLigne fonctionne
     */
    @Test
    public void echangerColonneLigne() {
        modeleMenu.echangerColonneLigne(0, 2);

        ArrayList<ColonneLigne> lcl = new ArrayList<>();
        lcl.add(colonneLigne3);
        lcl.add(colonneLigne1);

        // Vérification que les colonnes ont été échangées
        int i = 0;
        for (ColonneLigne cl : lcl){
            assertEquals(cl, lcl.get(i), "Ce n'est pas la bonne colonneLigne attendu");
            i++;
        }
    }
}