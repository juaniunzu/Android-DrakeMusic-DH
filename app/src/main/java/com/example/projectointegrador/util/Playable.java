package com.example.projectointegrador.util;

import com.example.projectointegrador.model.Track;

import java.util.ArrayList;

public interface Playable {
    void setPlayerTemaNuevo(Integer posicionNueva);
    void setPlayerInicio(ArrayList<Track> trackList);
    void onTrackPrevious();
    void onTrackPlay();
    void onTrackPause();
    void onTrackNext();
}
