package com.example.projectointegrador.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArtistService {

    @GET("chart/0/artists")
    Call<ResponseArtist> obtenerTop10Artistas();

    @GET("search/artist")
    Call<ResponseArtist> buscarArtistas(@Query("q") String busqueda);
}