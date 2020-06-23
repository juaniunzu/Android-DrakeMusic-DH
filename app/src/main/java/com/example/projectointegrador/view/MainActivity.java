package com.example.projectointegrador.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.projectointegrador.R;
import com.example.projectointegrador.controller.AlbumController;
import com.example.projectointegrador.controller.ArtistController;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static com.example.projectointegrador.view.PlayerActivity.KEY_LISTA;
import static com.example.projectointegrador.view.PlayerActivity.KEY_TRACK;
import static com.example.projectointegrador.view.SearchDetailFragment.KEY_QUERY;
import static com.example.projectointegrador.view.SearchDetailFragment.KEY_TYPE;

public class MainActivity extends AppCompatActivity implements HomeFragment.FragmentHomeListener,
        DetailArtistFragment.FragmentArtistDetailListener,
        FragmentTrackList.FragmentTrackListListener,
        TracksFavoritosFragment.TracksFavoritosFragmentListener,
        ArtistasFavoritosFragment.ArtistasFavoritosFragmentListener,
        AlbumesFavoritosFragment.AlbumesFavoritosFragmentListener,
        PerfilFragment.PerfilFragmentListener,
        SearchFragment.SearchFragmentListener,
        SearchInputFragment.SearchInputFragmentListener,
        SearchDetailFragment.SearchDetailFragmentListener {

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private FirebaseUser firebaseUser;
    public static final String FRAGMENT_HOME = "1";
    public static final String FRAGMENT_BOTTOM = "2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFindViewsByIds();

        HomeFragment homeFragment = new HomeFragment();
        setFragmentInicial(homeFragment);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        bottomNavigationView.setSelectedItemId(R.id.bottomNavigationView_Menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavigationView_Menu:
                        agregarFragmentNavegacion(new HomeFragment(), FRAGMENT_BOTTOM);
                        break;
                    case R.id.bottomNavigationView_Search:
                        agregarFragmentNavegacion(new SearchFragment(), FRAGMENT_BOTTOM);
                        break;
                    case R.id.bottomNavigationView_Favorites:
                        agregarFragmentNavegacion(new FavoritosFragment(), FRAGMENT_BOTTOM);
                        break;
                }
                return true;
            }
        });
    }

    private void setFindViewsByIds() {
        drawerLayout = findViewById(R.id.activityMain_DrawerLayout);
        bottomNavigationView = findViewById(R.id.activityMain_BottomNavigationView);
    }


    @Override
    public void onClickRecomendadosDesdeHomeFragment(Track track, List<Track> trackList) {

        //al momento de hacer click en un track de recomendados, me traigo tanto el track clickeado
        //como la lista completa perteneciente al adapter. me la llevo a una nueva activity donde
        //esta el viewpager que se encargara de crear una lista de fragments con cada track de
        //la lista inicial
        Intent mainAPlayer = new Intent(MainActivity.this, PlayerActivity.class);
        Bundle datos = new Bundle();
        datos.putSerializable("track", track);
        datos.putSerializable("lista", (ArrayList) trackList);
        mainAPlayer.putExtras(datos);
        startActivity(mainAPlayer);

    }

    @Override
    public void onClickUltimosReproducidosDesdeHomeFragment(Track track, List<Track> trackList) {
        Intent mainAPlayer = new Intent(MainActivity.this, PlayerActivity.class);
        Bundle datos = new Bundle();
        datos.putSerializable("track", track);
        datos.putSerializable("lista", (ArrayList) trackList);
        mainAPlayer.putExtras(datos);
        startActivity(mainAPlayer);
    }

    @Override
    public void onClickArtistaDesdeHomeFragment(Artist artist) {
        Bundle datos = new Bundle();
        datos.putSerializable(DetailArtistFragment.ARTIST, artist);
        DetailArtistFragment detailArtistFragment = new DetailArtistFragment();
        detailArtistFragment.setArguments(datos);
        replaceFragment(detailArtistFragment);
    }

    @Override
    public void onClickAlbumDesdeHomeFragment(Album album) {
        Bundle datos = new Bundle();
        datos.putSerializable(FragmentTrackList.ALBUM, album);
        FragmentTrackList fragmentTrackList = new FragmentTrackList();
        fragmentTrackList.setArguments(datos);
        replaceFragment(fragmentTrackList);
    }

    @Override
    public void onClickSettingsHomeFragment() {
        replaceFragment(new PerfilFragment(this));
    }

    @Override
    public void fragmentOnClickAlbumDesdeFragmentArtistDetail(Album album) {
        Bundle datos = new Bundle();
        datos.putSerializable(FragmentTrackList.ALBUM, album);
        FragmentTrackList fragmentTrackList = new FragmentTrackList();
        fragmentTrackList.setArguments(datos);
        replaceFragment(fragmentTrackList);
    }

    @Override
    public void fragmentOnClickTrackDesdeFragmentArtistDetail(Track track, List<Track> trackList) {
        Intent mainAPlayer = new Intent(MainActivity.this, PlayerActivity.class);
        Bundle datos = new Bundle();
        datos.putSerializable("track", track);
        datos.putSerializable("lista", (ArrayList) trackList);
        mainAPlayer.putExtras(datos);
        startActivity(mainAPlayer);
    }


    //click a un track desde adentro del detalle de un album
    @Override
    public void onClickTrackFragmentTrackList(Track track, List<Track> trackList) {
        Intent mainAPlayer = new Intent(MainActivity.this, PlayerActivity.class);
        Bundle datos = new Bundle();
        datos.putSerializable("track", track);
        datos.putSerializable("lista", (ArrayList) trackList);
        mainAPlayer.putExtras(datos);
        startActivity(mainAPlayer);
    }

    @Override
    public void onClickAddAlbumFavFragmentTrackList(Album album, ToggleButton toggleButton) {
        if (!toggleButton.isChecked()) {

            AlbumController albumController = new AlbumController();
            albumController.eliminarAlbumFavoritos(album, firebaseUser, new ResultListener<Album>() {
                @Override
                public void finish(Album resultado) {
                    Toast.makeText(MainActivity.this, "Eliminaste el Album de Favoritos", Toast.LENGTH_SHORT).show();
                }
            });
            toggleButton.setChecked(false);
        } else {

            AlbumController albumController = new AlbumController();
            albumController.agregarAlbumAFavoritos(album, firebaseUser, new ResultListener<Album>() {
                @Override
                public void finish(Album resultado) {
                    Toast.makeText(MainActivity.this, "Agregaste el Album a Favoritos!", Toast.LENGTH_SHORT).show();
                }
            });
            toggleButton.setChecked(true);
        }
    }

    @Override
    public void onClickAddArtistFavFragmentArtistDetail(Artist artist, ToggleButton toggleButton) {
        if (!toggleButton.isChecked()) {
            ArtistController artistController = new ArtistController();
            artistController.eliminarArtistFavoritos(artist, firebaseUser, new ResultListener<Artist>() {
                @Override
                public void finish(Artist resultado) {
                    Toast.makeText(MainActivity.this, "Eliminaste el Artista de Favoritos", Toast.LENGTH_SHORT).show();
                }
            });
            toggleButton.setChecked(false);
        } else {

            ArtistController artistController = new ArtistController();
            artistController.agregarArtistAFavoritos(artist, firebaseUser, new ResultListener<Artist>() {
                @Override
                public void finish(Artist resultado) {
                    Toast.makeText(MainActivity.this, "Agregaste el Artista a Favoritos!", Toast.LENGTH_SHORT).show();
                }
            });
            toggleButton.setChecked(true);
        }
    }


    private void setFragmentInicial(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activityMain_contenedorDeFragments, fragment);
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityMain_contenedorDeFragments, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activityMain_contenedorDeFragments, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void agregarFragmentNavegacion(Fragment fragment, String id){
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityMain_contenedorDeFragments, fragment);
        final int count = fragmentManager.getBackStackEntryCount();

        if( id.equals(FRAGMENT_BOTTOM) ) {
            fragmentTransaction.addToBackStack(id);
        }

        fragmentTransaction.commit();

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // If the stack decreases it means I clicked the back button
                if( fragmentManager.getBackStackEntryCount() <= count){
                    // pop all the fragment and remove the listener
                    fragmentManager.popBackStack(FRAGMENT_BOTTOM, POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    // set the home button selected
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                }
            }
        });
    }



    /**
     * Metodo para Logout de FB y GOOGLE. Despues Se le agregara Firebase. Vuelve a la Login Activity y mata la Actividad Actual.
     */
    public void logout() {
        //Logout de CaraLibro
        LoginManager.getInstance().logOut();

        //LOGOUT BASE FUEGO
        FirebaseAuth.getInstance().signOut();

        //Logout de Google. Si, es bastante mas grande.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }


                );

    }

    @Override
    public void onClickTracksFavFragment(Track track, List<Track> trackList) {
        Intent mainAPlayer = new Intent(MainActivity.this, PlayerActivity.class);
        Bundle datos = new Bundle();
        datos.putSerializable("track", track);
        datos.putSerializable("lista", (ArrayList) trackList);
        mainAPlayer.putExtras(datos);
        startActivity(mainAPlayer);
    }

    @Override
    public void onClickAlbumFavFragment(Album album) {
        Bundle datos = new Bundle();
        datos.putSerializable(FragmentTrackList.ALBUM, album);
        FragmentTrackList fragmentTrackList = new FragmentTrackList();
        fragmentTrackList.setArguments(datos);
        replaceFragment(fragmentTrackList);
    }

    @Override
    public void onClickArtistasFavFragment(Artist artist) {
        Bundle datos = new Bundle();
        datos.putSerializable(DetailArtistFragment.ARTIST, artist);
        DetailArtistFragment detailArtistFragment = new DetailArtistFragment();
        detailArtistFragment.setArguments(datos);
        replaceFragment(detailArtistFragment);
    }

    @Override
    //perfil fragment
    public void onClickFavoritos() {
        addFragment(new FavoritosFragment());
    }

    @Override
    //perfil fragment
    public void onClickCerrarSesion() {
        Toast.makeText(this, "Has cerrado sesion! esperamos verte pronto", Toast.LENGTH_SHORT).show();
        logout();
    }






    //SEARCH FRAGMENT
    @Override
    public void onClickSearchFragment(Utils.Searchable searchable) {
        //en construccion, ponele
    }

    //SEARCH FRAGMENT
    @Override
    public void onClickSearchFragment() {
        SearchInputFragment searchInputFragment = new SearchInputFragment();
        addFragment(searchInputFragment);//todo
    }

    //SEARCH FRAGMENT LISTO












    //METODOS SEARCHINPUT FRAGMENT

    @Override
    public void onClickFiltroVerTodo(String query, String type) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_QUERY, query);
        bundle.putString(KEY_TYPE, type);
        SearchDetailFragment searchDetailFragment = new SearchDetailFragment();
        searchDetailFragment.setArguments(bundle);
        addFragment(searchDetailFragment);
    }

    @Override
    public void onClickAlbumSearchInputFragment(Album album) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentTrackList.ALBUM,album);
        FragmentTrackList fragmentTrackList = new FragmentTrackList();
        fragmentTrackList.setArguments(bundle);
        addFragment(fragmentTrackList);
    }

    @Override
    public void onClickArtistSearchInputFragment(Artist artist) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailArtistFragment.ARTIST, artist);
        DetailArtistFragment detailArtistFragment = new DetailArtistFragment();
        detailArtistFragment.setArguments(bundle);
        addFragment(detailArtistFragment);
    }

    @Override
    public void onClickTrackSearchInputFragment(Track track, List<Track> trackList) {
        Intent searchToPlayer = new Intent(this, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_TRACK, track);
        bundle.putSerializable(KEY_LISTA, (ArrayList) trackList);
        searchToPlayer.putExtras(bundle);
        startActivity(searchToPlayer);
    }

    //SEARCH INPUT FRAGMENT LISTO












    @Override
    public void onClickAlbumSearchDetailFragment(Album album) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentTrackList.ALBUM,album);
        FragmentTrackList fragmentTrackList = new FragmentTrackList();
        fragmentTrackList.setArguments(bundle);
        addFragment(fragmentTrackList);
    }

    @Override
    public void onClickArtistSearchDetailFragment(Artist artist) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailArtistFragment.ARTIST, artist);
        DetailArtistFragment detailArtistFragment = new DetailArtistFragment();
        detailArtistFragment.setArguments(bundle);
        addFragment(detailArtistFragment);
    }

    @Override
    public void onClickTrackSearchDetailFragment(Track track, List<Track> trackList) {
        Intent searchToPlayer = new Intent(this, PlayerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_TRACK, track);
        bundle.putSerializable(KEY_LISTA, (ArrayList) trackList);
        searchToPlayer.putExtras(bundle);
        startActivity(searchToPlayer);
    }

    
}
