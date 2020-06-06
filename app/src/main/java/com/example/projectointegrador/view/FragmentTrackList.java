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

import com.bumptech.glide.Glide;
import com.example.projectointegrador.R;
import com.example.projectointegrador.controller.TrackController;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.view.adapter.TrackListAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTrackList extends Fragment implements TrackListAdapter.TrackListListener {

    public static final String ALBUM = "album";
    private List<Track> listaDeTracks;
    private ImageView imageViewImagenDelAlbum;
    private RecyclerView recyclerViewListaDeTemas;
    private FragmentTrackListListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_list, container, false);

        Bundle bundleRecibido = getArguments();
        final Album albumRecibido = (Album) bundleRecibido.getSerializable(ALBUM);

        imageViewImagenDelAlbum = view.findViewById(R.id.fragmentTrackList_ImageViewImagenAlbum);
        recyclerViewListaDeTemas = view.findViewById(R.id.fragmentTrackList_RecyclerViewListaDeTracks);

        Glide.with(view)
                .load(albumRecibido.getCover())
                .into(imageViewImagenDelAlbum);

        LinearLayoutManager linearLayoutManagerDeLosTracks = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        TrackController trackController = new TrackController();
        trackController.getTracksDeUnAlbumPorId(albumRecibido.getId(), getContext(), new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                TrackListAdapter trackListAdapter = new TrackListAdapter(resultado,FragmentTrackList.this);
                recyclerViewListaDeTemas.setAdapter(trackListAdapter);
                listaDeTracks = resultado;
            }
        });
        recyclerViewListaDeTemas.setLayoutManager(linearLayoutManagerDeLosTracks);
        return view;
    }

    /**
     * Listeners... cambiar el tipo de dato que pasa si es necesario.
     */
    @Override
    public void TrackListAdapterOnClickTrack(Track track) {
        listener.fragmentOnClickTrackDesdeFragmentTrackList(track);
    }

    public interface FragmentTrackListListener{
        void fragmentOnClickTrackDesdeFragmentTrackList(Track track);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentTrackList.FragmentTrackListListener) {
            listener = (FragmentTrackList.FragmentTrackListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentTrackListListener");
        }
    }
}
