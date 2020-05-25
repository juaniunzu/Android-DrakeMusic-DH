package com.example.projectointegrador.model;

import com.example.projectointegrador.util.Utils;

import java.io.Serializable;

public class Artist implements Serializable, Utils.Searchable {
    private Integer id;
    private String name;
    private String picture;

    public Artist(Integer id, String name,String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String informarTitulo() {
        return getName();
    }

    @Override
    public String informarImagen() {
        return getPicture();
    }

    @Override
    public String informarDescripcion() {
        return "Artista";
    }
}
