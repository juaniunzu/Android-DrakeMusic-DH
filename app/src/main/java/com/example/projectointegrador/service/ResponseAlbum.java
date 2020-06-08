package com.example.projectointegrador.service;

import com.example.projectointegrador.model.Album;

import java.util.List;

public class ResponseAlbum {
    private List<Album> data;

    public ResponseAlbum() {
    }

    public List<Album> getAlbumes(){
        return data;
    }
}
