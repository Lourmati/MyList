package com.example.mylist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Note implements Parcelable {
    private String nom;
    private ArrayList<String> liste;

    public Note(String nom, ArrayList<String> liste) {
        this.nom = nom;
        this.liste = liste;
    }

    protected Note(Parcel in) {
        nom = in.readString();
        liste = in.createStringArrayList();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nom);
        parcel.writeStringList(liste);
    }
}
