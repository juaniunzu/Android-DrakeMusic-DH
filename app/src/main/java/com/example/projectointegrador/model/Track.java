package com.example.projectointegrador.model;

import android.net.Uri;

import androidx.annotation.Nullable;

import com.example.projectointegrador.util.Utils;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Track implements Serializable, Utils.Searchable {
    private Integer id;
    private String title;
    private Integer duration;
    private Artist artist;
    private Album album;
    private String type;
    private String preview;
    @ServerTimestamp
    private Date date;

    public Track() {
    }


    //constructor para hardcodeados . Borrar mas adelante
    public Track(Integer id, String title, Integer duration, Artist artist, Album album) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.album = album;
    }



    public Track(Integer id, String title, Integer duration, Artist artist, Album album, String type, String preview) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.album = album;
        this.type = type;
        this.preview = preview;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(!(obj instanceof Track)) return false;
        Track trackAComparar = (Track) obj;
        return (trackAComparar.getId().equals(this.getId()));
    }

    @Override
    public String informarTitulo() {
        return getTitle();
    }

    @Override
    public String informarImagen() {
        return getAlbum().getCover();
    }

    @Override
    public String informarDescripcion() {
        return getAlbum().informarTitulo() + " - " + getArtist().informarTitulo();
    }
}
