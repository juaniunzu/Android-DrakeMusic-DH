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
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritosFragment extends Fragment implements TracksFavoritosFragment.TracksFavoritosFragmentListener, AlbumesFavoritosFragment.AlbumesFavoritosFragmentListener, ArtistasFavoritosFragment.ArtistasFavoritosFragmentListener {

    private ViewPager viewPager;
    private FragmentFavoritosBinding binding;
    private TextView albumes;
    private TextView artistas;
    private TextView tracks;
    private FavoritosFragmentListener favoritosFragmentListener;

    public FavoritosFragment() {
        // Required empty public constructor
    }

    public FavoritosFragment(FavoritosFragmentListener favoritosFragmentListener) {
        this.favoritosFragmentListener = favoritosFragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFavoritosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        findViews();

        FragmentManager fragmentManager = getFragmentManager();

        List<Fragment> fragments = new ArrayList<>();
        AlbumesFavoritosFragment albumesFavoritosFragment = new AlbumesFavoritosFragment(FavoritosFragment.this);
        TracksFavoritosFragment tracksFavoritosFragment = new TracksFavoritosFragment(FavoritosFragment.this);
        ArtistasFavoritosFragment artistasFavoritosFragment = new ArtistasFavoritosFragment(this);
        fragments.add(albumesFavoritosFragment);
        fragments.add(tracksFavoritosFragment);
        fragments.add(artistasFavoritosFragment);


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

    @Override
    public void onClickTracksFavFragment(Track track, List<Track> trackList) {
        favoritosFragmentListener.onClickTracksFavoritosFragmentFF(track, trackList);
    }

    @Override
    public void onClickAlbumFavFragment(Album album) {
        favoritosFragmentListener.onClickAlbumFavoritosFragmentFF(album);
    }

    @Override
    public void onClickArtistasFavFragment(Artist artist) {
        favoritosFragmentListener.onClickArtistFavoritosFragmentFF(artist);
    }

    public interface FavoritosFragmentListener{
        void onClickTracksFavoritosFragmentFF(Track track, List<Track> trackList);
        void onClickAlbumFavoritosFragmentFF(Album album);
        void onClickArtistFavoritosFragmentFF(Artist artist);
    }
}
