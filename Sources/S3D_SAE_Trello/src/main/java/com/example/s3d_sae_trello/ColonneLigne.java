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


    public void ajouterTache(Tache t){
        this.tachelist.add(t);
    }

    public void supprimerTache(Tache t){
        this.tachelist.remove(t);
    }

    public void trierDate() {

        Collections.sort(tachelist, new Comparator<Tache>() {
            @Override
            public int compare(Tache t1, Tache t2) {
                if(t1.getDateCreation().equals(t2.getDateCreation())){
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

            public int compare(Tache t1, Tache t2) {
                if(t1.getDegreUrgence() > t2.getDegreUrgence()){

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
