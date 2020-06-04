package com.example.projectointegrador.model;

import com.example.projectointegrador.R;
import com.example.projectointegrador.util.Utils;

import java.io.Serializable;

public class Album implements Serializable, Utils.Searchable {
    private Integer id;
    private String title;
    private String cover;
    private Artist artist;

    public Album(Integer id, String title, String cover) {
        this.id = id;
        this.title = title;
        this.cover = cover;
    }

    public Album(Integer id, String title, String cover, Artist artist) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.artist = artist;
    }

    public Album() {
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String informarTitulo() {
        return getTitle();
    }

    @Override
    public String informarImagen() {
        return getCover();
    }

    @Override
    public String informarDescripcion() {
        return "Album";
    }
}
