package com.example.s3d_sae_trello;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe qui test la gestion de l'archive
 */
class ModeleMenuTestArchivage {

    private ModeleMenu modeleMenu;
    private ColonneLigne colonneLigne1;
    private ColonneLigne colonneLigne2;
    private ColonneLigne colonneLigne3;
    private Tache tache1;
    private Tache tache2;
    private Tache tache3;

    @BeforeEach
    public void init(){
        modeleMenu = new ModeleMenu();
        colonneLigne1 = new ColonneLigne("ColonneLigne1", 1);
        colonneLigne2 = new ColonneLigne("ColonneLigne2", 2);
        colonneLigne3 = new ColonneLigne("ColonneLigne3", 3);
        tache1 = new Tache(1, "Tache 1", "Description 1", 2, 1, LocalDate.now());
        tache2 = new Tache(2, "Tache 2", "Description 2", 2, 2, LocalDate.now());
        tache3 = new Tache(3, "Tache 3", "Description 3", 2, 3, LocalDate.now());

        colonneLigne1.ajouterTache(tache1);
        colonneLigne2.ajouterTache(tache2);
        colonneLigne2.ajouterTache(tache3);
        modeleMenu.getColonneLignes().add(colonneLigne1);
        modeleMenu.getColonneLignes().add(colonneLigne2);
        modeleMenu.getColonneLignes().add(colonneLigne3);
    }

    @AfterEach
    public void after(){
        modeleMenu.getColonneLignes().clear();
        modeleMenu.getArchive().getTacheList().clear();
    }

    /**
     * Méthode qui vérifie que toutes les taches d'une colonneLigne ont été archivé
     */
    @Test
    void archiverToutesTaches() {
        modeleMenu.archiverToutesTaches(1);

        ArrayList<Tache> tachesArchive = new ArrayList<>();
        tachesArchive.add(tache2);
        tachesArchive.add(tache3);

        // Test archivage de toutes les taches de la colonne 2 fonctionne
        int i = 0;
        for (Tache tt: modeleMenu.getArchive().getTacheList()){
            assertEquals(tt, tachesArchive.get(i), "Mauvaise tache récupéré");
            i++;
        }
    }

    @Test
    public void archiverTache() {
        modeleMenu.archiverTache(0, tache1);
        modeleMenu.archiverTache(1, tache3);

        ArrayList<Tache> tachesArchive = new ArrayList<>();
        tachesArchive.add(tache1);
        tachesArchive.add(tache3);

        // Test archivage de deux taches
        int i = 0;
        for (Tache tt: modeleMenu.getArchive().getTacheList()){
            assertEquals(tt, tachesArchive.get(i), "Mauvaise tache récupéré");
            i++;
        }
    }


    /**
     * Vérifie que la désarchivage fonctionne correctement
     */
    @Test
    public void desarchiverTache() {
        modeleMenu.archiverTache(0, tache1);
        modeleMenu.archiverTache(1, tache3);

        modeleMenu.desarchiverTache(tache1);
        modeleMenu.desarchiverTache(tache3);

        ArrayList<Tache> tachesDesarchive = new ArrayList<>();
        tachesDesarchive.add(tache1);
        tachesDesarchive.add(tache3);

        ColonneLigne cl1 = modeleMenu.getColonneLignes().get(0);

        assertTrue(cl1.getTacheList().contains(tachesDesarchive.get(0)));
        assertTrue(cl1.getTacheList().contains(tachesDesarchive.get(1)));
    }
}
