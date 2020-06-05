package com.example.projectointegrador.dao;

import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.service.ResponseTrack;
import com.example.projectointegrador.service.TrackService;
import com.example.projectointegrador.util.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackApiDao extends DaoHelper {

    private TrackService trackService;

    public TrackApiDao() {
        super();
        trackService = retrofit.create(TrackService.class);
    }
    public void getTracks(final ResultListener<List<Track>> listenerDelControler){
        trackService.obtenerTop10Tracks().enqueue(new Callback<ResponseTrack>() {
            @Override
            public void onResponse(Call<ResponseTrack> call, Response<ResponseTrack> response) {
                listenerDelControler.finish(response.body().getTracks());
            }

            @Override
            public void onFailure(Call<ResponseTrack> call, Throwable t) {
                t.toString();
            }
        });
    }
    public void getTop5TracksDeUnArtista(Integer idDelArtista, final ResultListener<List<Track>> listenerDelControler){
        trackService.obtenerTop5TracksDeUnArtista(idDelArtista).enqueue(new Callback<ResponseTrack>() {
            @Override
            public void onResponse(Call<ResponseTrack> call, Response<ResponseTrack> response) {
                listenerDelControler.finish(response.body().getTracks());
            }

            @Override
            public void onFailure(Call<ResponseTrack> call, Throwable t) {
            }
        });
    }
}
