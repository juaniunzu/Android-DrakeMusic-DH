package com.example.projectointegrador.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectointegrador.R;
import com.example.projectointegrador.databinding.FragmentFavoritosBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritosFragment extends Fragment {

    private ViewPager viewPager;
    private FragmentFavoritosBinding binding;
    private TextView albumes;
    private TextView artistas;
    private TextView tracks;

    public FavoritosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFavoritosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        findViews();

        FragmentManager fragmentManager = getFragmentManager();

        List<Fragment> fragments = new ArrayList<>();
        AlbumesFavoritosFragment albumesFavoritosFragment = new AlbumesFavoritosFragment();
        TracksFavoritosFragment tracksFavoritosFragment = new TracksFavoritosFragment();
        ArtistasFavoritosFragment artistasFavoritosFragment = new ArtistasFavoritosFragment();
        fragments.add(albumesFavoritosFragment);
        fragments.add(tracksFavoritosFragment);
        fragments.add(artistasFavoritosFragment);


        ViewPagerFavoritosAdapter viewPagerFavoritosAdapter = new ViewPagerFavoritosAdapter(fragmentManager, fragments);
        viewPager.setAdapter(viewPagerFavoritosAdapter);



        return view;
    }

    private void findViews() {
        viewPager = binding.fragmentFavoritosViewPager;
        albumes = binding.fragmentFavoritosTextViewAlbumes;
        artistas = binding.fragmentFavoritosTextViewArtistas;
        tracks = binding.fragmentFavoritosTextViewTracks;
    }
}
