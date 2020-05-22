package com.example.projectointegrador.service;

import com.example.projectointegrador.model.Track;

import java.util.List;

public class ResponseTrack {
    private List<Track> data;

    public ResponseTrack(){
    }

    public List<Track> getTracks() {
        return data;
    }
}
