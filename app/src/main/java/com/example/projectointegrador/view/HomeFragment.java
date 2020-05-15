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
import com.example.projectointegrador.dao.AlbumDao;
import com.example.projectointegrador.dao.ArtistDao;
import com.example.projectointegrador.dao.TrackDao;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Track;

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
        recyclerViewAlbums = view.findViewById(R.id.fargmentHome_RecyclerDeAlbumes);
        recyclerViewArtists = view.findViewById(R.id.fargmentHome_RecyclerDeArtistas);
        recyclerViewRecomendados = view.findViewById(R.id.fargmentHome_RecyclerDeRecomendados);
        recyclerViewUltimasReproducciones = view.findViewById(R.id.fargmentHome_RecyclerDeUltimasReproducciones);

        LinearLayoutManager linearLayoutManagerAlbum = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManagerRecomendado = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManagerUltimasReproducciones= new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManagerArtist = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);

        AlbumAdapter albumAdapter = new AlbumAdapter(listaDeAlbums);
        ArtistAdapter artistAdapter = new ArtistAdapter(listaDeArtistas);
        RecomendadoAdapter recomendadoAdapter = new RecomendadoAdapter(listaDeRecomendados,this);
        UltimosReproducidosAdapter ultimosReproducidosAdapter = new UltimosReproducidosAdapter(listaDeUltimasReproducciones,this);

        recyclerViewAlbums.setLayoutManager(linearLayoutManagerAlbum);
        recyclerViewAlbums.setAdapter(albumAdapter);
        recyclerViewUltimasReproducciones.setLayoutManager(linearLayoutManagerUltimasReproducciones);
        recyclerViewUltimasReproducciones.setAdapter(ultimosReproducidosAdapter);
        recyclerViewRecomendados.setLayoutManager(linearLayoutManagerRecomendado);
        recyclerViewRecomendados.setAdapter(recomendadoAdapter);
        recyclerViewArtists.setLayoutManager(linearLayoutManagerArtist);
        recyclerViewArtists.setAdapter(artistAdapter);


        return view;
    }

    @Override
    public void adapterRecomendadoOnClickRecomendados(Track track) {
        listener.fragmentOnClickRecomendados(track);
    }

    @Override
    public void onClickUltimosReproducidos(Track track) {
        listener.fragmentOnClickUltimosReproducidos(track);
    }

    public interface FragmentHomeListener {
        void fragmentOnClickRecomendados(Track track);
        void fragmentOnClickUltimosReproducidos(Track track);
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
