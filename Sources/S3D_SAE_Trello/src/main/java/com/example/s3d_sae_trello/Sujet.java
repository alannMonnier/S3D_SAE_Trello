package com.example.s3d_sae_trello;

public interface Sujet {

    public void ajouterObservateur(Observateur o);
    public void supprimerObservateur(Observateur o);
    public void notifierObservateurs();
}
