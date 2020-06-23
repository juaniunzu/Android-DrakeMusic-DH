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

    private AlbumApiDao albumApiDao;
    private AlbumFirestoreDao albumFirestoreDao;

    public AlbumController() {
        this.albumApiDao = new AlbumApiDao();
        this.albumFirestoreDao = new AlbumFirestoreDao();
    }

    public void getAlbums(Context context, final ResultListener<List<Album>> listenerDeLaVista){
        boolean hayInternet = Utils.hayInternet(context);
        if(hayInternet){
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
            albumApiDao.buscarAlbumes(busqueda, new ResultListener<ResponseAlbum>() {
                @Override
                public void finish(ResponseAlbum resultado) {
                    listener.finish(resultado);
                }
            });
        }
    }

    public void agregarAlbumAFavoritos(Album album, FirebaseUser firebaseUser, final ResultListener<Album> listener){
        albumFirestoreDao.agregarAlbumAFavoritos(album, firebaseUser, new ResultListener<Album>() {
            @Override
            public void finish(Album resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void getAlbumListFavoritos(FirebaseUser firebaseUser, final ResultListener<List<Album>> listener){
        albumFirestoreDao.getAlbumListFavoritos(firebaseUser, new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void searchAlbumFavoritos(Album album, FirebaseUser firebaseUser, final ResultListener<List<Album>> listener){
        albumFirestoreDao.searchAlbumFavoritos(album, firebaseUser, new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void eliminarAlbumFavoritos(final Album album, FirebaseUser firebaseUser, final ResultListener<Album> listener){
        albumFirestoreDao.eliminarAlbumFavoritos(album, firebaseUser, new ResultListener<Album>() {
            @Override
            public void finish(Album resultado) {
                listener.finish(resultado);
            }
        });
    }
}
