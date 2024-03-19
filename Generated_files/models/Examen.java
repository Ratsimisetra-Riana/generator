package com.vehicule.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idexamen;
    @ManyToOne
    @JoinColumn(name="idsalle")
    private Salle salle;
    private String dateexamen;

    public Examen(){

    }

    public Examen(int idexamen,Salle salle,String dateexamen){
        this.idexamen = idexamen;
        this.salle = salle;
        this.dateexamen = dateexamen;
    }

    public void setIdexamen(int idexamen){
        this.idexamen = idexamen;
    }

    public int getIdexamen(){
        return this.idexamen;
    }

    public void setSalle(Salle salle){
        this.salle = salle;
    }

    public Salle getSalle(){
        return this.salle;
    }

    public void setDateexamen(String dateexamen){
        this.dateexamen = dateexamen;
    }

    public String getDateexamen(){
        return this.dateexamen;
    }

}