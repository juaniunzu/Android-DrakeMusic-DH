package com.example.projectointegrador.controller;

import android.content.Context;

import com.example.projectointegrador.dao.AlbumFirestoreDao;
import com.example.projectointegrador.dao.TrackApiDao;
import com.example.projectointegrador.dao.TrackDao;
import com.example.projectointegrador.dao.TrackFirestoreDao;
import com.example.projectointegrador.dao.UltimosReproducidosFirestoreDao;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.service.ResponseTrack;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;
import com.google.firebase.auth.FirebaseUser;

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

    public void buscarTracks(Context context, String busqueda, final ResultListener<ResponseTrack> listener){
        boolean hayInternet = Utils.hayInternet(context);
        if (hayInternet){
            TrackApiDao trackApiDao = new TrackApiDao();
            trackApiDao.buscarTracks(busqueda, new ResultListener<ResponseTrack>() {
                @Override
                public void finish(ResponseTrack resultado) {
                    listener.finish(resultado);
                }
            });
        }
    }

    public void agregarTrackAFavoritos(Track track, FirebaseUser firebaseUser, final ResultListener<Track> listener){
        TrackFirestoreDao trackFirestoreDao = new TrackFirestoreDao();
        trackFirestoreDao.agregarTrackAFavoritos(track, firebaseUser, new ResultListener<Track>() {
            @Override
            public void finish(Track resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void getTrackListFavoritos(FirebaseUser firebaseUser, final ResultListener<List<Track>> listener){
        TrackFirestoreDao trackFirestoreDao = new TrackFirestoreDao();
        trackFirestoreDao.getTrackListFavoritos(firebaseUser, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void agregarTrackAUltimosReproducidos(Track track, FirebaseUser firebaseUser, final ResultListener<Track> listener){
        UltimosReproducidosFirestoreDao ultimosReproducidosFirestoreDao = new UltimosReproducidosFirestoreDao();
        ultimosReproducidosFirestoreDao.agregarTrackAUltimosReproducidos(track, firebaseUser, new ResultListener<Track>() {
            @Override
            public void finish(Track resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void getUltimosReproducidos(FirebaseUser firebaseUser, final ResultListener<List<Track>> listener){
        UltimosReproducidosFirestoreDao ultimosReproducidosFirestoreDao = new UltimosReproducidosFirestoreDao();
        ultimosReproducidosFirestoreDao.getUltimosReproducidos(firebaseUser, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void searchTrackFavoritos(Track track, FirebaseUser firebaseUser, final ResultListener<List<Track>> listener){
        TrackFirestoreDao trackFirestoreDao = new TrackFirestoreDao();
        trackFirestoreDao.searchTrackFavoritos(track, firebaseUser, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                listener.finish(resultado);
            }
        });
    }

}
