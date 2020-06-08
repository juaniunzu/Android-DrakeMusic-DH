package com.example.projectointegrador.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.projectointegrador.R;
import com.example.projectointegrador.controller.AlbumController;
import com.example.projectointegrador.controller.ArtistController;
import com.example.projectointegrador.controller.TrackController;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.service.ResponseAlbum;
import com.example.projectointegrador.service.ResponseArtist;
import com.example.projectointegrador.service.ResponseTrack;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.view.adapter.AlbumSearchAdapter;
import com.example.projectointegrador.view.adapter.ArtistSearchAdapter;
import com.example.projectointegrador.view.adapter.TrackSearchAdapter;

import java.util.List;

import static com.example.projectointegrador.view.SearchDetailFragment.TYPE_ALBUM;
import static com.example.projectointegrador.view.SearchDetailFragment.TYPE_ARTIST;
import static com.example.projectointegrador.view.SearchDetailFragment.TYPE_TRACK;


public class SearchInputFragment extends Fragment implements
                                                    AlbumSearchAdapter.AlbumSearchAdapterListener,
                                                    ArtistSearchAdapter.ArtistSearchAdapterListener,
                                                    TrackSearchAdapter.TrackSearchAdapterListener {

    //declarar atributos editText (o searchview), recyclerview y los tres textview
    private SearchView searchView;
    private TextView tvTracks;
    private TextView tvAlbums;
    private TextView tvArtists;
    private RecyclerView rvTracks;
    private RecyclerView rvAlbums;
    private RecyclerView rvArtists;
    private String query;
    private SearchInputFragmentListener listener;

    public SearchInputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (SearchInputFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //linkear los atributos y ponerle onClickListener a los tres textview,
        //los tres listener hacen lo mismo: crean y pegan un SearchDetailFragment y le pasan
        //la data de lo que está escrito en el editText (o searchview) como parametro en el constructor.
        //Segun cual de los tres textview se clickee, el SearchDetailFragment creado tiene que rellenar su lista con una
        //query del tipo correspondiente.

        View view = inflater.inflate(R.layout.fragment_search_input, container, false);


        //TODO USAR BINDING!!!!!
        searchView = view.findViewById(R.id.fragmentSearchInputSearchView);
        tvTracks = view.findViewById(R.id.fragmentSearchInputTextViewTracks);
        tvAlbums = view.findViewById(R.id.fragmentSearchInputTextViewAlbums);
        tvArtists = view.findViewById(R.id.fragmentSearchInputTextViewArtistas);
        rvTracks = view.findViewById(R.id.fragmentSearchInputRecyclerViewTracks);
        rvAlbums = view.findViewById(R.id.fragmentSearchInputRecyclerViewAlbums);
        rvArtists = view.findViewById(R.id.fragmentSearchInputRecyclerViewArtists);

        tvTracks.setVisibility(View.GONE);
        tvAlbums.setVisibility(View.GONE);
        tvArtists.setVisibility(View.GONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            AlbumController albumController = new AlbumController();
            ArtistController artistController = new ArtistController();
            TrackController trackController = new TrackController();

            @Override
            public boolean onQueryTextSubmit(String query) {
                //metodo para obtener la query que introduce el usuario, solo actualiza query
                SearchInputFragment.this.query = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //crear un controller de cada tipo que busque y muestre 2 resultados, luego en el
                //onFinish del controller crear y setear adapters y layoutmanagers
                query = newText;
                tvAlbums.setVisibility(View.VISIBLE);
                tvArtists.setVisibility(View.VISIBLE);
                tvTracks.setVisibility(View.VISIBLE);

                    albumController.buscarAlbumes(getContext(), newText, new ResultListener<ResponseAlbum>() {
                        @Override
                        public void finish(ResponseAlbum resultado) {
                            AlbumSearchAdapter albumSearchAdapter = new AlbumSearchAdapter(resultado.getAlbumes(), false, SearchInputFragment.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
                            rvAlbums.setLayoutManager(linearLayoutManager);
                            rvAlbums.setAdapter(albumSearchAdapter);
                        }
                    });

                    artistController.buscarArtistas(getContext(), searchView.getQuery().toString(), new ResultListener<ResponseArtist>() {
                        @Override
                        public void finish(ResponseArtist resultado) {
                            ArtistSearchAdapter artistSearchAdapter = new ArtistSearchAdapter(resultado.getArtistas(), false, SearchInputFragment.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                            rvArtists.setAdapter(artistSearchAdapter);
                            rvArtists.setLayoutManager(linearLayoutManager);
                        }
                    });

                    trackController.buscarTracks(getContext(), searchView.getQuery().toString(), new ResultListener<ResponseTrack>() {
                        @Override
                        public void finish(ResponseTrack resultado) {
                            TrackSearchAdapter trackSearchAdapter = new TrackSearchAdapter(resultado.getTracks(), false, SearchInputFragment.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            rvTracks.setAdapter(trackSearchAdapter);
                            rvTracks.setLayoutManager(linearLayoutManager);
                        }
                    });
                return true;
            }
        });

        tvArtists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (query.length() != 0){
                    String type = TYPE_ARTIST;
                    listener.onClickFiltroVerTodo(query, type);
                }
            }
        });

        tvAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (query.length() != 0){
                    String type = TYPE_ALBUM;
                    listener.onClickFiltroVerTodo(query, type);
                }
            }
        });

        tvTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (query.length() != 0){
                    String type = TYPE_TRACK;
                    listener.onClickFiltroVerTodo(query, type);
                }
            }
        });

        return view;
    }

    @Override
    public void onClickAlbumSearchAdapter(Album album) {
        listener.onClickAlbumSearchInputFragment(album);
    }

    @Override
    public void onClickArtistSearchAdapter(Artist artist) {
        listener.onClickArtistSearchInputFragment(artist);
    }

    @Override
    public void onClickTrackSearchAdapter(Track track, List<Track> trackList) {
        listener.onClickTrackSearchInputFragment(track, trackList);
    }

    public interface SearchInputFragmentListener {
        void onClickFiltroVerTodo(String query, String type);
        void onClickAlbumSearchInputFragment (Album album);
        void onClickArtistSearchInputFragment (Artist artist);
        void onClickTrackSearchInputFragment (Track track, List<Track> trackList);
    }
}