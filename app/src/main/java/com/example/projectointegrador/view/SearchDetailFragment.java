package com.example.projectointegrador.view;

import android.os.Bundle;

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
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.service.ResponseAlbum;
import com.example.projectointegrador.service.ResponseArtist;
import com.example.projectointegrador.service.ResponseTrack;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.view.adapter.AlbumSearchAdapter;
import com.example.projectointegrador.view.adapter.ArtistSearchAdapter;
import com.example.projectointegrador.view.adapter.TrackSearchAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchDetailFragment extends Fragment {

    public static final String KEY_QUERY = "query";
    public static final String KEY_TYPE = "type";
    public static final String TYPE_ARTIST = "artist";
    public static final String TYPE_ALBUM = "album";
    public static final String TYPE_TRACK = "track";

    TextView tv;
    RecyclerView rv;

    public SearchDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //despues de linkear el textview, setearle el %s con el string atributo que se recibe del fragment anterior

        //la lista que rellene el recyclerview tiene que venir de una busqueda correspondiente
        //a track, album o artista
        //segun en cual de los tres textview anteriores de haya clickeado

        View view = inflater.inflate(R.layout.fragment_search_detail, container, false);

        tv = view.findViewById(R.id.fragmentSearchDetailTextView);
        rv = view.findViewById(R.id.fragmentSearchDetailRecyclerView);

        Bundle bundle = getArguments();
        String query = bundle.getString(KEY_QUERY);

        tv.setText(String.format(getString(R.string.en_busqueda), query));

        switch (bundle.getString(KEY_TYPE)){

            case TYPE_ALBUM:
                AlbumController albumController = new AlbumController();
                albumController.buscarAlbumes(getContext(), query, new ResultListener<ResponseAlbum>() {
                    @Override
                    public void finish(ResponseAlbum resultado) {
                        AlbumSearchAdapter albumSearchAdapter = new AlbumSearchAdapter(resultado.getAlbumes(), true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv.setAdapter(albumSearchAdapter);
                        rv.setLayoutManager(linearLayoutManager);
                    }
                });
                break;

            case TYPE_ARTIST:
                ArtistController artistController = new ArtistController();
                artistController.buscarArtistas(getContext(), query, new ResultListener<ResponseArtist>() {
                    @Override
                    public void finish(ResponseArtist resultado) {
                        ArtistSearchAdapter artistSearchAdapter = new ArtistSearchAdapter(resultado.getArtistas(), true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv.setAdapter(artistSearchAdapter);
                        rv.setLayoutManager(linearLayoutManager);
                    }
                });
                break;
            case TYPE_TRACK:
                TrackController trackController = new TrackController();
                trackController.buscarTracks(getContext(), query, new ResultListener<ResponseTrack>() {
                    @Override
                    public void finish(ResponseTrack resultado) {
                        TrackSearchAdapter trackSearchAdapter = new TrackSearchAdapter(resultado.getTracks(), true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rv.setAdapter(trackSearchAdapter);
                        rv.setLayoutManager(linearLayoutManager);
                    }
                });
        }

        return view;
    }

}
