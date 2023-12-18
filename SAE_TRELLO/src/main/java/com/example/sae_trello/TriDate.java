package com.example.sae_trello;

public class TriDate extends Tri{

    public ColonneLigne tab;

    public TriDate(ColonneLigne c){
        this.tab = c;
    }
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Tache next() {
        return null;
    }
}
