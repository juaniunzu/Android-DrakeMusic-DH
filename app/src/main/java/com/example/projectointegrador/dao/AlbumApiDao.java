package com.example.projectointegrador.dao;

import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.service.AlbumService;
import com.example.projectointegrador.service.ResponseAlbum;
import com.example.projectointegrador.util.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumApiDao extends DaoHelper {
    private AlbumService albumService;

    public AlbumApiDao (){
        super();
        albumService = retrofit.create(AlbumService.class);
    }
    public void getAlbums(final ResultListener<List<Album>> listenerDeController){
        albumService.obtenerTop10Albumes().enqueue(new Callback<ResponseAlbum>() {
            @Override
            public void onResponse(Call<ResponseAlbum> call, Response<ResponseAlbum> response) {
                listenerDeController.finish(response.body().getAlbumes());
            }

            @Override
            public void onFailure(Call<ResponseAlbum> call, Throwable t) {
                t.toString();
            }
        });
    }
    public void getAlbumesDeUnArtista(Integer idDelArtista,final ResultListener<List<Album>> listenerDeController){
        albumService.obtenerAlbumesDeUnArtista(idDelArtista).enqueue(new Callback<ResponseAlbum>() {
            @Override
            public void onResponse(Call<ResponseAlbum> call, Response<ResponseAlbum> response) {
                listenerDeController.finish(response.body().getAlbumes());
            }

            @Override
            public void onFailure(Call<ResponseAlbum> call, Throwable t) {

            }
        });
    }

    public void buscarAlbumes(String busqueda, final ResultListener<ResponseAlbum> resultListenerFromController){
        Call<ResponseAlbum> call = albumService.buscarAlbumes(busqueda);
        call.enqueue(new Callback<ResponseAlbum>() {
            @Override
            public void onResponse(Call<ResponseAlbum> call, Response<ResponseAlbum> response) {
                if (response.isSuccessful()) {
                    ResponseAlbum responseAlbum = response.body();
                    resultListenerFromController.finish(responseAlbum);
                }
            }

            @Override
            public void onFailure(Call<ResponseAlbum> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
