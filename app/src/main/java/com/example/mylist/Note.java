package com.example.mylist;

import java.util.ArrayList;

public class Note {
    private String nom;
    private ArrayList<String> liste;

    public Note(String nom, ArrayList<String> liste) {
        this.nom = nom;
        this.liste = liste;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<String> getListe() {
        return liste;
    }

    public void setListe(ArrayList<String> liste) {
        this.liste = liste;
    }
}
