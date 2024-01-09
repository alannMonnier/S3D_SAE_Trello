package com.example.s3d_sae_trello;


public interface Sujet {


    /**
     * Ajoute observateur
     */
    public void ajouterObservateur(Observateur o);

    /**
     * Supprime observateur
     */
    public void supprimerObservateur(Observateur o);

    /**
     * Notifie observateurs
     */
    public void notifierObservateurs();
}
