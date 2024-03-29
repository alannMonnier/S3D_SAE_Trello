package com.example.s3d_sae_trello;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Gestion d'une tache
 */
public class Tache implements Comparable<Tache>, Serializable {

    /**
     * Declaration des attributs
     */
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

    private int idcolonne;


    /**
     * Constructeur
     * @param id id tache
     * @param nom nom tache
     * @param description description tache
     * @param degreUrgence degre urgence
     * @param tempsEstime temps pour réaliser la tache
     * @param dateDebutReal date début réalisation de la tache
     */
    public Tache(int id, String nom, String description, int degreUrgence, int tempsEstime, LocalDate dateDebutReal, int idcolonne) {
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
        this.idcolonne = idcolonne;
    }

    /**
     * Ajoute une sous tache à la tache
     */
    public void ajouterSousTache(Tache st){
        this.idSousTache++;
        this.sousTaches.add(st);

    }

    /**
     * Retirer une sousTache de la liste
     */
    public void retirerSousTache(Tache t){
        this.sousTaches.remove(t);
    }

    /**
     * Recupere l'id
     */
    public int getId() {
        return id;
    }

    /**
     * Recupere le nom de la tache
     */
    public String getNom() {
        return nom;
    }

    public int getIdcolonne(){return this.idcolonne;};

    /**
     * Recupere date de création
     */
    public LocalDate getDateCreation() {
        return dateCreation;
    }

    /**
     * Recuepre degré urgence
     */
    public int getDegreUrgence() {
        return degreUrgence;
    }

    /**
     * Récupère temps estimé
     */
    public int getTempsEstime() {
        return tempsEstime;
    }

    /**
     * Récupère si tache est réalisé
     */
    public boolean getTacheRealise() {
        return tacheRealise;
    }

    /**
     * Récupère date début réalisation
     */
    public LocalDate getDateDebutReal() {
        return dateDebutReal;
    }


    /**
     * Récupère id sous tache
     */
    public int getIdSousTache() {
        return idSousTache;
    }

    /**
     * Récupère description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Récupère liste soustache
     */
    public ArrayList<Tache> getSousTaches() {
        return sousTaches;
    }

    /**
     * Récupère si on peut afficher la soustache
     */
    public boolean isAfficherSousTache() {
        return afficherSousTache;
    }

    /**
     * Récupère si la tache est selectionée
     */
    public boolean isEstSelectionne() {
        return estSelectionne;
    }

    /**
     * Valide ou interdit l'affichage sous tache
     */
    public void setAfficherSousTache(boolean afficherSousTache) {
        this.afficherSousTache = afficherSousTache;
    }

    /**
     * Valide ou supprime la validation d'une tache
     */
    public void setEstSelectionne(boolean estSelectionne) {
        this.estSelectionne = estSelectionne;
    }

    /**
     * Modife l'id de la colonne
     * @param idcolonne
     */
    public void setIdcolonne(int idcolonne) {
        this.idcolonne = idcolonne;
    }

    /**
     * Compare de tache par rapport à la date de réalisation puis l'id
     */
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


    /**
     * Compare deux taches entre elles
     */
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


    /**
     * Hashcode de la tache
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, nom, description, dateCreation, degreUrgence, tempsEstime, tacheRealise,
                dateDebutReal, sousTacheComplete, sousTaches, idSousTache, afficherSousTache, estSelectionne);
    }

    public String toString() {
        String res = "ID: " + this.id + "\n";
        res += "Nom: " + this.nom + "\n";
        res += "Description: " + this.description + "\n";
        res += "Date de création: " + this.dateCreation + "\n";
        res += "Degré d'urgence: " + this.degreUrgence + "\n";
        res += "Temps estimé: " + this.tempsEstime + "\n";
        res += "Tâche réalisée: " + this.tacheRealise + "\n";
        res += "Date début réalisation: " + this.dateDebutReal + "\n";
        res += "Sous-tâche complète: " + this.sousTacheComplete + "\n";
        res += "ID sous-tâche: " + this.idSousTache + "\n";
        res += "Afficher sous-tâche: " + this.afficherSousTache + "\n";
        res += "Est sélectionnée: " + this.estSelectionne + "\n";

        return res;
    }


    /**
     * Retourne la sous tache a partir de son nom
     */
    public Tache recupererSousTache(String nomSousTache){
        for (Tache sousTache : this.getSousTaches()){

            if(sousTache.getNom().equals(nomSousTache)){
                return sousTache;
            }
            Tache st = sousTache.recupererSousTache(nomSousTache);
            if(st != null){
                return st;
            }
        }
        return null;
    }


    public void supprimerSousTaches(Tache sousTache){
        for (Tache st : this.getSousTaches()){
            if(st.getNom().equals(sousTache.getNom())){
                this.getSousTaches().remove(st);
                break;
            }
            else{
                st.supprimerSousTaches(sousTache);
            }
        }
    }



    /**
     * Calcule la somme des temps estimés de toutes les sous-tâches.
     *
     * @return La somme des temps estimés des sous-tâches.
     */
    private int sommeTempsEstimeSousTaches() {
        int somme = 0;
        for (Tache sousTache : sousTaches) {
            somme += sousTache.getTempsEstime();
        }
        return somme;
    }

    /**
     * Vérifie si l'ajout d'une nouvelle sous-tâche est possible sans dépasser
     * le temps estimé de la tâche mère.
     *
     * @param nouvelleSousTache La sous-tâche à ajouter.
     * @return true si l'ajout est possible, false sinon.
     */
    public boolean ajout_sous_tache_possible(Tache nouvelleSousTache) {
        int tempsTotalSousTaches = sommeTempsEstimeSousTaches();
        return (tempsTotalSousTaches + nouvelleSousTache.getTempsEstime()) <= this.tempsEstime;
    }

    /**
     * Vérifie si une sous-tâche avec le nom spécifié existe déjà.
     *
     * @param nomSousTache Le nom de la sous-tâche à vérifier.
     * @return true si une sous-tâche avec ce nom existe, false sinon.
     */
    public boolean nomSousTacheExiste(Tache nomSousTache) {
        for (Tache sousTache : sousTaches) {
            if (sousTache.getNom().equals(nomSousTache.getNom())) {
                return true;
            }
            // Vérifie également les sous-tâches des sous-tâches (récursion)
            if (sousTache.nomSousTacheExiste(nomSousTache)) {
                return true;
            }
        }
        return false;
    }

}
