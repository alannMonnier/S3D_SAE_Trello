package com.example.s3d_sae_trello;

// Tache classique qui ne possède pas de sous taches

public class Tache extends CompositeTache {

    public Tache(int id, String n, int urgence, int tempsEstime) {
        super(id, n, urgence, tempsEstime);
    }


    @Override
    public void ajouterSousTache(CompositeTache t) {

    }

    @Override
    public void retirerSousTache(int id) {

    }

    @Override
    public boolean equals(Object obj) {
        Tache t = (Tache)obj;
        return t.id == this.id;
    }
}