package com.example.projectointegrador.model;

import androidx.annotation.Nullable;

import com.example.projectointegrador.util.Utils;

import java.io.Serializable;

public class Artist implements Serializable, Utils.Searchable {
    private Integer id;
    private String name;
    private String picture;
    private String type;

    public Artist() {
    }

    //constructor para hardcodeados . Borrar mas adelante
    public Artist(Integer id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    public Artist(Integer id, String name, String picture, String type) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.type = type;
    }

    public Artist(String name) {
        this.name = name;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public boolean equals(@Nullable Object obj) {
         if (obj instanceof Artist){
             Artist artistAComparar = (Artist) obj;

             if(this.id.equals(artistAComparar.id)){
                 return true;
             }
             else{
                 return false;
             }
         }else{
             return false;
         }
    }
}
//        if (obj instanceof Profesor) {
//
//                Profesor tmpProfesor = (Profesor) obj;
//
//                if (super.equals(tmpProfesor) && this.IdProfesor.equals(tmpProfesor.IdProfesor) ) {
//
//                return true; } else { return false; }
//
//                }  else { return false; }
//
//                }
