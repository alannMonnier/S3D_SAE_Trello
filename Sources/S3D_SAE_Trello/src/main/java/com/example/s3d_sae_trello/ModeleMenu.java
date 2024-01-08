package com.example.s3d_sae_trello;


import java.util.*;

public class ModeleMenu implements Sujet {

    /**
     * Declarations des attributs
     */
    private ArrayList<Observateur> observateurs; // Vues
    private ArrayList<ColonneLigne> colonneLignes; // tableau de tacheComposite
    private Archive archive; // Archive
    private DiagrammeGantt gantt;
    private int nbColonnes; // Nombre de colonnes crée
    private int tacheCompositeNumId; // Numéro de la tâcheComposite
    private String typeVue;
    private TreeMap<Tache, ArrayList<Tache>> dependance;
    private ArrayList<Tache> tachesAjouterDependance;


    /**
     * Constructeur de modèle initialise les attributs
     */
    public ModeleMenu() {
        nbColonnes = 0;
        tacheCompositeNumId = 0;
        gantt = new DiagrammeGantt("", 0);
        observateurs = new ArrayList<>();
        colonneLignes = new ArrayList<>();
        typeVue = "Colonne";
        this.dependance = new TreeMap<>();
        this.archive = Archive.getInstance();
        this.tachesAjouterDependance = new ArrayList<>();
    }

    public String getTypeVue() {
        return typeVue;
    }

    public void setTypeVue(String s) {
        typeVue = s;
        this.notifierObservateurs();
    }

    public ArrayList<Observateur> getObservateurs() {
        return observateurs;
    }

    public ArrayList<ColonneLigne> getColonneLignes() {
        return colonneLignes;
    }

    public Archive getArchive() {
        return archive;
    }

    public DiagrammeGantt getGantt() {
        return gantt;
    }

    public int getNbColonnes() {
        return nbColonnes;
    }

    public int getTacheCompositeNumId() {
        return tacheCompositeNumId;
    }

    /**
     * Ajoute d'une nouvelle colonneLigne
     *
     * @param nom de la nouvelle colonneLigne
     */
    public void ajouterColonneLigne(String nom, int id) {
        this.colonneLignes.add(new ColonneLigne(nom, id));
        nbColonnes++;
        this.notifierObservateurs();
    }


    public int recupererColonneLigneID(String nomColonne){
        for (int i=0; i<this.colonneLignes.size(); i++){
            if(colonneLignes.get(i).getNom().equals(nomColonne)){
                return i;
            }
        }
        return -1;
    }





    /**
     * Ajoute une nouvelle tache ou sous tache dans la colonneLigne récupéré
     *
     * @param idColonneLigne indice dans la liste
     * @param t              tache ou sous tache
     */
<<<<<<< HEAD
    public void ajouterCompositeTache(int idColonneLigne, Tache t){
=======
    public void ajouterCompositeTache(int idColonneLigne, CompositeTache t) {
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b
        this.colonneLignes.get(idColonneLigne).ajouterTache(t);
        if(!(t.getId() < tacheCompositeNumId)){
            tacheCompositeNumId++;
        }
        this.notifierObservateurs();
    }

    /**
     * Déplace une tache ou sous tache
     *
     * @param idColonneLigne    index colonneLigne courante
     * @param idNewColonneLigne index nouvelle colonneLigne
     * @param t                 tache ou sous tache
     */
<<<<<<< HEAD
    public void deplacerCompositeTache(int idColonneLigne, int idNewColonneLigne, Tache t){
        // Ajoute la tâche dans la nouvelle colonneLigne
        this.colonneLignes.get(idNewColonneLigne).ajouterTache(t);
=======
    public void deplacerCompositeTache(int idColonneLigne, int idNewColonneLigne, CompositeTache t) {
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b
        // Supprime la tâche présente dans la colonneLigne actuel
        this.colonneLignes.get(idColonneLigne).getTacheList().remove(t);
        this.notifierObservateurs();
    }

    /**
     * Methode qui trie la colonneLigne en fonction d'un paramètre donnée
     *
     * @param idColonneLigne index colonneLigne à trier
     * @param typeTri        type de tri à utiliser
     */
    public void trierColonneLigne(int idColonneLigne, String typeTri) {
        ColonneLigne cl = this.colonneLignes.get(idColonneLigne);
        switch (typeTri) {
            case "date":
                cl.trierDate();
                break;
            case "urgence":
                cl.trierUrgence();
                break;
            case "alphabetique":
                cl.trierAlphabetique();
                break;
            default:
                try {
                    throw new Exception("Erreur");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
        this.notifierObservateurs();
    }

<<<<<<< HEAD


    public void archiverToutesTaches(int idColonneLigne){
        List<Tache> taches = this.colonneLignes.get(idColonneLigne).getTacheList();
        Iterator<Tache> iterator = taches.iterator();
        while (iterator.hasNext()) {
            Tache t = iterator.next();
=======
    /**
     * MVC
     */
    public void afficherColonne() {

    }

    /**
     * MVC
     */
    public void afficherListe() {

    }

    /**
     * MVC
     */
    public void afficherGantt() {

    }

    public void archiverToutesTaches(int idColonneLigne) {
        for (CompositeTache t : this.colonneLignes.get(idColonneLigne).getTacheList()) {
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b
            archive.ajouterTache(t);
            iterator.remove();
        }
        this.notifierObservateurs();
    }

<<<<<<< HEAD
    public void archiverTache(int idColonneLigne, Tache t){
=======
    public void archiverTache(int idColonneLigne, int idTache) {
        CompositeTache t = this.colonneLignes.get(idColonneLigne).trouverTache(idTache);
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b
        archive.ajouterTache(t);
        this.colonneLignes.get(idColonneLigne).supprimerTache(t);
        this.notifierObservateurs();
    }

<<<<<<< HEAD
    public void desarchiverTache(Tache t){
        this.ajouterCompositeTache(0, t);
        this.archive.supprimerTache(t);
        this.notifierObservateurs();
    }

    public void supprimerTache(int idColonneLigne, Tache t){
=======
    public void supprimerTache(int idColonneLigne, int idTache) {
        CompositeTache t = this.colonneLignes.get(idColonneLigne).trouverTache(idTache);
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b
        this.colonneLignes.get(idColonneLigne).supprimerTache(t);
        this.notifierObservateurs();
    }

    public void supprimerColonneLigne(int idColonneLigne) {
        this.colonneLignes.remove(this.colonneLignes.get(idColonneLigne));
        this.nbColonnes--;
        this.notifierObservateurs();
    }

    /**
     * Ajoute un nouvel observateur
     *
     * @param o observateur
     */
    @Override
    public void ajouterObservateur(Observateur o) {
        this.observateurs.add(o);
    }

    /**
     * Supprime un observateur
     *
     * @param o observateur
     */
    @Override
    public void supprimerObservateur(Observateur o) {
        this.observateurs.remove(o);
    }

    /**
     * Notifie les observateurs qu'il faut actualiser l'affichage
     */
    @Override
    public void notifierObservateurs() {
        for (Observateur o : this.observateurs) {
            o.actualiser(this);
        }
    }

    private void ajouterDependanceMere(Tache t, ArrayList<Tache> tacheMere){
        for(Tache tMere : tacheMere){

            if(this.dependance.containsKey(tMere)){
                ArrayList<Tache> tFille = this.dependance.get(tMere);
                tFille.add(t);
                this.dependance.put(tMere, tFille);
            }
            else{
                ArrayList<Tache> tFille = new ArrayList<>();
                tFille.add(t);
                this.dependance.put(tMere, tFille);
            }
        }
        this.tachesAjouterDependance.clear();
    }

    private void ajouterDependanceFille(Tache t, ArrayList<Tache> tacheFille){
        ArrayList<Tache> taches = new ArrayList<>();
        for (Tache tf : tacheFille){
            taches.add(tf);
        }
        this.dependance.put(t, taches);
    }

    public void ajouterDependance(Tache t, ArrayList<Tache> taches, String type){
        if(type.equals("fille")){
            ajouterDependanceFille(t, taches);
        }
        else {
            ajouterDependanceMere(t, taches);
        }
    }



    public void echangerColonneLigne(int colonneLigne1, int colonneLigne2){
        ColonneLigne cl = this.colonneLignes.get(colonneLigne1);
        this.colonneLignes.set(colonneLigne1, this.colonneLignes.get(colonneLigne2));
        this.colonneLignes.set(colonneLigne2, cl);
        this.notifierObservateurs();
    }


    public Tache recupererTache(int idAncienneColonne, String txtCol){
        for (Tache t : this.colonneLignes.get(idAncienneColonne).getTacheList() ){
            if(t.getNom().equals(txtCol)){
                return t;
            }
        }
        return null;
    }

    public Tache supprimerSousTache(Tache ancienneTache, String txtSousTache){
        for (Tache sousTache : ancienneTache.getSousTaches()){
            if(sousTache.getNom().equals(txtSousTache)){
                ancienneTache.retirerSousTache(sousTache.getId());
                return sousTache;
            }
            supprimerSousTache(sousTache, txtSousTache);
        }
        return null;
    }


    /**
     * Récupéré toutes les tâches sans la tache passée en paramètre et les tâches filles déjà existante
     */
    public ArrayList<Tache> recupererToutesTachesSansMere(Tache t){
        ArrayList<Tache> listeTaches = this.recupererToutesTaches();

        for (int i = 0; i < listeTaches.size(); i++){
            Tache tt = listeTaches.get(i);
            // Vérifie si la tache t n'est pas dans l'arrayList fille de la tache mère de la Map
            if(this.dependance.containsKey(tt)){
                if(this.dependance.get(tt).contains(t)){
                    listeTaches.remove(tt);
                    i--;
                }
            }
        }
        listeTaches.remove(t);
        return listeTaches;
    }


    /**
     * Récupéré toutes les tâches sans la tache passée en paramètre et les tâches filles déjà existante
     */
    public ArrayList<Tache> recupererToutesTachesSansFille(Tache t){
        ArrayList<Tache> listeTaches = this.recupererToutesTaches();
        if(this.dependance.containsKey(t)){
            for (Tache tacheFille : this.dependance.get(t)){
                listeTaches.remove(tacheFille);
            }
        }

        listeTaches.remove(t);
        return listeTaches;
    }


    public ArrayList<Tache> recupererToutesTaches(){
        ArrayList<Tache> listeTaches = new ArrayList<>();
        for (ColonneLigne cl : this.colonneLignes){
            for (Tache t : cl.tachelist){
                listeTaches.add(t);
                for (Tache sousTache: t.getSousTaches()){
                    listeTaches.add(sousTache);
                }
            }
        }
        return listeTaches;
    }

    public void ajouterTacheListeTacheDependance(Tache tacheDependante){
        this.tachesAjouterDependance.add(tacheDependante);
    }

    public void supprimerTacheListeTacheDependance(Tache tacheDependante){
        this.tachesAjouterDependance.remove(tacheDependante);
    }

    public ArrayList<Tache> getTachesAjouterDependance() {
        return tachesAjouterDependance;
    }
}
