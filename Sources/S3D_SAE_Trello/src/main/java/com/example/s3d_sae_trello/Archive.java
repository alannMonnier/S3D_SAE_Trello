package com.example.s3d_sae_trello;

public class Archive extends ColonneLigne {

    private static Archive instance;

    private Archive(String s, int i) {
        super(s, i);
    }

<<<<<<< HEAD
    public static synchronized Archive getInstance(){
        if(instance == null){
=======
    public Archive getInstance() {
        if (instance == null) {
>>>>>>> 21e90624cb961e02791b0f38a6852032422c6b5b
            instance = new Archive("Archive", 0);
        }
        return instance;
    }
}
