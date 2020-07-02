package com.example.projectointegrador.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectointegrador.R;
import com.example.projectointegrador.controller.AlbumController;
import com.example.projectointegrador.controller.ArtistController;
import com.example.projectointegrador.controller.TrackController;
import com.example.projectointegrador.databinding.FragmentSearchDetailBinding;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchDetailFragment extends Fragment implements AlbumSearchAdapter.AlbumSearchAdapterListener,
                                                    ArtistSearchAdapter.ArtistSearchAdapterListener,
                                                    TrackSearchAdapter.TrackSearchAdapterListener {

    public static final String KEY_QUERY = "query";
    public static final String KEY_TYPE = "type";
    public static final String TYPE_ARTIST = "artist";
    public static final String TYPE_ALBUM = "album";
    public static final String TYPE_TRACK = "track";

    private SearchDetailFragmentListener listener;

    private FragmentSearchDetailBinding binding;

    public SearchDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (SearchDetailFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //despues de linkear el textview, setearle el %s con el string atributo que se recibe del fragment anterior

        //la lista que rellene el recyclerview tiene que venir de una busqueda correspondiente
        //a track, album o artista
        //segun en cual de los tres textview anteriores de haya clickeado

        binding = FragmentSearchDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.fragmentSearchDetailRecyclerView.setVisibility(View.INVISIBLE);
        binding.fragmentSearchDetailShimmer.startShimmer();


        Bundle bundle = getArguments();
        String query = bundle.getString(KEY_QUERY);

        binding.fragmentSearchDetailTextView.setText(String.format(getString(R.string.en_busqueda), query));

        switch (bundle.getString(KEY_TYPE)){

            case TYPE_ALBUM:
                AlbumController albumController = new AlbumController();
                albumController.buscarAlbumes(getContext(), query, null, new ResultListener<ResponseAlbum>() {
                    @Override
                    public void finish(ResponseAlbum resultado) {
                        AlbumSearchAdapter albumSearchAdapter = new AlbumSearchAdapter(resultado.getAlbumes(), true, SearchDetailFragment.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        binding.fragmentSearchDetailRecyclerView.setAdapter(albumSearchAdapter);
                        binding.fragmentSearchDetailRecyclerView.setLayoutManager(linearLayoutManager);
                        binding.fragmentSearchDetailRecyclerView.setVisibility(View.VISIBLE);
                        binding.fragmentSearchDetailShimmer.setVisibility(View.INVISIBLE);
                        binding.fragmentSearchDetailShimmer.stopShimmer();
                    }
                });
                break;

            case TYPE_ARTIST:
                ArtistController artistController = new ArtistController();
                artistController.buscarArtistas(getContext(), query, null, new ResultListener<ResponseArtist>() {
                    @Override
                    public void finish(ResponseArtist resultado) {
                        ArtistSearchAdapter artistSearchAdapter = new ArtistSearchAdapter(resultado.getArtistas(), true, SearchDetailFragment.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        binding.fragmentSearchDetailRecyclerView.setAdapter(artistSearchAdapter);
                        binding.fragmentSearchDetailRecyclerView.setLayoutManager(linearLayoutManager);
                        binding.fragmentSearchDetailRecyclerView.setVisibility(View.VISIBLE);
                        binding.fragmentSearchDetailShimmer.setVisibility(View.INVISIBLE);
                        binding.fragmentSearchDetailShimmer.stopShimmer();
                    }
                });
                break;
            case TYPE_TRACK:
                TrackController trackController = new TrackController();
                trackController.buscarTracks(getContext(), query, null, new ResultListener<ResponseTrack>() {
                    @Override
                    public void finish(ResponseTrack resultado) {
                        TrackSearchAdapter trackSearchAdapter = new TrackSearchAdapter(resultado.getTracks(), true, SearchDetailFragment.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        binding.fragmentSearchDetailRecyclerView.setAdapter(trackSearchAdapter);
                        binding.fragmentSearchDetailRecyclerView.setLayoutManager(linearLayoutManager);
                        binding.fragmentSearchDetailRecyclerView.setVisibility(View.VISIBLE);
                        binding.fragmentSearchDetailShimmer.setVisibility(View.INVISIBLE);
                        binding.fragmentSearchDetailShimmer.stopShimmer();
                    }
                });
        }

        return view;
    }

    @Override
    public void onClickAlbumSearchAdapter(Album album) {
        listener.onClickAlbumSearchDetailFragment(album);
    }

    @Override
    public void onClickArtistSearchAdapter(Artist artist) {
        listener.onClickArtistSearchDetailFragment(artist);
    }

    @Override
    public void onClickTrackSearchAdapter(Track track, List<Track> trackList) {
        listener.onClickTrackSearchDetailFragment(track,trackList);
    }

    public interface SearchDetailFragmentListener{
        void onClickAlbumSearchDetailFragment(Album album);
        void onClickArtistSearchDetailFragment(Artist artist);
        void onClickTrackSearchDetailFragment(Track track, List<Track> trackList);
    }

}
