package com.example.projectointegrador.controller;

import android.content.Context;

import com.example.projectointegrador.dao.TrackApiDao;
import com.example.projectointegrador.dao.TrackFirestoreDao;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.service.ResponseTrack;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class TrackController {

    private TrackApiDao trackApiDao;
    private TrackFirestoreDao trackFirestoreDao;

    public TrackController() {
        this.trackApiDao = new TrackApiDao();
        this.trackFirestoreDao = new TrackFirestoreDao();
    }

    public void getTracks(Context context, final ResultListener<List<Track>> listenerDeLaVista) {
        boolean hayInternet = Utils.hayInternet(context);
        if (hayInternet) {
            trackApiDao.getTracks(new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                    listenerDeLaVista.finish(resultado);
                }
            });
        } else {
            //TODO ROOM
        }
    }

    public void getTop5TracksDeUnArtistaPorId(Integer idDelArtista, Context context, final ResultListener<List<Track>> listenerDeLaVista) {
        boolean hayInternet = Utils.hayInternet(context);
        if (hayInternet) {
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
            trackApiDao.getTracksDeUnAlbumPorId(idDelAlbum, new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                    listenerDeLaVista.finish(resultado);
                }
            });
        }
    }

    public void buscarTracks(Context context, String busqueda, String limit, final ResultListener<ResponseTrack> listener){
        boolean hayInternet = Utils.hayInternet(context);
        if (hayInternet){
            trackApiDao.buscarTracks(busqueda, limit, new ResultListener<ResponseTrack>() {
                @Override
                public void finish(ResponseTrack resultado) {
                    listener.finish(resultado);
                }
            });
        }
    }

    public void agregarTrackAFavoritos(Track track, FirebaseUser firebaseUser, final ResultListener<Track> listener){
        trackFirestoreDao.agregarTrackAFavoritos(track, firebaseUser, new ResultListener<Track>() {
            @Override
            public void finish(Track resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void getTrackListFavoritos(FirebaseUser firebaseUser, final ResultListener<List<Track>> listener){
        trackFirestoreDao.getTrackListFavoritos(firebaseUser, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void agregarTrackAUltimosReproducidos(Track track, FirebaseUser firebaseUser, final ResultListener<Track> listener){
        trackFirestoreDao.agregarTrackAUltimosReproducidos(track, firebaseUser, new ResultListener<Track>() {
            @Override
            public void finish(Track resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void getUltimosReproducidos(FirebaseUser firebaseUser, final ResultListener<List<Track>> listener){
        trackFirestoreDao.getUltimosReproducidos(firebaseUser, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void searchTrackFavoritos(Track track, FirebaseUser firebaseUser, final ResultListener<List<Track>> listener){
        trackFirestoreDao.searchTrackFavoritos(track, firebaseUser, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void eliminarTrackFavoritos (final Track track, FirebaseUser firebaseUser, final ResultListener<Track> listener){
        trackFirestoreDao.eliminarTrackFavoritos(track, firebaseUser, new ResultListener<Track>() {
            @Override
            public void finish(Track resultado) {
                listener.finish(track);
            }
        });
    }

}
