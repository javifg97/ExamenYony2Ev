package com.example.javierfernandez3.examenyony2ev.sqlLiteAdmin;

/**
 * Created by javier.fernandez3 on 19/02/2018.
 */

public class sqlCoche {

    public int id;
    public int Fabricado;
    public String Marca;
    public String Nombre;
    public double lat;
    public double lon;



    public sqlCoche(){

    }

    public sqlCoche(int id, int Fabricado,String Marca,String Nombre,double lat,double lon){
        this.id = id;
        this.Fabricado=Fabricado;
        this.Marca=Marca;
        this.Nombre=Nombre;
        this.lat=lat;
        this.lon=lon;

    }
    public sqlCoche(int Fabricado,String Marca,String Nombre,double lat,double lon){
        this.Fabricado=Fabricado;
        this.Marca=Marca;
        this.Nombre=Nombre;
        this.lat=lat;
        this.lon=lon;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFabricado() {
        return Fabricado;
    }

    public void setFabricado(int fabricado) {
        Fabricado = fabricado;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
