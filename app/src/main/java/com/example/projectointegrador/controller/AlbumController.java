package com.example.projectointegrador.controller;

import android.content.Context;

import com.example.projectointegrador.dao.AlbumApiDao;
import com.example.projectointegrador.dao.AlbumDao;
import com.example.projectointegrador.dao.AlbumFirestoreDao;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.service.ResponseAlbum;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AlbumController {
    public void getAlbums(Context context, final ResultListener<List<Album>> listenerDeLaVista){
        boolean hayInternet = Utils.hayInternet(context);
        if(hayInternet){
            AlbumApiDao albumApiDao = new AlbumApiDao();
            albumApiDao.getAlbums(new ResultListener<List<Album>>() {
                @Override
                public void finish(List<Album> resultado) {
                    listenerDeLaVista.finish(resultado);
                }
            });
        }
        else {
            listenerDeLaVista.finish(AlbumDao.getAlbums());
        }
    }
    public void getAlbumesDeUnArtista(Integer idDelArtista, Context context, final ResultListener<List<Album>>listenerDeLaVista){
        boolean hayInternet = Utils.hayInternet(context);
        if(hayInternet){
            AlbumApiDao albumApiDao = new AlbumApiDao();
            albumApiDao.getAlbumesDeUnArtista(idDelArtista, new ResultListener<List<Album>>() {
                @Override
                public void finish(List<Album> resultado) {
                    listenerDeLaVista.finish(resultado);
                }
            });
        }
    }

    public void buscarAlbumes(Context context, String busqueda, final ResultListener<ResponseAlbum> listener){
        boolean hayInternet = Utils.hayInternet(context);
        if(hayInternet){
            AlbumApiDao albumApiDao = new AlbumApiDao();
            albumApiDao.buscarAlbumes(busqueda, new ResultListener<ResponseAlbum>() {
                @Override
                public void finish(ResponseAlbum resultado) {
                    listener.finish(resultado);
                }
            });
        }
    }

    public void agregarAlbumAFavoritos(Album album, FirebaseUser firebaseUser, final ResultListener<Album> listener){
        AlbumFirestoreDao albumFirestoreDao = new AlbumFirestoreDao();
        albumFirestoreDao.agregarAlbumAFavoritos(album, firebaseUser, new ResultListener<Album>() {
            @Override
            public void finish(Album resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void getAlbumListFavoritos(FirebaseUser firebaseUser, final ResultListener<List<Album>> listener){
        AlbumFirestoreDao albumFirestoreDao = new AlbumFirestoreDao();
        albumFirestoreDao.getAlbumListFavoritos(firebaseUser, new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                listener.finish(resultado);
            }
        });
    }
}
