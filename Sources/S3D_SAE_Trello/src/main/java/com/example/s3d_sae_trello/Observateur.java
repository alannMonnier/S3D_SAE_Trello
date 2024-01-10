package com.example.s3d_sae_trello;

/**
 * Interface Observateur
 */
public interface Observateur {

    /**
     * Actualise la partie graphique de l'observateur
     */
    public void actualiser(Sujet s);
}
