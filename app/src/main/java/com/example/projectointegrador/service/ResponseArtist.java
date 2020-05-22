package com.example.projectointegrador.service;

import com.example.projectointegrador.model.Artist;

import java.util.List;

public class ResponseArtist {
    private List<Artist> data;

    public ResponseArtist() {
    }

    public List<Artist> getArtistas(){
        return data;
    }
}
