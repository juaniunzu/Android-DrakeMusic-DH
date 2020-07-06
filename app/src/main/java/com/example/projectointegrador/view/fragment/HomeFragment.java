package com.example.projectointegrador.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectointegrador.R;
import com.example.projectointegrador.controller.AlbumController;
import com.example.projectointegrador.controller.ArtistController;
import com.example.projectointegrador.controller.TrackController;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;
import com.example.projectointegrador.view.adapter.AlbumAdapter;
import com.example.projectointegrador.view.adapter.ArtistAdapter;
import com.example.projectointegrador.view.adapter.RecomendadoAdapter;
import com.example.projectointegrador.view.adapter.UltimosReproducidosAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
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
    private ShimmerFrameLayout shimmerRecomendados;
    private ShimmerFrameLayout shimmerUltimosReproducidos;
    private ShimmerFrameLayout shimmerArtistas;
    private ShimmerFrameLayout shimmerAlbumes;
    private TextView ultimosReproducidosPlaceholder;
    private TextView ultimosReproducidos;
    private TextView artistasPlaceholder;
    private TextView artistas;
    private TextView albumesPlaceholder;
    private TextView albumes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setFindViewByIds(view);

        recyclerViewRecomendados.setVisibility(View.INVISIBLE);
        shimmerRecomendados = view.findViewById(R.id.fragmentHomeShimmerRecomendados);
        shimmerRecomendados.startShimmer();








        shimmerArtistas = view.findViewById(R.id.fragmentHomeShimmerArtistas);
        shimmerArtistas.startShimmer();
        artistasPlaceholder = view.findViewById(R.id.fragmentHome_TextViewArtistasPlaceholder);
        artistas = view.findViewById(R.id.fragmentHome_TextViewArtistas);
        recyclerViewArtists.setVisibility(View.INVISIBLE);
        artistas.setVisibility(View.INVISIBLE);


        shimmerAlbumes = view.findViewById(R.id.fragmentHomeShimmerAlbumes);
        shimmerAlbumes.startShimmer();
        albumesPlaceholder = view.findViewById(R.id.fragmentHome_TextViewAlbumesPlaceholder);
        albumes = view.findViewById(R.id.fragmentHome_TextViewAlbumes);
        recyclerViewAlbums.setVisibility(View.INVISIBLE);
        albumes.setVisibility(View.INVISIBLE);

        LinearLayoutManager linearLayoutManagerAlbum = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManagerRecomendado = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManagerArtist = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerViewRecomendados.setLayoutManager(linearLayoutManagerRecomendado);
        TrackController trackController = new TrackController();
        if(Utils.hayInternet(getContext())){
            trackController.getTracks(getContext(), new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                    RecomendadoAdapter recomendadoAdapter = new RecomendadoAdapter(resultado,HomeFragment.this);
                    recyclerViewRecomendados.setAdapter(recomendadoAdapter);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shimmerRecomendados.stopShimmer();
                            shimmerRecomendados.setVisibility(View.INVISIBLE);
                            recyclerViewRecomendados.setVisibility(View.VISIBLE);
                        }
                    }, 1000);


                }
            });
        } else {
            listener.noHayInternetHomeFragment();
        }


        ultimosReproducidosPlaceholder = view.findViewById(R.id.fragmentHome_TextViewUltimasReproducidasPlaceholder);
        shimmerUltimosReproducidos = view.findViewById(R.id.fragmentHomeShimmerUltimasReproducidas);

        recyclerViewUltimasReproducciones.setVisibility(View.INVISIBLE);
        textViewUltimosRep.setVisibility(View.INVISIBLE);
        shimmerUltimosReproducidos.setVisibility(View.VISIBLE);
        ultimosReproducidosPlaceholder.setVisibility(View.VISIBLE);
        shimmerUltimosReproducidos.startShimmer();

        trackController.getUltimosReproducidos(firebaseUser, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                if (resultado.size() != 0){
                    UltimosReproducidosAdapter ultimosReproducidosAdapter = new UltimosReproducidosAdapter(resultado,HomeFragment.this);
                    LinearLayoutManager linearLayoutManagerUltimasReproducciones= new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                    recyclerViewUltimasReproducciones.setAdapter(ultimosReproducidosAdapter);
                    recyclerViewUltimasReproducciones.setLayoutManager(linearLayoutManagerUltimasReproducciones);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerViewUltimasReproducciones.setVisibility(View.VISIBLE);
                            textViewUltimosRep.setVisibility(View.VISIBLE);
                            shimmerUltimosReproducidos.stopShimmer();
                            shimmerUltimosReproducidos.setVisibility(View.INVISIBLE);
                            ultimosReproducidosPlaceholder.setVisibility(View.INVISIBLE);
                        }
                    }, 1200);

                } else {
                    shimmerUltimosReproducidos.stopShimmer();
                    shimmerUltimosReproducidos.setVisibility(View.GONE);
                    ultimosReproducidosPlaceholder.setVisibility(View.GONE);
                }
            }
        });

        recyclerViewArtists.setLayoutManager(linearLayoutManagerArtist);
        ArtistController artistController = new ArtistController();
        artistController.getArtists(getContext(), new ResultListener<List<Artist>>() {
            @Override
            public void finish(List<Artist> resultado) {
                ArtistAdapter artistAdapter = new ArtistAdapter(resultado,HomeFragment.this);
                recyclerViewArtists.setAdapter(artistAdapter);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shimmerArtistas.stopShimmer();
                        shimmerArtistas.setVisibility(View.INVISIBLE);
                        artistas.setVisibility(View.VISIBLE);
                        artistasPlaceholder.setVisibility(View.INVISIBLE);
                        recyclerViewArtists.setVisibility(View.VISIBLE);
                    }
                }, 1000);

            }
        });


        recyclerViewAlbums.setLayoutManager(linearLayoutManagerAlbum);
        AlbumController albumController = new AlbumController();
        albumController.getAlbums(getContext(), new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                AlbumAdapter albumAdapter = new AlbumAdapter(resultado,HomeFragment.this);
                recyclerViewAlbums.setAdapter(albumAdapter);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shimmerAlbumes.stopShimmer();
                        shimmerAlbumes.setVisibility(View.INVISIBLE);
                        albumes.setVisibility(View.VISIBLE);
                        albumesPlaceholder.setVisibility(View.INVISIBLE);
                        recyclerViewAlbums.setVisibility(View.VISIBLE);
                    }
                }, 1000);

            }
        });


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
        void noHayInternetHomeFragment();
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

    @Override
    public String toString() {
        return "1";
    }
}
