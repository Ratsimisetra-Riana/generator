namespace com.example;

import note;

public class Examen {

    private int idexamen;
    private date dateexamen;

    public Examen(){

    }

    public void setIdexamen(int idexamen){
        this.idexamen = idexamen;
    }

    public int getIdexamen(){
        return this.idexamen;
    }

    public void setDateexamen(date dateexamen){
        this.dateexamen = dateexamen;
    }

    public date getDateexamen(){
        return this.dateexamen;
    }

}