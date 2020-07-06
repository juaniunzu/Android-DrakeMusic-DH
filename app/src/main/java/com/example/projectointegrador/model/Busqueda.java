package com.example.projectointegrador.model;


import java.io.Serializable;

public class Busqueda implements Serializable {

    private String busqueda;

    public Busqueda() {
    }

    public Busqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

}
