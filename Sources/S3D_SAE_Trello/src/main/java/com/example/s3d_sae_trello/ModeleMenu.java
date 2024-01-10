package com.example.s3d_sae_trello;


import java.io.*;
import java.util.*;

/**
 * Gestion des données de l'application
 */
public class ModeleMenu implements Sujet, Serializable {

    /**
     * Declarations des attributs
     */
    private ArrayList<Observateur> observateurs; // Vues
    private ArrayList<ColonneLigne> colonneLignes;
    private ArrayList<Tache> tacheSelectionee;
    private Archive archive; // Archive
    private DiagrammeGantt gantt;
    private int nbColonnes; // Nombre de colonnes crée
    private int tacheCompositeNumId; // Numéro de la tâcheComposite
    private String typeVue;
    /*Attribut qui permettra de gérer les dépendances avec tache mère en clé et une liste des filles en objet*/
    private TreeMap<Tache, ArrayList<Tache>> dependance;
    //List qui contient les taches selectionnées sur l'interface, notamment pour gérer les dépendances de celles ci
    private ArrayList<Tache> tachesAjouterDependance;


    /**
     * Constructeur de modèle initialise les attributs
     */
    public ModeleMenu() {
        nbColonnes = 0;
        tacheCompositeNumId = 0;
        gantt = new DiagrammeGantt(this);
        observateurs = new ArrayList<>();
        colonneLignes = new ArrayList<>();
        typeVue = "Colonne";
        this.dependance = new TreeMap<>();
        this.archive = Archive.getInstance();
        this.tachesAjouterDependance = new ArrayList<>();
        this.tacheSelectionee = new ArrayList<>();
    }

    /**
     * Recupere type de la vue
     */
    public String getTypeVue() {
        return typeVue;
    }

    /**
     * Récupère la map des dépendances
     */
    public TreeMap<Tache, ArrayList<Tache>> getDependance() {
        return dependance;
    }

    /**
     * Change type de vue
     */
    public void setTypeVue(String s) {
        typeVue = s;
        this.notifierObservateurs();
    }

    public void setTacheCompositeNumId() {
        this.tacheCompositeNumId++;
    }

    /**
     * Recupere les observateurs
     */
    public ArrayList<Observateur> getObservateurs() {
        return observateurs;
    }

    /**
     * Recuere les colonnesLignes
     */
    public ArrayList<ColonneLigne> getColonneLignes() {
        return colonneLignes;
    }

    /**
     * Recupere l'archive
     */
    public Archive getArchive() {
        return archive;
    }

    /**
     * Recupere le diagramme de Gantt
     */
    public DiagrammeGantt getGantt() {
        return gantt;
    }

    /**
     * Recupere le nombre de colonneLigne
     */
    public int getNbColonnes() {
        return nbColonnes;
    }

    /**
     * Recupere l'identifiant de la tache
     */
    public int getTacheCompositeNumId() {
        return tacheCompositeNumId;
    }

    /**
     * Recupere les taches selectionees
     */
    public ArrayList<Tache> getTacheSelectionee() {
        return tacheSelectionee;
    }

    /**
     * Ajoute d'une nouvelle colonneLigne
     *
     * @param nom de la nouvelle colonneLigne
     */
    public void ajouterColonneLigne(String nom, int id) throws IOException {
        ColonneLigne c = new ColonneLigne(nom, id);
        this.colonneLignes.add(c);
        nbColonnes++;
        this.sauvegarderColonneLigne();
        this.notifierObservateurs();
    }

    /**
     * Recupere la colonneLigne par rapport au nom de la colonne
     * @param nomColonne nom de la colonne
     */
    public int recupererColonneLigneID(String nomColonne) {
        for (int i = 0; i < this.colonneLignes.size(); i++) {
            if (colonneLignes.get(i).getNom().equals(nomColonne)) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Ajoute une nouvelle tache ou sous tache dans la colonneLigne récupéré
     *
     * @param t              tache ou sous tache
     */

    public void ajouterCompositeTache(Tache t) throws IOException {
        if (t.getIdcolonne() >= 0 && t.getIdcolonne() < colonneLignes.size()) {
            this.colonneLignes.get(t.getIdcolonne()).ajouterTache(t);
            /**if (!(t.getId() < tacheCompositeNumId)) {
             tacheCompositeNumId++;
             }*/
            tacheCompositeNumId++;
            this.sauvegarderColonneLigne();
            this.notifierObservateurs();
        } else {
            System.out.println("Mauvais numéro de colonne: " + t.getIdcolonne());
        }
    }


    /**
     * Ajoute une nouvelle sous tache dans la tache recuperee
     *
     * @param idColonneLigne indice dans la liste
     * @param id_tache indice de la tache mere
     * @param t              sous tache
     */
    public void ajouterSousTache(int idColonneLigne, int id_tache, Tache t) throws IOException {
        this.colonneLignes.get(idColonneLigne).tachelist.get(id_tache).ajouterSousTache(t);
        this.sauvegarderColonneLigne();
        this.notifierObservateurs();
    }



    /**
     * Déplace une tache ou sous tache
     *
     * @param idColonneLigne    index colonneLigne courante
     * @param idNewColonneLigne index nouvelle colonneLigne
     * @param t                 tache ou sous tache
     */

    public void deplacerCompositeTache(int idColonneLigne, int idNewColonneLigne, Tache t) throws IOException {
        // Ajoute la tâche dans la nouvelle colonneLigne
        this.colonneLignes.get(idNewColonneLigne).ajouterTache(t);
        // Supprime la tâche présente dans la colonneLigne actuel
        this.colonneLignes.get(idColonneLigne).getTacheList().remove(t);
        this.sauvegarderColonneLigne();
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

    /**
     * Archive toutes les taches de la colonneLigne donnée
     * @param idColonneLigne id de la colonneLigne
     */
    public void archiverToutesTaches(int idColonneLigne) throws IOException {
        List<Tache> taches = this.colonneLignes.get(idColonneLigne).getTacheList();
        Iterator<Tache> iterator = taches.iterator();
        while (iterator.hasNext()) {
            Tache t = iterator.next();

            archive.ajouterTache(t);
            iterator.remove();
        }
        this.sauvegarderArchive();
        this.sauvegarderColonneLigne();
        this.notifierObservateurs();
    }

    /**
     * Archive la tache donnée
     * @param idColonneLigne id de la colonneLigne
     * @param t tache donnée
     */
    public void archiverTache(int idColonneLigne, Tache t) throws IOException {
        archive.ajouterTache(t);
        this.colonneLignes.get(idColonneLigne).supprimerTache(t);
        this.sauvegarderArchive();
        this.sauvegarderColonneLigne();
        this.notifierObservateurs();
    }


    /**
     * Désarchive la tache donnée
     * @param t tache donnée
     */
    public void desarchiverTache(Tache t) throws IOException {
        this.ajouterCompositeTache(t);
        this.archive.supprimerTache(t);
        this.sauvegarderArchive();
        this.sauvegarderColonneLigne();
        this.notifierObservateurs();
    }

    /**
     * Supprime la tache donnée
     * @param idColonneLigne id de la colonne
     * @param t tache donnée
     */
    public void supprimerTache(int idColonneLigne, Tache t) throws IOException {
        this.colonneLignes.get(idColonneLigne).supprimerTache(t);
        this.notifierObservateurs();
        this.sauvegarderColonneLigne();
    }

    /**
     * Supprime la colonneLigne
     * @param idColonneLigne id de la colonneLigne donnée
     */
    public void supprimerColonneLigne(int idColonneLigne) throws IOException {
        this.colonneLignes.remove(this.colonneLignes.get(idColonneLigne));
        this.nbColonnes--;
        this.notifierObservateurs();
        this.sauvegarderColonneLigne();
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
        try{
            for (Observateur o : this.observateurs) {
                o.actualiser(this);
            }
        }catch(IOException e){
            System.out.println("Input output exception");
        }

    }

    /**
     * Ajoute une dépendance Mère
     * @param t tache sur laquelle on va ajouter des dépendances
     * @param tacheMere listes de tache mere
     */
    private void ajouterDependanceMere(Tache t, ArrayList<Tache> tacheMere) {
        for (Tache tMere : tacheMere) {

            if (this.dependance.containsKey(tMere)) {
                ArrayList<Tache> tFille = this.dependance.get(tMere);
                tFille.add(t);
                this.dependance.put(tMere, tFille);
            } else {
                ArrayList<Tache> tFille = new ArrayList<>();
                tFille.add(t);
                this.dependance.put(tMere, tFille);
            }
        }
        this.tachesAjouterDependance.clear();
    }

    /**
     * Ajoute dépendance fille a la tache donnée
     * @param t tache donnée
     * @param tacheFille liste taches fille
     */
    private void ajouterDependanceFille(Tache t, ArrayList<Tache> tacheFille) {
        ArrayList<Tache> taches = new ArrayList<>();
        for (Tache tf : tacheFille) {
            taches.add(tf);
        }
        this.dependance.put(t, taches);
    }

    /**
     * Ajoute une dépendance mère ou fille à la tache donnée
     * @param t tache donnée
     * @param taches liste taches mère ou fille
     * @param type type mere ou fille
     */
    public void ajouterDependance(Tache t, ArrayList<Tache> taches, String type) {
        if (type.equals("fille")) {
            ajouterDependanceFille(t, taches);
        } else {
            ajouterDependanceMere(t, taches);
        }
    }


    /**
     * Echange deux colonnesLignes
     * @param colonneLigne1 colonneLigne 1
     * @param colonneLigne2 colonneLigne 2
     */
    public void echangerColonneLigne(int colonneLigne1, int colonneLigne2) {
        ColonneLigne cl = this.colonneLignes.get(colonneLigne1);
        this.colonneLignes.set(colonneLigne1, this.colonneLignes.get(colonneLigne2));
        this.colonneLignes.set(colonneLigne2, cl);
        this.notifierObservateurs();
    }


    /**
     * Recupère une tache dans une colonne donnée avec son nom
     * @param idAncienneColonne id de la colonne
     * @param txtCol nom de la tache
     */
    public Tache recupererTache(int idAncienneColonne, String txtCol) {
        for (Tache t : this.colonneLignes.get(idAncienneColonne).getTacheList()) {
            if (t.getNom().equals(txtCol)) {
                return t;
            }
        }
        return null;
    }


    /**
     * Supprime sous tache d'une tache grâce à son nom
     */
    public Tache supprimerSousTache(Tache ancienneTache, String txtSousTache){
        for (Tache sousTache : ancienneTache.getSousTaches()){
            if(sousTache.getNom().equals(txtSousTache)){
                ancienneTache.retirerSousTache(sousTache);
                return sousTache;
            }
            supprimerSousTache(sousTache, txtSousTache);
        }
        return null;
    }





    /**
     * Récupéré toutes les tâches sans la tache passée en paramètre et les tâches filles déjà existante
     */
    public ArrayList<Tache> recupererToutesTachesSansMere(Tache t) {
        ArrayList<Tache> listeTaches = this.recupererToutesTaches();
        for (int i = 0; i < listeTaches.size(); i++) {
            Tache tt = listeTaches.get(i);
            if(tt.getDateDebutReal().isAfter(t.getDateDebutReal())){
                listeTaches.remove(tt);
            }
            // Vérifie si la tache t n'est pas dans l'arrayList fille de la tache mère de la Map
            else if (this.dependance.containsKey(tt)) {
                if (this.dependance.get(tt).contains(t)) {
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
    public ArrayList<Tache> recupererToutesTachesSansFille(Tache t) {
        ArrayList<Tache> listeTaches = this.recupererToutesTaches();
        if (this.dependance.containsKey(t)) {
            for (Tache tacheFille : this.dependance.get(t)) {
                listeTaches.remove(tacheFille);
            }
        }
        for (int i = 0; i < listeTaches.size(); i++) {
            Tache tt = listeTaches.get(i);
            if (tt.getDateDebutReal().isBefore(t.getDateDebutReal())) {
                listeTaches.remove(tt);
            }
        }
        listeTaches.remove(t);
        return listeTaches;
    }


    /**
     * Recupere toutes les taches de l'application
     */
    public ArrayList<Tache> recupererToutesTaches() {
        ArrayList<Tache> listeTaches = new ArrayList<>();
        for (ColonneLigne cl : this.colonneLignes) {
            for (Tache t : cl.tachelist) {
                listeTaches.add(t);
                for (Tache sousTache : t.getSousTaches()) {
                    listeTaches.add(sousTache);
                }
            }
        }
        return listeTaches;
    }

    /**
     * Permet de récupérer les "premières mères" soit les taches qui ne possède pas de tache mère afin de créer le diagramme
     * à partir de celles ci, en descendant petit à petit l'arborescence
    */
    public ArrayList<Tache> recupererTachesSansMere() {
        TreeMap<Tache, ArrayList<Tache>> map = this.dependance;

        // On stock les taches sans mere ici pour retour
        ArrayList<Tache> tachessansmere = new ArrayList<>();

        // On parcourt les taches pour trier celles sans mère
        for (Tache tache : map.keySet()) {

            boolean amere = false;

            // On itère dans les taches dont dépend une tache mere
            for (ArrayList<Tache> filles : map.values()) {
                if (filles.contains(tache)) {
                    amere = true;
                    break;
                }
            }

            //Si elle n'a pas de mere alors on l'ajoute
            if (!amere) {
                tachessansmere.add(tache);
            }
        }

        return tachessansmere;
    }

    /**
     * Ajoute une tache dans la liste de dépendance a ajouter
     */
    public void ajouterTacheListeTacheDependance(Tache tacheDependante) {
        this.tachesAjouterDependance.add(tacheDependante);
    }

    /**
     * Supprime une tache dans la liste de dépendance a ajouter
     */
    public void supprimerTacheListeTacheDependance(Tache tacheDependante) {
        this.tachesAjouterDependance.remove(tacheDependante);
    }


    /**
     * Recupere les taches ou l'on va ajouter des dépendances
     * @return
     */
    public ArrayList<Tache> getTachesAjouterDependance() {
        return tachesAjouterDependance;
    }

    /**
     * Ajoute une tache selectionnee a la liste
     */
    public void ajouterTacheSelectionee(Tache t){
        this.tacheSelectionee.add(t);
    }

    /**
     * Supprime  une tache selectionnee a la liste
     */
    public void supprimerTacheSelectionee(Tache t){
        this.tacheSelectionee.remove(t);
    }

    /**
     * Cette méthode permet de sauvegarder dans un fichier les colonnes et leur données afin de pouvoir
     * récupérer les données de l'application lors de la prochaine exécution
     * Cette méthode est appellée à chaque modification d'une colonne
     * @throws IOException
     */
    public void sauvegarderColonneLigne() throws IOException {
        try (FileOutputStream os = new FileOutputStream("colonne.txt");
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(this.colonneLignes);
        }
    }

    /**
     * Cette méthode permet de sauvegarder dans un fichier l'archive et ses données afin de pouvoir
     * récupérer les données de l'archive lors de la prochaine exécution
     * Cette méthode est appellée à chaque modification de l'archive
     * @throws IOException
     */
    public void sauvegarderArchive() throws IOException {
        try (FileOutputStream os = new FileOutputStream("archive.txt");
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(this.archive);
        }
    }


    /**
     * Cette méthode permet de récupérer les données stockées dans le fichier colonne, elle est appellée
     * à l'initialisation de l'interface
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void recupererSauvegardeColonneLigne() throws IOException, ClassNotFoundException {
        try (FileInputStream is = new FileInputStream("colonne.txt");
             ObjectInputStream ois = new ObjectInputStream(is)) {
            this.colonneLignes = (ArrayList<ColonneLigne>) ois.readObject();
            this.nbColonnes += this.colonneLignes.size();
        } catch (EOFException e) {
            System.out.println("Aucune donnée dans le fichier, aucun objet chargé");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        notifierObservateurs();
    }

    /**
     * Cette méthode permet de récupérer les données stockées dans le fichier archive, elle est appellée
     * à l'initialisation de l'interface
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void recupererSauvegardeArchive() throws IOException, ClassNotFoundException {
        try (FileInputStream is = new FileInputStream("archive.txt");
             ObjectInputStream ois = new ObjectInputStream(is)) {
            this.archive = (Archive) ois.readObject();
        } catch (EOFException e) {
            System.out.println("Aucune donnée dans le fichier, aucun objet chargé");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        notifierObservateurs();
    }

    public void sauvegarderDependance() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("dependance.txt"))) {
            oos.writeObject(dependance);
        }
    }

    public void recupererDependance() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("dependance.txt"))) {
            dependance = (TreeMap<Tache, ArrayList<Tache>>) ois.readObject();
        } catch (EOFException e) {
            System.out.println("Aucune donnée dans le fichier, aucun objet chargé");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        notifierObservateurs();
    }

    /**
     * Permet de supprimer les tâches selectionnées
     * @param tacheSelectionee Liste des taches selectionnées sur l'interface
     */
    public void supprimerListeTaches(ArrayList<Tache> tacheSelectionee){
        for (int i = 0; i<colonneLignes.size(); i++){
            for (int j = colonneLignes.get(i).getTacheList().size() - 1; j > -1; j--){
                // Suppression des tâches
                Tache tache = colonneLignes.get(i).getTacheList().get(j);
                if(tacheSelectionee.contains(tache)){
                    colonneLignes.get(i).tachelist.remove(tache);
                    tacheSelectionee.remove(tache);
                }
                // Suppression des sous taches

                for (int k = tache.getSousTaches().size()-1; k > -1; k--){
                    Tache soustache = tache.getSousTaches().get(k);
                    if(tacheSelectionee.contains(soustache)){
                        tacheSelectionee.remove(soustache);
                        tache.getSousTaches().remove(soustache);
                    }
                }
            }

        }
        this.notifierObservateurs();
    }

    /**
     * Permet de récupérer les taches filles "finales" qui ne possèdent, elles, pas de filles
     * @return List de tache des filles "finales"
     */
    public ArrayList<Tache> recupererTacheFinal(){
        ArrayList<Tache> tacheFinal = new ArrayList<>();
        for (Tache tt : this.dependance.keySet()){
            for (Tache tf : this.dependance.get(tt)){
                if(!this.dependance.containsKey(tf)){
                    if(!tacheFinal.contains(tf)){
                        tacheFinal.add(tf);
                    }
                }
            }
        }
        return tacheFinal;
    }

    /**
     * Récupère les tâches mère d'une tache entrée en paramètres
     * @param t tache dont on récupère les taches mères
     * @return List des taches mères
     */
    public ArrayList<Tache> recupererTacheMere(Tache t){
        ArrayList<Tache> tacheMere = new ArrayList<>();
        for (Tache tt : this.dependance.keySet()) {
            if(this.dependance.get(tt).contains(t)){
                tacheMere.add(tt);
            }
        }
        return tacheMere;

    }

    /**
     * Récupère les taches mère d'une liste de tache passée en paramètre
     * @param taches liste des taches dont on veut récupérer les mères
     * @return liste des mères des tâches
     */
    public ArrayList<ArrayList<Tache>> recupererListTachesMere(ArrayList<Tache> taches){
        ArrayList<ArrayList<Tache>> tacheMere = new ArrayList<>();
        tacheMere.add(taches);
        while (true){
            ArrayList<Tache> l = new ArrayList<>();
            for (Tache t :taches){
                ArrayList<Tache> tacheRecup = recupererTacheMere(t);
                l.addAll(tacheRecup);
            }
            if(l.size() == 0){
                break;
            }
            else{
                tacheMere.add(l);
                taches = l;
            }
        }
        return tacheMere;
    }
}
