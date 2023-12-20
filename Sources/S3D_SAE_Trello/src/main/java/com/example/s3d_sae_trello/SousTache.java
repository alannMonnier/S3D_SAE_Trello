package com.example.s3d_sae_trello;

import java.util.ArrayList;

// Tache qui possède des sous taches

public class SousTache extends CompositeTache {

    private ArrayList<CompositeTache> sousTaches;

    public SousTache(int id, String nomTache, String nomUtilisateur, String prenomUtilisateur, int urgence, int tempsEstime, String description) {
        super(id, nomTache, nomUtilisateur, prenomUtilisateur, urgence, tempsEstime, description);
        this.sousTaches = new ArrayList<>();
    }

    public boolean ajouterSousTache(CompositeTache t){
        sousTaches.add(t);
        return true;
    }

    public boolean retirerSousTache(int id){
        boolean retire = false;
        int indice = -1;
        for(CompositeTache t : sousTaches){
            if(t.getId() == id){
                indice = t.getId();
            }
        }

        if(indice != -1){
            sousTaches.remove(indice);
            retire = true;
        }

        return retire;
    }

    public ArrayList<CompositeTache> getSousTaches() {
        return sousTaches;
    }

    /**
     * Compare deux objets SousTache
     * @return true s'ils sont égaux false sinon
     */
    @Override
    public boolean equals(Object obj) {
        SousTache st = (SousTache) obj;
        return st.id == this.id;
    }
}