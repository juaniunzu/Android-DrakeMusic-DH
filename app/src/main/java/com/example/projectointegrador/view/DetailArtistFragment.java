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
import com.example.projectointegrador.controller.TrackController;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.view.adapter.AlbumAdapter;
import com.example.projectointegrador.view.adapter.TrackListAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailArtistFragment extends Fragment implements AlbumAdapter.AlbumAdapterListener,
                                                                TrackListAdapter.TrackListListener {

    public static final String ARTIST = "artist";
    private List<Album> listaDeAlbumesDelArtista;
    private List<Track> listaDeTop5TracksDelArtista;

    private RecyclerView recyclerViewListaDeAlbumes;
    private RecyclerView recyclerViewListaDeTop5Tracks;

    private FragmentArtistDetailListener listener;

    public DetailArtistFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_artist, container, false);

        Bundle datosRecibidos = getArguments();
        final Artist artistaRecibido = (Artist) datosRecibidos.getSerializable(ARTIST);
        recyclerViewListaDeAlbumes = view.findViewById(R.id.fragmentArtistDetail_RecyclerViewAlbumes);
        recyclerViewListaDeTop5Tracks = view.findViewById(R.id.fragmentArtistDetail_RecyclerViewTopTracks);


        LinearLayoutManager linearLayoutManagerDeAlbumes = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManagerDeTop5Tracks = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        AlbumController albumController = new AlbumController();
        albumController.getAlbumesDeUnArtista(artistaRecibido.getId(), getContext(), new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                for (Album album: resultado) {
                    album.setArtist(new Artist(artistaRecibido.getName()));
                }
                AlbumAdapter albumAdapter = new AlbumAdapter(resultado, DetailArtistFragment.this);
                recyclerViewListaDeAlbumes.setAdapter(albumAdapter);
                listaDeAlbumesDelArtista = resultado;
            }
        });
        recyclerViewListaDeAlbumes.setLayoutManager(linearLayoutManagerDeAlbumes);

        TrackController trackController = new TrackController();
        trackController.getTop5TracksDeUnArtistaPorId(artistaRecibido.getId(), getContext(), new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                TrackListAdapter trackListAdapter = new TrackListAdapter(resultado, DetailArtistFragment.this);
                recyclerViewListaDeTop5Tracks.setAdapter(trackListAdapter);
                listaDeTop5TracksDelArtista = resultado;
                for (Track track : resultado) {
                    track.setArtist(artistaRecibido);
                }
            }
        });
        recyclerViewListaDeTop5Tracks.setLayoutManager(linearLayoutManagerDeTop5Tracks);

        return view;
    }

    /**
     * Interfaces y metodos para hacer que la actividad donde se pegue el fragment tenga que implementar sus listener
     * y Asi conocerlos.
     */


    @Override
    public void onClickTrackTrackListAdapter(Track track, List<Track> trackList) {
        listener.fragmentOnClickTrackDesdeFragmentArtistDetail(track, this.listaDeTop5TracksDelArtista);
    }

    @Override
    public void onClickAlbumAlbumAdapter(Album album) {
        listener.fragmentOnClickAlbumDesdeFragmentArtistDetail(album);
    }

    public interface FragmentArtistDetailListener{
        void fragmentOnClickAlbumDesdeFragmentArtistDetail(Album album);
        void fragmentOnClickTrackDesdeFragmentArtistDetail(Track track, List<Track> trackList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailArtistFragment.FragmentArtistDetailListener) {
            listener = (DetailArtistFragment.FragmentArtistDetailListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentArtistDetailListener");
        }
    }
}
