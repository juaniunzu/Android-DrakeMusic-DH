package com.example.projectointegrador.view;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class HomeFragment extends Fragment implements   RecomendadoAdapter.RecomendadoAdapterListener,
                                                        UltimosReproducidosAdapter.UltimosReproducidosAdapterListener,
                                                        ArtistAdapter.ArtistAdapterListener,
                                                        AlbumAdapter.AlbumAdapterListener {

    private List<Album> listaDeAlbums;
    private RecyclerView recyclerViewAlbums;
    private List<Track> listaDeRecomendados;
    private RecyclerView recyclerViewRecomendados;
    private List<Track> listaDeUltimasReproducciones;
    private RecyclerView recyclerViewUltimasReproducciones;
    private List<Artist> listaDeArtistas;
    private RecyclerView recyclerViewArtists;
    private FragmentHomeListener listener;
    private FirebaseUser firebaseUser;
    private TextView textViewUltimosRep;
    private ImageView settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listaDeUltimasReproducciones = TrackDao.getUltimosReproducidos();
        setFindViewByIds(view);

        LinearLayoutManager linearLayoutManagerAlbum = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManagerRecomendado = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManagerArtist = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        TrackController trackController = new TrackController();
        trackController.getTracks(getContext(), new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                RecomendadoAdapter recomendadoAdapter = new RecomendadoAdapter(resultado,HomeFragment.this);
                recyclerViewRecomendados.setAdapter(recomendadoAdapter);
            }
        });

        recyclerViewUltimasReproducciones.setVisibility(View.GONE);
        textViewUltimosRep.setVisibility(View.GONE);

        trackController.getUltimosReproducidos(firebaseUser, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                if (resultado.size() != 0){
                    UltimosReproducidosAdapter ultimosReproducidosAdapter = new UltimosReproducidosAdapter(resultado,HomeFragment.this);
                    recyclerViewUltimasReproducciones.setAdapter(ultimosReproducidosAdapter);
                    LinearLayoutManager linearLayoutManagerUltimasReproducciones= new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                    recyclerViewUltimasReproducciones.setLayoutManager(linearLayoutManagerUltimasReproducciones);
                    recyclerViewUltimasReproducciones.setVisibility(View.VISIBLE);
                    textViewUltimosRep.setVisibility(View.VISIBLE);
                }
            }
        });
        ArtistController artistController = new ArtistController();
        artistController.getArtists(getContext(), new ResultListener<List<Artist>>() {
            @Override
            public void finish(List<Artist> resultado) {
                ArtistAdapter artistAdapter = new ArtistAdapter(resultado,HomeFragment.this);
                recyclerViewArtists.setAdapter(artistAdapter);
            }
        });
        AlbumController albumController = new AlbumController();
        albumController.getAlbums(getContext(), new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                AlbumAdapter albumAdapter = new AlbumAdapter(resultado,HomeFragment.this);
                recyclerViewAlbums.setAdapter(albumAdapter);
            }
        });


        recyclerViewArtists.setLayoutManager(linearLayoutManagerArtist);
        recyclerViewRecomendados.setLayoutManager(linearLayoutManagerRecomendado);
        recyclerViewAlbums.setLayoutManager(linearLayoutManagerAlbum);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickSettingsHomeFragment();
            }
        });

        return view;
    }

    private void setFindViewByIds(View view) {
        textViewUltimosRep = view.findViewById(R.id.fragmentHome_TextViewUltimasReproducidas);
        recyclerViewAlbums = view.findViewById(R.id.fargmentHome_RecyclerDeAlbumes);
        recyclerViewArtists = view.findViewById(R.id.fargmentHome_RecyclerDeArtistas);
        recyclerViewRecomendados = view.findViewById(R.id.fargmentHome_RecyclerDeRecomendados);
        recyclerViewUltimasReproducciones = view.findViewById(R.id.fargmentHome_RecyclerDeUltimasReproducciones);
        settings = view.findViewById(R.id.fragmentHome_ImageViewPerfil);
    }

    @Override
    public void onClickRecomendadoRecomendadoAdapter(Track track, List<Track> trackList) {
        listener.onClickRecomendadosDesdeHomeFragment(track, trackList);
    }

    @Override
    public void onClickUltimosReproducidosAdapter(Track track, List<Track> trackList) {
        listener.onClickUltimosReproducidosDesdeHomeFragment(track, trackList);
    }

    @Override
    public void onClickArtistaArtistAdapter(Artist artist) {
        listener.onClickArtistaDesdeHomeFragment(artist);
    }

    @Override
    public void onClickAlbumAlbumAdapter(Album album) {
        listener.onClickAlbumDesdeHomeFragment(album);
    }

    /**
     * Esta Interface es para que la implemente una Actividad para que conozca los listeners del Fragment. En este Caso la
     * Main Activity.
     */
    public interface FragmentHomeListener {
        void onClickRecomendadosDesdeHomeFragment(Track track, List<Track> trackList);
        void onClickUltimosReproducidosDesdeHomeFragment(Track track, List<Track> trackList);
        void onClickArtistaDesdeHomeFragment(Artist artist);
        void onClickAlbumDesdeHomeFragment(Album album);
        void onClickSettingsHomeFragment();
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
