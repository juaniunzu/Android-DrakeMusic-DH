package com.example.projectointegrador.controller;

import android.content.Context;

import com.example.projectointegrador.dao.TrackApiDao;
import com.example.projectointegrador.dao.TrackDao;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;

import java.util.List;

public class TrackController {
    public void getTracks(Context context, final ResultListener<List<Track>> listenerDeLaVista) {
        boolean hayInternet = Utils.hayInternet(context);
        if (hayInternet) {
            TrackApiDao trackApiDao = new TrackApiDao();
            trackApiDao.getTracks(new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                    listenerDeLaVista.finish(resultado);
                }
            });
        } else {
            listenerDeLaVista.finish(TrackDao.getRecomendados());
        }
    }

    public void getTop5TracksDeUnArtistaPorId(Integer idDelArtista, Context context, final ResultListener<List<Track>> listenerDeLaVista) {
        boolean hayInternet = Utils.hayInternet(context);
        if (hayInternet) {
            TrackApiDao trackApiDao = new TrackApiDao();
            trackApiDao.getTop5TracksDeUnArtista(idDelArtista, new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                listenerDeLaVista.finish(resultado);
                }
            });
        }
    }
    public void getTracksDeUnAlbumPorId(Integer idDelAlbum,Context context, final ResultListener<List<Track>> listenerDeLaVista){
        boolean hayInternet = Utils.hayInternet(context);
        if(hayInternet){
            TrackApiDao trackApiDao = new TrackApiDao();
            trackApiDao.getTracksDeUnAlbumPorId(idDelAlbum, new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                    listenerDeLaVista.finish(resultado);
                }
            });
        }
    }
}
