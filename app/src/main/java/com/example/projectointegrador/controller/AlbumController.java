package com.example.projectointegrador.controller;

import android.content.Context;

import com.example.projectointegrador.dao.AlbumApiDao;
import com.example.projectointegrador.dao.AlbumDao;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;

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
}
