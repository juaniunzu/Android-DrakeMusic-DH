package com.example.projectointegrador.service;

import com.example.projectointegrador.model.Track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TrackService {

    @GET("chart/0/tracks")
    Call<ResponseTrack> obtenerTop10Tracks();

    @GET("search/track?q={busqueda}")
    Call<ResponseTrack> buscarTracks(@Path("busqueda") String busqueda);
}
