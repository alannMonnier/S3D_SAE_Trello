package com.example.saetrello;

public interface Sujet {

    public void ajouterObservateur(Observateur o);
    public void supprimerObservateur(Observateur o);
    public void notifierObservateurs();
}
