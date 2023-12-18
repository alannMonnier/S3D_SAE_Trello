package com.example.saetrello;

import java.util.ArrayList;

/**
 * Classe qui contient des dépendances Mère et Fille
 */
public class Dependance {

    /**
     * Déclarations des attributs
     */
    private ArrayList<CompositeTache> tachesMere;
    private ArrayList<CompositeTache> tachesFille;

    /**
     * Constructeur de lc classe Dependance. Initialise les listes
     */
    public Dependance(){
        this.tachesMere = new ArrayList<>();
        this.tachesFille = new ArrayList<>();
    }

    /**
     * Récupère la liste contenant les tâches Filles
     */
    public ArrayList<CompositeTache> getTachesFille() {
        return tachesFille;
    }

    /**
     * Récupère la liste contenant les tâches Mères
     */
    public ArrayList<CompositeTache> getTachesMere() {
        return tachesMere;
    }

    /**
     * Ajoute une dépendance mère dans la liste tachesMere
     * @param t Tache ou SousTache
     */
    public void addDependanceMere(CompositeTache t){
        this.tachesMere.add(0, t);
    }

    /**
     * Ajoute une dépendance fille dans la liste tachesFille
     * @param t Tache ou SousTache
     */
    public void addDependanceFille(CompositeTache t){
        this.tachesFille.add(0, t);
    }

    /**
     * Affiche les dépendances de la tâche ou sous tâche passée en paramètre
     * @param t tâche ou sous tâche
     * @return uen chaine contenant les dépendances
     */
    public String afficherDependance(CompositeTache t){
        StringBuilder sb = new StringBuilder();
        // Ajout des dépendances mères
        sb.append("Taches ou sous taches à réaliser avant la tâche " + t.getNom() + "\n");
        int i = 0;
        for (CompositeTache ct: this.tachesMere){
            sb.append(i + ". => " + ct.getNom() + "\n");
        }
        // Ajout des dépendances filles
        sb.append("Taches ou sous taches à réaliser après la tâche " + t.getNom() + "\n");
        i = 0;
        for (CompositeTache ct: this.tachesFille){
            sb.append(i + ". => " + ct.getNom() + "\n");
        }
        return sb.toString();
    }
}
