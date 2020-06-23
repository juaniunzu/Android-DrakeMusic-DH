package com.example.projectointegrador.controller;

import android.content.Context;


import com.example.projectointegrador.R;
import com.example.projectointegrador.dao.ArtistApiDao;
import com.example.projectointegrador.dao.ArtistDao;
import com.example.projectointegrador.dao.ArtistFirestoreDao;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.service.ResponseArtist;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ArtistController {
    public void getArtists(Context context, final ResultListener<List<Artist>> listenerDeLaVista) {
        boolean hayInternet = Utils.hayInternet(context);
        if (hayInternet) {
            ArtistApiDao artistApiDao = new ArtistApiDao();
            artistApiDao.getArtists(new ResultListener<List<Artist>>() {
                @Override
                public void finish(List<Artist> resultado) {
                    listenerDeLaVista.finish(resultado);
                }
            });
        } else {
            listenerDeLaVista.finish(ArtistDao.getArtists());
        }
    }

    public void buscarArtistas(Context context, String busqueda, final ResultListener<ResponseArtist> listener) {
        boolean hayInternet = Utils.hayInternet(context);
        if (hayInternet) {
            ArtistApiDao artistApiDao = new ArtistApiDao();
            artistApiDao.buscarArtistas(busqueda, new ResultListener<ResponseArtist>() {
                @Override
                public void finish(ResponseArtist resultado) {
                    listener.finish(resultado);
                }
            });
        }
    }

    public void agregarArtistAFavoritos(Artist artist, FirebaseUser firebaseUser, final ResultListener<Artist> listener) {
        ArtistFirestoreDao artistFirestoreDao = new ArtistFirestoreDao();
        artistFirestoreDao.agregarArtistAFavoritos(artist, firebaseUser, new ResultListener<Artist>() {
            @Override
            public void finish(Artist resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void getArtistListFavoritos(FirebaseUser firebaseUser, final ResultListener<List<Artist>> listener) {
        ArtistFirestoreDao artistFirestoreDao = new ArtistFirestoreDao();
        artistFirestoreDao.getArtistListFavoritos(firebaseUser, new ResultListener<List<Artist>>() {
            @Override
            public void finish(List<Artist> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void searchArtistFavoritos(Artist artist, FirebaseUser firebaseUser, final ResultListener<List<Artist>> listener){
        ArtistFirestoreDao artistFirestoreDao = new ArtistFirestoreDao();
        artistFirestoreDao.searchArtistFavoritos(artist, firebaseUser, new ResultListener<List<Artist>>() {
            @Override
            public void finish(List<Artist> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void eliminarArtistFavoritos(Artist artist, FirebaseUser firebaseUser, final ResultListener<Artist> listener){
        ArtistFirestoreDao artistFirestoreDao = new ArtistFirestoreDao();
        artistFirestoreDao.eliminarArtistFavoritos(artist, firebaseUser, new ResultListener<Artist>() {
            @Override
            public void finish(Artist resultado) {
                listener.finish(resultado);
            }
        });
    }


}
