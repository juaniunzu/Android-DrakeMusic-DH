package com.example.projectointegrador.service;

import com.example.projectointegrador.model.Track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TrackService {

    @GET("chart/0/tracks")
    Call<ResponseTrack> obtenerTop10Tracks();
}
