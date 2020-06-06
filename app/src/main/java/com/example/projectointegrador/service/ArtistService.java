package com.example.projectointegrador.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArtistService {

    @GET("chart/0/artists")
    Call<ResponseArtist> obtenerTop10Artistas();

    @GET("search/artist?q={busqueda}")
    Call<ResponseArtist> buscarArtistas(@Path("busqueda") String busqueda);
}