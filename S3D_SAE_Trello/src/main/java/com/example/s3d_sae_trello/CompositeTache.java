package com.example.s3d_sae_trello;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public abstract class CompositeTache {
    protected int id;
    private String nom;

    private String descript;
    private LocalDate date;
    private int degreUrgence;
    private int tempsEstime;
    private boolean tacheRealise;
    private LocalDate dateDebutReal;
    private Dependance dependance; // Contient dépendance Mere et Fille de la tâche



    public CompositeTache(int id, String n, int urgence, int tempsEstime) {
        this.id = id;
        this.nom = n;
        this.date = LocalDate.now();
        this.degreUrgence = urgence;
        this.tempsEstime = tempsEstime;
        this.tacheRealise = false;
        this.dateDebutReal = null;
        this.dependance = new Dependance();
        this.descript = "";
    }

    public CompositeTache(int id, String n, int urgence, int tempsEstime, String descr) {
        this.id = id;
        this.nom = n;
        this.date = LocalDate.now();
        this.degreUrgence = urgence;
        this.tempsEstime = tempsEstime;
        this.tacheRealise = false;
        this.dateDebutReal = null;
        this.dependance = new Dependance();
        this.descript = descr;
    }



    public abstract void ajouterSousTache(CompositeTache t);

    public abstract void retirerSousTache(int id);

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getDegreUrgence() {
        return degreUrgence;
    }

    public int getTempsEstime() {
        return tempsEstime;
    }

    public boolean getTacheRealise() {
        return tacheRealise;
    }

    public LocalDate getDateDebutReal() {
        return dateDebutReal;
    }

    public String getDescription(){ return descript; }


    /**
     * Récupère les dépendances de la classe
     */
    public Dependance getDependance() {
        return dependance;
    }

    /**
     * Ajoute une dépendance Mère à la tâche
     * @param t une tâche ou sous tâche
     */
    public void ajouterDependanceMere(CompositeTache t){
        dependance.addDependanceMere(t);
    }

    /**
     * Ajoute une dépendance Fille à la tâche
     * @param t une tâche ou sous tâche
     */
    public void ajouterDependanceFille(CompositeTache t){
        dependance.addDependanceFille(t);
    }

    /**
     * Affiche les dépendances mère et fille de la tâche
     * @return la chaine contenant les dépendances de la tâche
     */
    public String  afficherDependance(){
        return this.dependance.afficherDependance(this);
    }

    public String toString(){
        return "ID : "+this.id+" | Nom: "+this.nom+" | Date création: "+this.date+" | Degré d'urgence : "+this.degreUrgence+" | Temps estimé : "+this.tempsEstime+" | Réalisée : "+this.tacheRealise+"\n";
    }
}
