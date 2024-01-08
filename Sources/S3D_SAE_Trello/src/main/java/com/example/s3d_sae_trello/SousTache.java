package com.example.s3d_sae_trello;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class SousTache extends Tache{

    private int idSousTache;

    public SousTache(int id, String nom, int degreUrgence, int tempsEstime, LocalDate dateDebutReal, int idSousTache){
        super(id, nom, "", degreUrgence, tempsEstime, dateDebutReal);

<<<<<<< HEAD
    }


=======
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
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b
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