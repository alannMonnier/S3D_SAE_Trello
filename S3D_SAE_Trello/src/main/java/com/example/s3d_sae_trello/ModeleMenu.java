package com.example.s3d_sae_trello;


import java.util.ArrayList;

public class ModeleMenu implements Sujet {

    /**
     * Declarations des attributs
     */
    private ArrayList<Observateur> observateurs; // Vues
    private ArrayList<ColonneLigne> colonneLignes; // tableau de tacheComposite
    private Archive archive; // Archive
    private DiagrammeGantt gantt;
    private int nbColonnes; // Nombre de colonnes crée
    private int tacheCompositeNumId; // Numéro de la tâcheComposite


    /**
     * Constructeur de modèle initialise les attributs
     */
    public ModeleMenu(){
        nbColonnes = 0;
        tacheCompositeNumId = 0;
        gantt = new DiagrammeGantt("", 0);
        observateurs = new ArrayList<>();
        colonneLignes = new ArrayList<>();
    }

    /**
     * Ajoute d'une nouvelle colonneLigne
     * @param nom de la nouvelle colonneLigne
     */
    public void ajouterColonneLigne(String nom){
        //this.colonneLignes.add(new ColonneLigne(nom, nbColonnes+1));
        this.notifierObservateurs();
    }

    /**
     * Ajoute une nouvelle tache ou sous tache dans la colonneLigne récupéré
     * @param idColonneLigne indice dans la liste
     * @param t tache ou sous tache
     */
    public void ajouterCompositeTache(int idColonneLigne, CompositeTache t){
        //this.colonneLignes.get(idColonneLigne).ajouterTache(t);
        this.notifierObservateurs();
    }

    /**
     * Déplace une tache ou sous tache
     * @param idColonneLigne index colonneLigne courante
     * @param idNewColonneLigne index nouvelle colonneLigne
     * @param t tache ou sous tache
     */
    public void deplacerCompositeTache(int idColonneLigne, int idNewColonneLigne, CompositeTache t){
        // Supprime la tâche présente dans la colonneLigne actuel
        this.colonneLignes.get(idColonneLigne).getTacheList().remove(t);
        // Ajoute la tâche dans la nouvelle colonneLigne
        //this.colonneLignes.get(idNewColonneLigne).ajouterTache(t);

        this.notifierObservateurs();
    }

    /**
     * Methode qui trie la colonneLigne en fonction d'un paramètre donnée
     * @param idColonneLigne index colonneLigne à trier
     * @param typeTri type de tri à utiliser
     */
    public void trierColonneLigne(int idColonneLigne, String typeTri){
        ColonneLigne cl = this.colonneLignes.get(idColonneLigne);
        switch (typeTri){
            case "date":
                cl.trierDate();
                break;
            case "urgence":
                cl.trierUrgence();
                break;
            case "alphabetique":
                cl.trierAlphabetique();
                break;
            default:
                try {
                    throw new Exception("Erreur");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
        this.notifierObservateurs();
    }

    /**
     * MVC
     */
    public void afficherColonne(){

    }
    /**
     * MVC
     */
    public void afficherListe(){

    }
    /**
     * MVC
     */
    public void afficherGantt(){

    }

    public void archiverToutesTaches(int idColonneLigne){
        for(Tache t : this.colonneLignes.get(idColonneLigne).getTacheList()){
            archive.ajouterTache(t);
            this.colonneLignes.get(idColonneLigne).supprimerTache(t);
        }
    }

    public void archiverTache(int idColonneLigne, int idTache){
        Tache t = this.colonneLignes.get(idColonneLigne).trouverTache(idTache);
        archive.ajouterTache(t);
        this.colonneLignes.get(idColonneLigne).supprimerTache(t);
    }

    public void supprimerTache(int idColonneLigne, int idTache){
        Tache t = this.colonneLignes.get(idColonneLigne).trouverTache(idTache);
        this.colonneLignes.get(idColonneLigne).supprimerTache(t);
    }

    public void supprimerColonneLigne(int idColonneLigne){
        this.colonneLignes.remove(this.colonneLignes.get(idColonneLigne));
    }

    /**
     * Ajoute un nouvel observateur
     * @param o observateur
     */
    @Override
    public void ajouterObservateur(Observateur o) {
        this.observateurs.add(o);
    }

    /**
     * Supprime un observateur
     * @param o observateur
     */
    @Override
    public void supprimerObservateur(Observateur o) {
        this.observateurs.remove(o);
    }

    /**
     * Notifie les observateurs qu'il faut actualiser l'affichage
     */
    @Override
    public void notifierObservateurs() {
        for (Observateur o: this.observateurs){
            o.actualiser();
        }
    }
}
