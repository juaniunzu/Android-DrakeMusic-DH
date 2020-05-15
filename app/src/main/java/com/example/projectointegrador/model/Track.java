package com.example.projectointegrador.model;

import java.io.Serializable;

public class Track implements Serializable {
    private Integer id;
    private String title;
    private Integer duration;
    private Artist artist;
    private Album album;

    public Track(Integer id, String title, Integer duration, Artist artist, Album album) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.album = album;
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
}
