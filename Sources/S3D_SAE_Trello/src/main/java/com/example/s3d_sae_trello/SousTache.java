package com.example.s3d_sae_trello;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Gestion des soustaches
 */
public class SousTache extends Tache implements Serializable {

    /**
     * Declaration attributs
     */
    private int idSousTache;

    /**
     * Constructeur
     */
    public SousTache(int id, String nom, String description, int degreUrgence, int tempsEstime, LocalDate dateDebutReal, int idcol){
        super(id, nom, description, degreUrgence, tempsEstime, dateDebutReal, idcol);
    }


    /**
     * Verifie que deux sous taches soient egales
     */
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