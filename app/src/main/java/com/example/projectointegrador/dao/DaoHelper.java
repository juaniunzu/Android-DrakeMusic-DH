package com.example.projectointegrador.dao;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Este Helper es para ser Reutilizado.
 * Se usa retrofit builder y tiene la URL base de la API, Para hacer diferentes Daos y Services.
 */

public abstract class DaoHelper {
    public Retrofit retrofit;
    private static final String BASE_URL = "https://api.deezer.com/";

    public DaoHelper() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
