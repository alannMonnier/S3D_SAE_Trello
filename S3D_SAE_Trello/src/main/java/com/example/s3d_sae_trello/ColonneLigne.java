package com.example.s3d_sae_trello;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ColonneLigne {

    public List<Tache> tachelist;
    public int numero;
    public String nom;

    public ColonneLigne(String s, int i){
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
                if(t1.getDate().equals(t2.getDate())){
                    return 0;
                }
                return t1.getDate().isAfter(t2.getDate()) ? -1 : 1;
                //return t1.getDate().compareTo(t2.getDate());
            }
        });
    }

    public void trierUrgence(){

        Collections.sort(tachelist, new Comparator<Tache>() {
            @Override
            public int compare(Tache t1, Tache t2) {
                if(t1.getDegreUrgence() > t2.getDegreUrgence()){
                    return -1;
                }else if(t1.getDegreUrgence() < t2.getDegreUrgence()){
                    return 1;
                }
                return 0;
            }
        });
    }

    public void trierAlphabetique(){

        Collections.sort(tachelist, new Comparator<Tache>() {
            @Override
            public int compare(Tache t1, Tache t2) {
                return t1.getNom().compareTo(t2.getNom());
            }
        });
    }

    public Tache trouverTache(int i){
        for(Tache t : this.tachelist){
            if(t.getId() == i){
                return t;
            }
        }
        //La tâche recherchée n'est pas présente dans cette ColonneLigne
        return null;
    }

    public List<Tache> getTacheList(){
        return tachelist;
    }

    public int getNumero(){
        return numero;
    }

    public void setNom(String s){
        this.nom = s;
    }

}
