package com.example.projectointegrador.model;

public class Album {
    private Integer id;
    private String title;
    private Integer cover;

    public Album(Integer id, String title, Integer cover) {
        this.id = id;
        this.title = title;
        this.cover = cover;
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

    public Integer getCover() {
        return cover;
    }

    public void setCover(Integer cover) {
        this.cover = cover;
    }
}
