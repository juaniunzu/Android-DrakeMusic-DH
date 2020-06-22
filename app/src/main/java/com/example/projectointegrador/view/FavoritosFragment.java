package com.example.projectointegrador.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Track;

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

        //favoritosFragmentListener = (FavoritosFragmentListener) super.getContext();

        FragmentManager fragmentManager = getFragmentManager();

        List<Fragment> fragments = new ArrayList<>();
        AlbumesFavoritosFragment albumesFavoritosFragment = new AlbumesFavoritosFragment();
        TracksFavoritosFragment tracksFavoritosFragment = new TracksFavoritosFragment();
        ArtistasFavoritosFragment artistasFavoritosFragment = new ArtistasFavoritosFragment();
        fragments.add(albumesFavoritosFragment);
        fragments.add(artistasFavoritosFragment);
        fragments.add(tracksFavoritosFragment);
        albumes.setTextColor(getResources().getColor(R.color.accent));


        ViewPagerFavoritosAdapter viewPagerFavoritosAdapter = new ViewPagerFavoritosAdapter(fragmentManager, fragments);
        viewPager.setAdapter(viewPagerFavoritosAdapter);



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (viewPager.getCurrentItem()){
                    case 0:
                        albumes.setTextColor(getResources().getColor(R.color.accent));
                        artistas.setTextColor(getResources().getColor(R.color.text_title_home));
                        tracks.setTextColor(getResources().getColor(R.color.text_title_home));
                        break;
                    case 1:
                        artistas.setTextColor(getResources().getColor(R.color.accent));
                        tracks.setTextColor(getResources().getColor(R.color.text_title_home));
                        albumes.setTextColor(getResources().getColor(R.color.text_title_home));
                        break;
                    case 2:
                        tracks.setTextColor(getResources().getColor(R.color.accent));
                        artistas.setTextColor(getResources().getColor(R.color.text_title_home));
                        albumes.setTextColor(getResources().getColor(R.color.text_title_home));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }



    private void findViews() {
        viewPager = binding.fragmentFavoritosViewPager;
        albumes = binding.fragmentFavoritosTextViewAlbumes;
        artistas = binding.fragmentFavoritosTextViewArtistas;
        tracks = binding.fragmentFavoritosTextViewTracks;
    }


}
