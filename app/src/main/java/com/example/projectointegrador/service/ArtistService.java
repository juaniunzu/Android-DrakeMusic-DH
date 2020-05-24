package com.example.projectointegrador.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ArtistService {

    @GET("chart/0/artists")
    Call<ResponseArtist> obtenerTop10Artistas();
}