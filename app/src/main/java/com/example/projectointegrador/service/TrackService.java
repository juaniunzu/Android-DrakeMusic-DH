package com.example.projectointegrador.service;

import com.example.projectointegrador.model.Track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrackService {

    @GET("chart/0/tracks")
    Call<ResponseTrack> obtenerTop10Tracks();

    @GET("search/track")
    Call<ResponseTrack> buscarTracks(@Query("q") String busqueda, @Query("limit") String limit);

    @GET("artist/{idArtista}/top")
    Call<ResponseTrack> obtenerTop5TracksDeUnArtista(@Path("idArtista") Integer idArtista);

    @GET("album/{idAlbum}/tracks")
    Call<ResponseTrack> obtenerTracksDeUnAlbum(@Path("idAlbum") Integer idAlbum);
}
