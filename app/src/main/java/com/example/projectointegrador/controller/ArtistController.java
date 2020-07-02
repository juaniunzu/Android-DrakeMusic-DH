package com.example.projectointegrador.controller;

import android.content.Context;


import com.example.projectointegrador.dao.ArtistApiDao;
import com.example.projectointegrador.dao.ArtistFirestoreDao;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.service.ResponseArtist;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ArtistController {

    private ArtistApiDao artistApiDao;
    private ArtistFirestoreDao artistFirestoreDao;

    public ArtistController() {
        this.artistApiDao = new ArtistApiDao();
        this.artistFirestoreDao = new ArtistFirestoreDao();
    }

    public void getArtists(Context context, final ResultListener<List<Artist>> listenerDeLaVista) {
        boolean hayInternet = Utils.hayInternet(context);
        if (hayInternet) {
            artistApiDao.getArtists(new ResultListener<List<Artist>>() {
                @Override
                public void finish(List<Artist> resultado) {
                    listenerDeLaVista.finish(resultado);
                }
            });
        } else {
            //TODO ROOM
        }
    }

    public void buscarArtistas(Context context, String busqueda, String limit, final ResultListener<ResponseArtist> listener) {
        boolean hayInternet = Utils.hayInternet(context);
        if (hayInternet) {
            artistApiDao.buscarArtistas(busqueda, limit, new ResultListener<ResponseArtist>() {
                @Override
                public void finish(ResponseArtist resultado) {
                    listener.finish(resultado);
                }
            });
        }
    }

    public void agregarArtistAFavoritos(Artist artist, FirebaseUser firebaseUser, final ResultListener<Artist> listener) {
        artistFirestoreDao.agregarArtistAFavoritos(artist, firebaseUser, new ResultListener<Artist>() {
            @Override
            public void finish(Artist resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void getArtistListFavoritos(FirebaseUser firebaseUser, final ResultListener<List<Artist>> listener) {
        artistFirestoreDao.getArtistListFavoritos(firebaseUser, new ResultListener<List<Artist>>() {
            @Override
            public void finish(List<Artist> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void searchArtistFavoritos(Artist artist, FirebaseUser firebaseUser, final ResultListener<List<Artist>> listener){
        artistFirestoreDao.searchArtistFavoritos(artist, firebaseUser, new ResultListener<List<Artist>>() {
            @Override
            public void finish(List<Artist> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void eliminarArtistFavoritos(Artist artist, FirebaseUser firebaseUser, final ResultListener<Artist> listener){
        artistFirestoreDao.eliminarArtistFavoritos(artist, firebaseUser, new ResultListener<Artist>() {
            @Override
            public void finish(Artist resultado) {
                listener.finish(resultado);
            }
        });
    }


}
