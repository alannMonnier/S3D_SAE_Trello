package com.example.sae_trello;

import com.example.saetrello.ColonneLigne;

public class Archive extends ColonneLigne {

    private Archive instance;

    private Archive(String s, int i) {
        super(s, i);
    }

    public Archive getInstance(){
        if(instance == null){
            instance = new Archive("Archive", 0);
        }
        return instance;
    }
}
