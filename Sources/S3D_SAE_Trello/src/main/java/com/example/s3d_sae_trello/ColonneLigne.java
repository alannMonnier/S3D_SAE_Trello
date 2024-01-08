package com.example.s3d_sae_trello;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ColonneLigne {

    public List<Tache> tachelist;
    public int numero;
    public String nom;

    public ColonneLigne(String s, int i) {
        this.numero = i;
        this.nom = s;
        this.tachelist = new ArrayList<Tache>();
    }

<<<<<<< HEAD
    public void ajouterTache(Tache t){
        this.tachelist.add(t);
    }

    public void supprimerTache(Tache t){
=======
    public void ajouterTache(CompositeTache t) {
        this.tachelist.add(t);
    }

    public void supprimerTache(CompositeTache t) {
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b
        this.tachelist.remove(t);
    }

    public void trierDate() {

        Collections.sort(tachelist, new Comparator<Tache>() {
            @Override
<<<<<<< HEAD
            public int compare(Tache t1, Tache t2) {
                if(t1.getDateCreation().equals(t2.getDateCreation())){
=======
            public int compare(CompositeTache t1, CompositeTache t2) {
                if (t1.getDate().equals(t2.getDate())) {
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b
                    return 0;
                }
                return t1.getDateCreation().isAfter(t2.getDateCreation()) ? -1 : 1;
                //return t1.getDate().compareTo(t2.getDate());
            }
        });
    }

    public void trierUrgence() {

        Collections.sort(tachelist, new Comparator<Tache>() {
            @Override
<<<<<<< HEAD
            public int compare(Tache t1, Tache t2) {
                if(t1.getDegreUrgence() > t2.getDegreUrgence()){
=======
            public int compare(CompositeTache t1, CompositeTache t2) {
                if (t1.getDegreUrgence() > t2.getDegreUrgence()) {
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b
                    return -1;
                } else if (t1.getDegreUrgence() < t2.getDegreUrgence()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    public void trierAlphabetique() {

        Collections.sort(tachelist, new Comparator<Tache>() {
            @Override
            public int compare(Tache t1, Tache t2) {
                return t1.getNom().compareTo(t2.getNom());
            }
        });
    }

<<<<<<< HEAD
    public Tache trouverTache(int i){
        for(Tache t : this.tachelist){
            if(t.getId() == i){
=======
    public CompositeTache trouverTache(int i) {
        for (CompositeTache t : this.tachelist) {
            if (t.getId() == i) {
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b
                return t;
            }
        }
        //La tâche recherchée n'est pas présente dans cette ColonneLigne
        return null;
    }

<<<<<<< HEAD
    public ArrayList<Tache> getTacheList(){
        return (ArrayList<Tache>) tachelist;
    }

    public Tache getTache(String nomTache){
        for (Tache t : this.tachelist){
            if(t.getNom().equals(nomTache)){
                return t;
            }
        }
        return null;
=======
    public ArrayList<CompositeTache> getTacheList() {
        return (ArrayList<CompositeTache>) tachelist;
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b
    }

    public int getNumero() {
        return numero;
    }

    public void setNom(String s) {
        this.nom = s;
    }

    public String getNom() {
        return nom;
    }

    public Tache getCompositeTache(int idCompositeTache){
        for (Tache ct : this.tachelist){
            if(ct.getId() == idCompositeTache){
                return ct;
            }
        }
        return null;
    }

    public String toString(){
        String res = "La liste est composée des tâches suivantes:\n";
        for(Tache t : this.tachelist){
            res += t.toString();
        }
        return res;
    }
}
