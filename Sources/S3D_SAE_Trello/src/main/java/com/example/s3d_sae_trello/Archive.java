package com.example.s3d_sae_trello;

public class Archive extends ColonneLigne {

    private static Archive instance;

    private Archive(String s, int i) {
        super(s, i);
    }


    public static synchronized Archive getInstance(){
        if(instance == null){

            instance = new Archive("Archive", 0);
        }
        return instance;
    }
}
