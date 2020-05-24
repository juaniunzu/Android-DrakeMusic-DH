package com.example.projectointegrador.controller;

import android.content.Context;

import com.example.projectointegrador.dao.ArtistApiDao;
import com.example.projectointegrador.dao.ArtistDao;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;

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
        }else{
            listenerDeLaVista.finish(ArtistDao.getArtists());
        }
    }
}
