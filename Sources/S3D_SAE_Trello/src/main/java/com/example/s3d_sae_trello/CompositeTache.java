package com.example.s3d_sae_trello;

import java.time.LocalDate;
import java.util.Date;

public abstract class CompositeTache {
    protected int id;
    private String nom;
    private String nomUser;
    private String prenomUser;
    private String description;
    private LocalDate date;
    private int degreUrgence;
    private int tempsEstime;
    private boolean tacheRealise;
    private Date dateDebutReal;
    private Dependance dependance; // Contient dépendance Mere et Fille de la tâche


    public CompositeTache(int id, String nomTache, String nomUtilisateur, String prenomUtilisateur, int urgence, int tempsEstime, String description) {
        this.id = id;
        this.nom = nomTache;
        this.nomUser = nomUtilisateur;
        this.prenomUser = prenomUtilisateur;
        this.description = description;
        this.date = LocalDate.now();
        this.degreUrgence = urgence;
        this.tempsEstime = tempsEstime;
        this.tacheRealise = false;
        this.dateDebutReal = null;
        this.dependance = new Dependance();
    }

    public abstract boolean ajouterSousTache(CompositeTache t);

    public abstract boolean retirerSousTache(int id);

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

    public Date getDateDebutReal() {
        return dateDebutReal;
    }

    public String getNomUser() {
        return nomUser;
    }

    public String getPrenomUser() {
        return prenomUser;
    }

    public String getDescription() {
        return description;
    }

    public void ajouterDescription(String d){
        description += d;
    }

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Tache numero : ").append(id).append(", d'urgence : ").append(degreUrgence).append("\n");
        builder.append("Nom : ").append(nom).append("\n");
        builder.append("Cree par ").append(nomUser).append(" ").append(prenomUser).append(" le ").append(date).append("\n");
        builder.append("Description : ").append(description).append("\n");
        builder.append("Temps estime : ").append(tempsEstime).append("\n");
        builder.append("-----------------------------------\n");

        if (dependance != null) {
            builder.append(dependance.afficherDependance(this));
        }

        builder.append("\n");

        if (tacheRealise) {
            builder.append("\n~ Tache realisee ~ \n");
        } else {
            builder.append("\n~ Tache " + nom + " non realisee ~\n");
        }

        return builder.toString();
    }

}