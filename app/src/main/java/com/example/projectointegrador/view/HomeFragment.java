package com.example.projectointegrador.view;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectointegrador.R;
import com.example.projectointegrador.controller.AlbumController;
import com.example.projectointegrador.controller.ArtistController;
import com.example.projectointegrador.controller.TrackController;
import com.example.projectointegrador.dao.AlbumDao;
import com.example.projectointegrador.dao.ArtistDao;
import com.example.projectointegrador.dao.TrackDao;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.view.adapter.AlbumAdapter;
import com.example.projectointegrador.view.adapter.ArtistAdapter;
import com.example.projectointegrador.view.adapter.RecomendadoAdapter;
import com.example.projectointegrador.view.adapter.UltimosReproducidosAdapter;

import java.util.List;


public class HomeFragment extends Fragment implements   RecomendadoAdapter.RecomendadoAdapterListener,
                                                        UltimosReproducidosAdapter.UltimosReproducidosAdapterListener {

    private List<Album> listaDeAlbums;
    private RecyclerView recyclerViewAlbums;
    private List<Track> listaDeRecomendados;
    private RecyclerView recyclerViewRecomendados;
    private List<Track> listaDeUltimasReproducciones;
    private RecyclerView recyclerViewUltimasReproducciones;
    private List<Artist> listaDeArtistas;
    private RecyclerView recyclerViewArtists;
    private FragmentHomeListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listaDeAlbums = AlbumDao.getAlbums();
        listaDeArtistas = ArtistDao.getArtists();
        listaDeRecomendados = TrackDao.getRecomendados();
        listaDeUltimasReproducciones = TrackDao.getUltimosReproducidos();
        setFindViewByIds(view);

        LinearLayoutManager linearLayoutManagerAlbum = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManagerRecomendado = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManagerUltimasReproducciones= new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManagerArtist = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);

        //AlbumAdapter albumAdapter = new AlbumAdapter(listaDeAlbums);
        //ArtistAdapter artistAdapter = new ArtistAdapter(listaDeArtistas);
        //RecomendadoAdapter recomendadoAdapter = new RecomendadoAdapter(listaDeRecomendados,this);
        UltimosReproducidosAdapter ultimosReproducidosAdapter = new UltimosReproducidosAdapter(listaDeUltimasReproducciones,this);

        TrackController trackController = new TrackController();
        trackController.getTracks(getContext(), new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                RecomendadoAdapter recomendadoAdapter = new RecomendadoAdapter(resultado,HomeFragment.this);
                recyclerViewRecomendados.setAdapter(recomendadoAdapter);
            }
        });
        ArtistController artistController = new ArtistController();
        artistController.getArtists(getContext(), new ResultListener<List<Artist>>() {
            @Override
            public void finish(List<Artist> resultado) {
                ArtistAdapter artistAdapter = new ArtistAdapter(resultado);
                recyclerViewArtists.setAdapter(artistAdapter);
            }
        });
        AlbumController albumController = new AlbumController();
        albumController.getAlbums(getContext(), new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                AlbumAdapter albumAdapter = new AlbumAdapter(resultado);
                recyclerViewAlbums.setAdapter(albumAdapter);
            }
        });


        recyclerViewArtists.setLayoutManager(linearLayoutManagerArtist);
        recyclerViewRecomendados.setLayoutManager(linearLayoutManagerRecomendado);
        recyclerViewUltimasReproducciones.setLayoutManager(linearLayoutManagerUltimasReproducciones);
        recyclerViewAlbums.setLayoutManager(linearLayoutManagerAlbum);

        //recyclerViewAlbums.setAdapter(albumAdapter);
        recyclerViewUltimasReproducciones.setAdapter(ultimosReproducidosAdapter);
        //recyclerViewRecomendados.setAdapter(recomendadoAdapter);
        //recyclerViewArtists.setAdapter(artistAdapter);

        return view;
    }

    private void setFindViewByIds(View view) {
        recyclerViewAlbums = view.findViewById(R.id.fargmentHome_RecyclerDeAlbumes);
        recyclerViewArtists = view.findViewById(R.id.fargmentHome_RecyclerDeArtistas);
        recyclerViewRecomendados = view.findViewById(R.id.fargmentHome_RecyclerDeRecomendados);
        recyclerViewUltimasReproducciones = view.findViewById(R.id.fargmentHome_RecyclerDeUltimasReproducciones);
    }

    @Override
    public void adapterRecomendadoOnClickRecomendados(Track track, List<Track> trackList) {
        listener.fragmentOnClickRecomendados(track, trackList);
    }

    @Override
    public void onClickUltimosReproducidos(Track track, List<Track> trackList) {
        listener.fragmentOnClickUltimosReproducidos(track, trackList);
    }

    public interface FragmentHomeListener {
        void fragmentOnClickRecomendados(Track track, List<Track> trackList);
        void fragmentOnClickUltimosReproducidos(Track track, List<Track> trackList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentHomeListener) {
            listener = (FragmentHomeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentHomeListener");
        }
    }

}
