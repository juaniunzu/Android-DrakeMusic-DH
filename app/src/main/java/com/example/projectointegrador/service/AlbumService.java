package com.example.projectointegrador.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlbumService {

    @GET("chart/0/albums")
    Call<ResponseAlbum> obtenerTop10Albumes();
}
