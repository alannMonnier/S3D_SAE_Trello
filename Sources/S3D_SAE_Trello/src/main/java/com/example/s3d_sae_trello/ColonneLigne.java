package com.example.s3d_sae_trello;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Gestion ColonneLigne
 */
public class ColonneLigne {

    /**
     * Declarations attributs
     */
    public List<Tache> tachelist;
    public int numero;
    public String nom;

    /**
     * Constructeur
     * @param s Nom colonneLigne
     * @param i Numéro colonneLigne
     */
    public ColonneLigne(String s, int i) {
        this.numero = i;
        this.nom = s;
        this.tachelist = new ArrayList<Tache>();
    }

    /**
     * Ajoute une tache à la liste de tache
     */
    public void ajouterTache(Tache t){
        this.tachelist.add(t);
    }

    /**
     * Supprime une tache de la liste de tache
     */
    public void supprimerTache(Tache t){
        this.tachelist.remove(t);
    }

    /**
     * Tri les taches par date
     */
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

    /**
     * Tri les taches par urgence
     */
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

    /**
     * Tri les taches par ordre alphabétique de nom
     */
    public void trierAlphabetique() {

        Collections.sort(tachelist, new Comparator<Tache>() {
            @Override
            public int compare(Tache t1, Tache t2) {
                return t1.getNom().compareTo(t2.getNom());
            }
        });
    }


    /**
     * Recupere les taches de la colonneLigne
     */
    public ArrayList<Tache> getTacheList(){
        return (ArrayList<Tache>) tachelist;
    }

    /**
     * Renvoie une tache grâce à son nom
     */
    public Tache getTache(String nomTache){
        for (Tache t : this.tachelist){
            if(t.getNom().equals(nomTache)){
                return t;
            }
        }
        return null;
    }

    /**
     * Modifie le nom de la colonne
     */
    public void setNom(String s) {
        this.nom = s;
    }

    /**
     * Recupere le nom de la colonne
     */
    public String getNom() {
        return nom;
    }

    /**
     * Affiche les informations sur les taches contenu dans la colonneLigne
     */
    public String toString(){
        String res = "La liste est composée des tâches suivantes:\n";
        for(Tache t : this.tachelist){
            res += t.toString();
        }
        return res;
    }
}
