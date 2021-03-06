package com.example.projectointegrador.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.controller.TrackController;
import com.example.projectointegrador.databinding.FragmentPlayerBinding;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.ResultListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;

import static com.example.projectointegrador.util.Utils.setFragmentBackground;


public class PlayerFragment extends Fragment {

    public static final String KEY_DETAIL_TRACK = "track";
    private ImageView fragmentPlayerImageView;
    private CheckBox fragmentPlayerButtonAddFavorite;
    private TextView fragmentPlayerTextViewArtista;
    private TextView fragmentPlayerTextViewNombre;
    private PlayerFragmentListener listener;
    private Track trackRecibido;
    private FragmentPlayerBinding binding;
    private FirebaseUser firebaseUser;



    /**
     * Nuevo constructor. Setea el fragment segun el {@param track} parametro
     * @return
     */
    public static PlayerFragment crearPlayerFragment(Track track, PlayerFragmentListener listener){
        PlayerFragment fragment = new PlayerFragment();
        fragment.setListener(listener);
        Bundle datosDeTrack = new Bundle();
        datosDeTrack.putSerializable(KEY_DETAIL_TRACK, track);
        fragment.setArguments(datosDeTrack);
        return fragment;
    }

    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPlayerBinding.inflate(inflater, container, false);

        findViews();

        final View view = binding.getRoot();

        Bundle desdeMain = getArguments();
        trackRecibido = (Track) desdeMain.getSerializable(KEY_DETAIL_TRACK);

        setViewResources(trackRecibido);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        setFragmentBackground(getContext(), view, trackRecibido.getAlbum().getCover());

        fragmentPlayerButtonAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickAddTrackFavorite(trackRecibido, fragmentPlayerButtonAddFavorite);
            }
        });

        TrackController trackController = new TrackController();
        trackController.searchTrackFavoritos(trackRecibido, firebaseUser, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                if (resultado.contains(trackRecibido)){
                    fragmentPlayerButtonAddFavorite.setChecked(true);
                }
                else {
                    fragmentPlayerButtonAddFavorite.setChecked(false);
                }
            }
        });


        return view;
    }

    public void setListener(PlayerFragmentListener listener) {
        this.listener = listener;
    }

    public Track getTrackRecibido() {
        return trackRecibido;
    }

    private void setViewResources(Track trackRecibido) {
        Glide.with(getContext()).load(trackRecibido.getAlbum().getCover()).into(fragmentPlayerImageView);
        fragmentPlayerTextViewArtista.setText(trackRecibido.getArtist().getName());
        fragmentPlayerTextViewNombre.setText(trackRecibido.getTitle());
    }

    private void findViews() {

        fragmentPlayerImageView = binding.fragmentPlayerImageView;
        fragmentPlayerButtonAddFavorite = binding.fragmentPlayerButtonAddFavorite;
        fragmentPlayerTextViewArtista = binding.fragmentPlayerTextViewArtista;
        fragmentPlayerTextViewNombre = binding.fragmentPlayerTextViewNombre;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public interface PlayerFragmentListener{
        void onClickAddTrackFavorite(Track track, CheckBox checkBox);
    }
}
