package com.example.saetrello;

import java.util.ArrayList;

// Tache qui possède des sous taches

public class SousTache extends CompositeTache {

    private ArrayList<CompositeTache> sousTaches;

    public SousTache(int id, String n, int urgence, int tempsEstime) {
        super(id, n, urgence, tempsEstime);
        sousTaches = new ArrayList<>();
    }

    public void ajouterSousTache(CompositeTache t){
        sousTaches.add(t);
    }

    public void retirerSousTache(int id){
        int indice = -1;
        for(CompositeTache t : sousTaches){
            if(t.getId() == id){
                indice = t.getId();
            }
        }

        if(indice != -1){
            sousTaches.remove(indice);
        }
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
