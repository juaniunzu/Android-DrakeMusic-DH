package com.example.projectointegrador.model;

import java.io.Serializable;

public class Artist implements Serializable {
    private Integer id;
    private String name;
    private Integer picture;

    public Artist(Integer id, String name,Integer picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    public Integer getPicture() {
        return picture;
    }

    public void setPicture(Integer picture) {
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
}
