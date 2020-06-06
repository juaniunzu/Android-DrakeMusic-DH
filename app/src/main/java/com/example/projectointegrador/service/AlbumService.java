package com.example.projectointegrador.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AlbumService {

    @GET("chart/0/albums")
    Call<ResponseAlbum> obtenerTop10Albumes();

    @GET("artist/{idArtista}/albums")
    Call<ResponseAlbum> obtenerAlbumesDeUnArtista(@Path("idArtista") Integer idArtista);

    @GET("search/album?q={busqueda}")
    Call<ResponseAlbum> buscarAlbumes(@Path("busqueda") String busqueda);
}
