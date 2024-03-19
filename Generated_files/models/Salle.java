package com.vehicule.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idsalle;
    private String nom;

    public Salle(){

    }

    public Salle(int idsalle,String nom){
        this.idsalle = idsalle;
        this.nom = nom;
    }

    public void setIdsalle(int idsalle){
        this.idsalle = idsalle;
    }

    public int getIdsalle(){
        return this.idsalle;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return this.nom;
    }

}