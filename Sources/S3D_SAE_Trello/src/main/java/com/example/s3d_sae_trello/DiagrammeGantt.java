package com.example.s3d_sae_trello;

import java.util.ArrayList;

public class DiagrammeGantt{

    private ModeleMenu modele;

    public DiagrammeGantt(ModeleMenu m) {
        this.modele = m;
    }

    public void afficherGantt(){

        ArrayList<Tache> tachesansmere = modele.recupererTachesSansMere();

        for (Tache mere : tachesansmere) {
            parcourirFille(mere, 0);
            System.out.println("\n");
        }
    }


    public void  afficherGantt2(){

        ArrayList<Tache> tachesansmere = modele.recupererTachesSansMere();

    }




    public void parcourirFille(Tache mere, int rang) {
        System.out.print(getRang(rang) + mere.getNom());

        // Iterate through the daughters of the current task and recursively call the method
        if(modele.getDependance().get(mere) != null) {
            for (Tache fille : modele.getDependance().get(mere)) {
                parcourirFille(fille, rang + 1);
            }
        }
    }

    public void parcourirMeres(Tache tache, int rang) {
        if (tache != null) {
            ArrayList<Tache> meres = new ArrayList<>();

            // Trouve les taches meres de la tache donnee
            for (Tache t : modele.getDependance().keySet()) {
                if (modele.getDependance().get(t) != null && modele.getDependance().get(t).contains(tache)) {
                    meres.add(t);
                }
            }

            // Parcours les taches meres
            for (Tache mere : meres) {
                System.out.print(getRang(rang) + mere.getNom());
                parcourirMeres(mere, rang + 1);
            }
        }
    }

    private String getRang(int rang) {
        String espace = "";
        for (int i = 0; i < rang; i++) {
            espace += "  "; // Use two spaces for each level of depth
        }
        return espace;
    }

}
