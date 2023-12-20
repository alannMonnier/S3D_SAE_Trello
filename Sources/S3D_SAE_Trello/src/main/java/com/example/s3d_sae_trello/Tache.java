package com.example.s3d_sae_trello;

// Tache classique qui ne possède pas de sous taches

public class Tache extends CompositeTache {
    public Tache(int id, String nomTache, String nomUtilisateur, String prenomUtilisateur, int urgence, int tempsEstime, String description) {
        super(id, nomTache, nomUtilisateur, prenomUtilisateur, urgence, tempsEstime, description);
    }

    @Override
    public boolean ajouterSousTache(CompositeTache t) {
        return false;
    }

    @Override
    public boolean retirerSousTache(int id) {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        Tache t = (Tache)obj;
        return t.id == this.id;
    }
}