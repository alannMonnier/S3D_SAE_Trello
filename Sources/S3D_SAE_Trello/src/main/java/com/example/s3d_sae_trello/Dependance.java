package com.example.s3d_sae_trello;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe qui contient des dépendances Mère et Fille
 */
public class Dependance {

    /**
     * Déclarations des attributs
     */
    private Map<Tache, ArrayList<Tache>> tachesDependande;  // Key => Tache Mere, Val => List<Tache Fille>

    public Dependance(){
        this.tachesDependande = new TreeMap<>();
    }


    public void ajouterDependanceFilles(Tache tache, ArrayList<Tache> tachesFilles){
        this.tachesDependande.put(tache, tachesFilles);
    }

    public void ajouterDependanceMere(Tache tache, ArrayList<Tache> tachesMeres){
        // Cherche si on a déjà une clé dans parmi les taches meres
        for (Tache t : tachesMeres){
            if(this.tachesDependande.containsKey(t)){
                // Récupère la liste de la clé t
                ArrayList<Tache> tachesFilles = this.tachesDependande.get(t);
                // Ajoute notre tache dans la liste des taches filles
                tachesFilles.add(tache);
                this.tachesDependande.put(t, tachesFilles);
            }
            // La clé n'existe pas encore on va créer une clé mère et ajouter dans listeFille notre tache
            else{
                ArrayList<Tache> tachesFille = new ArrayList<>();
                tachesFille.add(tache);
                this.ajouterDependanceFilles(t, tachesFille);
            }
        }
    }


    public ArrayList<Tache> getTachesFilles(Tache tacheMere){
        if(this.tachesDependande.containsKey(tacheMere)){
            return this.tachesDependande.get(tacheMere);
        }
        return null;
    }

}
