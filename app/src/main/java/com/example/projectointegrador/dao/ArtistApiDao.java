package com.example.projectointegrador.dao;

import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.service.ArtistService;
import com.example.projectointegrador.service.ResponseArtist;
import com.example.projectointegrador.util.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistApiDao extends DaoHelper {
    private ArtistService artistService;

    public ArtistApiDao(){
        super();
        artistService = retrofit.create(ArtistService.class);
    }
    public void getArtists(final ResultListener<List<Artist>> listenerDelController){
        artistService.obtenerTop10Artistas().enqueue(new Callback<ResponseArtist>() {
            @Override
            public void onResponse(Call<ResponseArtist> call, Response<ResponseArtist> response) {
                listenerDelController.finish(response.body().getArtistas());
            }

            @Override
            public void onFailure(Call<ResponseArtist> call, Throwable t) {
                t.toString();
            }
        });
    }
}
