package com.example.s3d_sae_trello;

public class Coordonnees {

    private int x;
    private int y;

    public Coordonnees(int _x, int _y){
        this.x = _x;
        this.y = _y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        Coordonnees c = (Coordonnees) obj;
        return c.x == this.x && c.y == this.y;
    }
}
