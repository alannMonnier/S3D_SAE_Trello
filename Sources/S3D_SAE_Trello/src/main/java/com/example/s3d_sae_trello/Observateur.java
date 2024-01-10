package com.example.s3d_sae_trello;

import java.io.IOException;

/**
 * Interface Observateur
 */
public interface Observateur {

    /**
     * Actualise la partie graphique de l'observateur
     */
    public void actualiser(Sujet s) throws IOException;
}
