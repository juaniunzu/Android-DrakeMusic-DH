package com.example.projectointegrador.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.R;
import com.example.projectointegrador.controller.AlbumController;
import com.example.projectointegrador.controller.TrackController;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;
import com.example.projectointegrador.view.adapter.TrackListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private TextView textViewNombreAlbum;
    private TextView fragmentTrackListTextViewArtista;
    private ToggleButton toggleAddFav;
    private FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_list, container, false);

        Bundle bundleRecibido = getArguments();
        final Album albumRecibido = (Album) bundleRecibido.getSerializable(ALBUM);

        imageViewImagenDelAlbum = view.findViewById(R.id.fragmentTrackList_ImageViewImagenAlbum);
        recyclerViewListaDeTemas = view.findViewById(R.id.fragmentTrackList_RecyclerViewListaDeTracks);
        View appBarLayout = view.findViewById(R.id.fragmentTrackListAppBarLayout);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Utils.setFragmentBackground(getContext(), appBarLayout, albumRecibido.getCover());

        toggleAddFav = view.findViewById(R.id.fragmentTrackListButtonAgregarAFavoritos);
        textViewNombreAlbum = view.findViewById(R.id.fragmentTrackListTextViewNombre);
        textViewNombreAlbum.setText(albumRecibido.getTitle());
        fragmentTrackListTextViewArtista = view.findViewById(R.id.fragmentTrackListTextViewArtista);
        fragmentTrackListTextViewArtista.setText(albumRecibido.getArtist().getName());

        AlbumController albumController = new AlbumController();
        albumController.searchAlbumFavoritos(albumRecibido, firebaseUser, new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                if (resultado.contains(albumRecibido)){
                    toggleAddFav.setText(R.string.en_fav);
                    toggleAddFav.setChecked(true);
                }
                else {
                    toggleAddFav.setText(R.string.agregar_a_fav);
                    toggleAddFav.setChecked(false);
                }
            }
        });

        Glide.with(view)
                .load(albumRecibido.getCover())
                .into(imageViewImagenDelAlbum);

        LinearLayoutManager linearLayoutManagerDeLosTracks = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        TrackController trackController = new TrackController();
        if(Utils.hayInternet(getContext())){
            trackController.getTracksDeUnAlbumPorId(albumRecibido.getId(), getContext(), new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> resultado) {
                    TrackListAdapter trackListAdapter = new TrackListAdapter(resultado,FragmentTrackList.this);
                    recyclerViewListaDeTemas.setAdapter(trackListAdapter);
                    listaDeTracks = resultado;
                    for (Track track : resultado) {
                        track.setAlbum(albumRecibido);
                    }
                }
            });
        } else {
            listener.noHayInternetFragmentTrackList();
        }

        recyclerViewListaDeTemas.setLayoutManager(linearLayoutManagerDeLosTracks);

        toggleAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickAddAlbumFavFragmentTrackList(albumRecibido, toggleAddFav);
            }
        });
        return view;
    }

    /**
     * Listeners... cambiar el tipo de dato que pasa si es necesario.
     */

    @Override
    public void onClickTrackTrackListAdapter(Track track, List<Track> trackList) {
        listener.onClickTrackFragmentTrackList(track, listaDeTracks);
    }

    public interface FragmentTrackListListener{
        void noHayInternetFragmentTrackList();
        void onClickTrackFragmentTrackList(Track track, List<Track> trackList);
        void onClickAddAlbumFavFragmentTrackList(Album album, ToggleButton toggleButton);
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
