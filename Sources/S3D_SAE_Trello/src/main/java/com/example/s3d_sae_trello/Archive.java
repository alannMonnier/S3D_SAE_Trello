package com.example.s3d_sae_trello;


public class Archive extends ColonneLigne {

    private static Archive instance;

    /**
     * Constructeur
     * @param s Nom archive
     * @param i Nombre élément
     */
    private Archive(String s, int i) {
        super(s, i);
    }

    /**
     * Créer instance unique d'archive
     * @return instance d'Archive
     */
    public static synchronized Archive getInstance(){
        if(instance == null){

            instance = new Archive("Archive", 0);
        }
        return instance;
    }
}
