package com.example.s3d_sae_trello;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;


public class Tache implements Comparable<Tache>, Serializable {

    private int id;
    private String nom;
    private String description;
    private LocalDate dateCreation;
    private int degreUrgence;
    private int tempsEstime;
    private boolean tacheRealise;
    private LocalDate dateDebutReal;
    private boolean sousTacheComplete;
    private ArrayList<Tache> sousTaches;
    private int idSousTache;
    private boolean afficherSousTache;
    private boolean estSelectionne;


    public Tache(int id, String nom, String description, int degreUrgence, int tempsEstime, LocalDate dateDebutReal){
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.dateCreation = LocalDate.now();
        this.degreUrgence = degreUrgence;
        this.tempsEstime = tempsEstime;
        this.tacheRealise = false;
        this.dateDebutReal = dateDebutReal;
        this.sousTacheComplete = true;
        this.sousTaches = new ArrayList<>();
        this.idSousTache = 0;
        this.afficherSousTache = false;
        this.estSelectionne = false;
    }


    public void ajouterSousTache(Tache st){
        this.idSousTache++;
        this.sousTaches.add(st);

    }

    public void retirerSousTache(Tache t){
        this.sousTaches.remove(t);
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
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


    public int getIdSousTache() {
        return idSousTache;
    }

    public String getDescription() {
        return description;
    }



    public ArrayList<Tache> getSousTaches() {
        return sousTaches;
    }

    public boolean isAfficherSousTache() {
        return afficherSousTache;
    }

    public boolean isEstSelectionne() {
        return estSelectionne;
    }

    public void setAfficherSousTache(boolean afficherSousTache) {
        this.afficherSousTache = afficherSousTache;
    }

    public void setEstSelectionne(boolean estSelectionne) {
        this.estSelectionne = estSelectionne;
    }



    @Override

    public int compareTo(Tache o) {
        if(this.getDateDebutReal().isBefore(o.dateDebutReal)){
            return -1;
        }
        else if (this.getDateDebutReal().isAfter(o.dateDebutReal)){
            return 1;
        }
        else if(this.getDateDebutReal().equals(o.dateDebutReal)){
            if(this.getId()< o.id){
                return -1;
            }
            else if(this.getId() > o.id){
                return 1;
            }
        }
        return 0;
    }



    @Override
    public boolean equals(Object obj) {
        if(this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) return false;
        Tache t = (Tache) obj;
        return id == t.id && Objects.equals(nom, t.nom) && Objects.equals(description, t.description)
                && Objects.equals(dateCreation, t.dateCreation) && degreUrgence == t.degreUrgence
                && tempsEstime == t.tempsEstime && tacheRealise == t.tacheRealise
                && Objects.equals(dateDebutReal, t.dateDebutReal) && sousTacheComplete == t.sousTacheComplete
                && Objects.equals(sousTaches, t.sousTaches) && idSousTache == t.idSousTache
                && afficherSousTache == t.afficherSousTache && estSelectionne == t.estSelectionne;
    }

    public boolean isTacheRealise() {
        return tacheRealise;
    }

    public boolean isSousTacheComplete() {
        return sousTacheComplete;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, description, dateCreation, degreUrgence, tempsEstime, tacheRealise,
                dateDebutReal, sousTacheComplete, sousTaches, idSousTache, afficherSousTache, estSelectionne);
    }

}
