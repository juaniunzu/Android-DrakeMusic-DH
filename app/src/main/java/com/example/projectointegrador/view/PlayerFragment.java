package com.example.projectointegrador.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.projectointegrador.databinding.FragmentPlayerBinding;
import com.example.projectointegrador.model.Track;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import static com.example.projectointegrador.util.Utils.setFragmentBackground;


public class PlayerFragment extends Fragment {

    public static final String KEY_DETAIL_TRACK = "track";
    private FragmentPlayerBinding binding;
    private ImageView fragmentPlayerImageView;
    private FloatingActionButton fragmentPlayerButtonNext;
    private FloatingActionButton fragmentPlayerButtonPlay;
    private FloatingActionButton fragmentPlayerButtonPrevious;
    private FloatingActionButton fragmentPlayerButtonRepeat;
    private FloatingActionButton fragmentPlayerButtonShuffle;
    private ImageView fragmentPlayerButtonAddFavorite;
    private TextView fragmentPlayerTextViewArtista;
    private TextView fragmentPlayerTextViewNombre;
    private Bitmap bitmap;


    /**
     * Nuevo constructor. Setea el fragment segun el {@param track} parametro
     * @return
     */
    public static PlayerFragment crearPlayerFragment(Track track){
        PlayerFragment fragment = new PlayerFragment();
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
        final Track trackRecibido = (Track) desdeMain.getSerializable(KEY_DETAIL_TRACK);

        setViewResources(trackRecibido);

        setFragmentBackground(getContext(), view, trackRecibido.getAlbum().getCover());

        return view;
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
}
