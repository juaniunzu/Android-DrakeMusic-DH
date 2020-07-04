package com.example.projectointegrador.controller;

import android.content.Context;

import com.example.projectointegrador.dao.AlbumApiDao;
import com.example.projectointegrador.dao.AlbumFirestoreDao;
import com.example.projectointegrador.dao.ArtistApiDao;
import com.example.projectointegrador.dao.ArtistFirestoreDao;
import com.example.projectointegrador.dao.HistorialFirestoreDao;
import com.example.projectointegrador.dao.TrackApiDao;
import com.example.projectointegrador.dao.TrackFirestoreDao;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Busqueda;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.service.ResponseAlbum;
import com.example.projectointegrador.service.ResponseArtist;
import com.example.projectointegrador.service.ResponseTrack;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class Controller {

    private AlbumApiDao albumApiDao;
    private AlbumFirestoreDao albumFirestoreDao;
    private ArtistApiDao artistApiDao;
    private ArtistFirestoreDao artistFirestoreDao;
    private HistorialFirestoreDao historialFirestoreDao;
    private TrackApiDao trackApiDao;
    private TrackFirestoreDao trackFirestoreDao;

    public Controller() {
        this.albumApiDao = new AlbumApiDao();
        this.albumFirestoreDao = new AlbumFirestoreDao();
        this.artistApiDao = new ArtistApiDao();
        this.artistFirestoreDao = new ArtistFirestoreDao();
        this.historialFirestoreDao = new HistorialFirestoreDao();
        this.trackApiDao = new TrackApiDao();
        this.trackFirestoreDao = new TrackFirestoreDao();
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
            //TODO ROOM
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

    public void buscarAlbumes(Context context, String busqueda, String limit, final ResultListener<ResponseAlbum> listener){
        boolean hayInternet = Utils.hayInternet(context);
        if(hayInternet){
            albumApiDao.buscarAlbumes(busqueda, limit, new ResultListener<ResponseAlbum>() {
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



    public void agregarBusquedaAlHistorial (Busqueda busqueda, FirebaseUser firebaseUser, ResultListener<Busqueda> listener){
        historialFirestoreDao.agregarBusquedaAlHistorial(busqueda, firebaseUser, new ResultListener<Busqueda>() {
            @Override
            public void finish(Busqueda resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void getHistorial (FirebaseUser firebaseUser, ResultListener<List<Busqueda>> listener){
        historialFirestoreDao.getHistorial(firebaseUser, new ResultListener<List<Busqueda>>() {
            @Override
            public void finish(List<Busqueda> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void borrarHistorial(FirebaseUser firebaseUser, Context context){
        historialFirestoreDao.borrarHistorial(firebaseUser, context);
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
