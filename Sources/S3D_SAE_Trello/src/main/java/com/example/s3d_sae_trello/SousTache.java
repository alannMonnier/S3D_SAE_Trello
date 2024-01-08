package com.example.s3d_sae_trello;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class SousTache extends Tache{

    private int idSousTache;

    public SousTache(int id, String nom, int degreUrgence, int tempsEstime, LocalDate dateDebutReal, int idSousTache){
        super(id, nom, "", degreUrgence, tempsEstime, dateDebutReal);
    }



    @Override
    public boolean equals(Object obj) {
        if(this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) return false;
        Tache t = (Tache) obj;
        return this.getId() == t.getId() && Objects.equals(getNom(), t.getNom()) && Objects.equals(getDescription(), t.getDescription())
                && Objects.equals(getDateCreation(), t.getDateCreation()) && getDegreUrgence() == t.getDegreUrgence()
                && getTempsEstime() == t.getTempsEstime() && getTacheRealise() == t.getTacheRealise()
                && Objects.equals(getDateDebutReal(), t.getDateDebutReal()) && getTacheRealise() == t.getTacheRealise()
                && Objects.equals(getSousTaches(), t.getSousTaches()) && idSousTache == t.getIdSousTache()
                && isAfficherSousTache() == t.isAfficherSousTache() && isEstSelectionne() == t.isEstSelectionne();
    }
}