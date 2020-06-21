package com.example.projectointegrador.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.projectointegrador.R;
import com.example.projectointegrador.controller.AlbumController;
import com.example.projectointegrador.controller.ArtistController;
import com.example.projectointegrador.databinding.ActivitySearchBinding;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static com.example.projectointegrador.view.PlayerActivity.KEY_LISTA;
import static com.example.projectointegrador.view.PlayerActivity.KEY_TRACK;
import static com.example.projectointegrador.view.SearchDetailFragment.KEY_QUERY;
import static com.example.projectointegrador.view.SearchDetailFragment.KEY_TYPE;

public class SearchActivity extends AppCompatActivity implements SearchFragment.SearchFragmentListener,
                                                    SearchInputFragment.SearchInputFragmentListener,
                                                    SearchDetailFragment.SearchDetailFragmentListener,
        FragmentTrackList.FragmentTrackListListener, DetailArtistFragment.FragmentArtistDetailListener {


    private ActivitySearchBinding binding;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        pegarFragmentAdd(new SearchFragment());


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        binding.activitySearchBottomNavigationView.setSelectedItemId(R.id.bottomNavigationView_Search);

        binding.activitySearchBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavigationView_Menu:
                        Intent searchAMain = new Intent(SearchActivity.this, MainActivity.class);
                        startActivity(searchAMain);
                        break;
                    case R.id.bottomNavigationView_Search:
                        setFragmentReplace(new SearchFragment());
                        break;
                    case R.id.bottomNavigationView_Favorites:
                        Toast.makeText(SearchActivity.this, "En Construccion.", Toast.LENGTH_SHORT).show();
                        //logout();
                        break;
                }
                return true;
            }
        });

    }

    /**
     * setea fragment inicial en el onCreate de la actividad
     * @param fragment
     */
    private void pegarFragmentAdd(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activitySearchFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    private void setFragmentReplace(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activitySearchFragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setFragmentReplaceNoBackStack(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activitySearchFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    private void pegarFragmentAddBackStack(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activitySearchFragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClickSearchFragment(Utils.Searchable searchable) {

    }

    @Override
    public void onClickSearchFragment() {
        SearchInputFragment searchInputFragment = new SearchInputFragment();
        setFragmentReplace(searchInputFragment);
    }



    public void pegarSearchDetailFragment (String query, String type){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_QUERY, query);
        bundle.putString(KEY_TYPE, type);
        SearchDetailFragment searchDetailFragment = new SearchDetailFragment();
        searchDetailFragment.setArguments(bundle);
        setFragmentReplace(searchDetailFragment);
    }

    @Override
    public void onClickFiltroVerTodo(String query, String type) {
        pegarSearchDetailFragment(query,type);
    }

    @Override
    public void onClickAlbumSearchInputFragment(Album album) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentTrackList.ALBUM,album);
        FragmentTrackList fragmentTrackList = new FragmentTrackList();
        fragmentTrackList.setArguments(bundle);
        setFragmentReplace(fragmentTrackList);
    }

    @Override
    public void onClickArtistSearchInputFragment(Artist artist) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailArtistFragment.ARTIST, artist);
        DetailArtistFragment detailArtistFragment = new DetailArtistFragment();
        detailArtistFragment.setArguments(bundle);
        setFragmentReplace(detailArtistFragment);
    }

    @Override
    public void onClickTrackSearchInputFragment(Track track, List<Track> trackList) {
        Intent searchToPlayer = new Intent(SearchActivity.this, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_TRACK, track);
        bundle.putSerializable(KEY_LISTA, (ArrayList) trackList);
        searchToPlayer.putExtras(bundle);
        startActivity(searchToPlayer);
    }

    @Override
    public void onClickAlbumSearchDetailFragment(Album album) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentTrackList.ALBUM,album);
        FragmentTrackList fragmentTrackList = new FragmentTrackList();
        fragmentTrackList.setArguments(bundle);
        setFragmentReplace(fragmentTrackList);
    }

    @Override
    public void onClickArtistSearchDetailFragment(Artist artist) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailArtistFragment.ARTIST, artist);
        DetailArtistFragment detailArtistFragment = new DetailArtistFragment();
        detailArtistFragment.setArguments(bundle);
        setFragmentReplace(detailArtistFragment);
    }

    @Override
    public void onClickTrackSearchDetailFragment(Track track, List<Track> trackList) {
        Intent searchToPlayer = new Intent(SearchActivity.this, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_TRACK, track);
        bundle.putSerializable(KEY_LISTA, (ArrayList) trackList);
        searchToPlayer.putExtras(bundle);
        startActivity(searchToPlayer);
    }

    @Override
    public void fragmentOnClickAlbumDesdeFragmentArtistDetail(Album album) {
        Bundle datos = new Bundle();
        datos.putSerializable(FragmentTrackList.ALBUM,album);
        FragmentTrackList fragmentTrackList = new FragmentTrackList();
        fragmentTrackList.setArguments(datos);
        setFragmentReplace(fragmentTrackList);
    }

    @Override
    public void fragmentOnClickTrackDesdeFragmentArtistDetail(Track track, List<Track> trackList) {
        Intent searchAPlayer = new Intent(SearchActivity.this, PlayerActivity.class);
        Bundle datos = new Bundle();
        datos.putSerializable("track", track);
        datos.putSerializable("lista", (ArrayList) trackList);
        searchAPlayer.putExtras(datos);
        startActivity(searchAPlayer);
    }

    @Override
    public void onClickAddArtistFavFragmentArtistDetail(Artist artist) {
        ArtistController artistController = new ArtistController();
        artistController.agregarArtistAFavoritos(artist, firebaseUser, new ResultListener<Artist>() {
            @Override
            public void finish(Artist resultado) {
                Toast.makeText(SearchActivity.this, "Agregaste el Artista a Favoritos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickTrackFragmentTrackList(Track track, List<Track> trackList) {
        Intent searchAPlayer = new Intent(SearchActivity.this, PlayerActivity.class);
        Bundle datos = new Bundle();
        datos.putSerializable("track", track);
        datos.putSerializable("lista", (ArrayList) trackList);
        searchAPlayer.putExtras(datos);
        startActivity(searchAPlayer);
    }

    @Override
    public void onClickAddAlbumFavFragmentTrackList(Album album) {
        AlbumController albumController = new AlbumController();
        albumController.agregarAlbumAFavoritos(album, firebaseUser, new ResultListener<Album>() {
            @Override
            public void finish(Album resultado) {
                Toast.makeText(SearchActivity.this, "Agregaste el Album a Favoritos!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}