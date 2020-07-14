package com.example.projectointegrador.view;

import android.content.Intent;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.R;
import com.example.projectointegrador.controller.AlbumController;
import com.example.projectointegrador.controller.ArtistController;
import com.example.projectointegrador.controller.HistorialController;
import com.example.projectointegrador.controller.TrackController;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Busqueda;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.DrakePlayer;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;
import com.example.projectointegrador.view.fragment.AlbumesFavoritosFragment;
import com.example.projectointegrador.view.fragment.ArtistasFavoritosFragment;
import com.example.projectointegrador.view.fragment.DetailArtistFragment;
import com.example.projectointegrador.view.fragment.FavoritosFragment;
import com.example.projectointegrador.view.fragment.FragmentTrackList;
import com.example.projectointegrador.view.fragment.HomeFragment;
import com.example.projectointegrador.view.fragment.NoInetFragment;
import com.example.projectointegrador.view.fragment.PerfilFragment;
import com.example.projectointegrador.view.fragment.SearchDetailFragment;
import com.example.projectointegrador.view.fragment.SearchFragment;
import com.example.projectointegrador.view.fragment.SearchInputFragment;
import com.example.projectointegrador.view.fragment.TracksFavoritosFragment;
import com.example.projectointegrador.view.notification.CreateNotification;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.seismic.ShakeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static com.example.projectointegrador.view.PlayerActivity.KEY_LISTA;
import static com.example.projectointegrador.view.PlayerActivity.KEY_TRACK;
import static com.example.projectointegrador.view.fragment.SearchDetailFragment.KEY_QUERY;
import static com.example.projectointegrador.view.fragment.SearchDetailFragment.KEY_TYPE;
import static com.example.projectointegrador.view.fragment.SearchInputFragment.KEY_BUSQUEDA;

public class MainActivity extends AppCompatActivity implements HomeFragment.FragmentHomeListener,
        DetailArtistFragment.FragmentArtistDetailListener,
        FragmentTrackList.FragmentTrackListListener,
        TracksFavoritosFragment.TracksFavoritosFragmentListener,
        ArtistasFavoritosFragment.ArtistasFavoritosFragmentListener,
        AlbumesFavoritosFragment.AlbumesFavoritosFragmentListener,
        PerfilFragment.PerfilFragmentListener,
        SearchFragment.SearchFragmentListener,
        SearchInputFragment.SearchInputFragmentListener,
        SearchDetailFragment.SearchDetailFragmentListener,
        NoInetFragment.NoInetFragmentListener,
        DrakePlayer.DrakePlayerListener,
ShakeDetector.Listener{

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private FirebaseUser firebaseUser;
    private ConstraintLayout reproductorChico;
    private Track trackSonando;
    private List<Track> listaDeReproduccion = new ArrayList<>();
    private ImageView imagenReproductorChico;
    private TextView trackReproductorChico;
    private TextView artistaReproductorChico;
    private ImageView imageViewTrackSiguiente;
    private ImageView imageViewTrackAnterior;
    private ToggleButton playPauseReproductorChico;
    public static final String FRAGMENT_HOME = "1";
    public static final String FRAGMENT_BOTTOM = "2";
    public static final Integer GO_REPRODUCTOR = 150;
    private DrakePlayer drakePlayer;
    private ShakeDetector shakeDetector = new ShakeDetector(this);
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFindViewsByIds();
        // Llama al audioPlayer. Que es uno solo y existe en to.do el proyecto.
        final FavoritosFragment favoritosFragment = new FavoritosFragment();
        drakePlayer = DrakePlayer.getInstance();
        drakePlayer.setListener(this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        reproductorChico.setVisibility(View.GONE);

        playPauseReproductorChico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playPauseReproductorChico.isChecked()) {
                    drakePlayer.start(MainActivity.this);
                    playPauseReproductorChico.setBackground(getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));

                } else {
                    drakePlayer.pause(MainActivity.this);
                    playPauseReproductorChico.setBackground(getDrawable(R.drawable.ic_play_circle_filled_black_24dp));
                }
            }
        });


        HomeFragment homeFragment = new HomeFragment();
        setFragmentInicial(homeFragment);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        bottomNavigationView.setSelectedItemId(R.id.bottomNavigationView_Menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(Objects.equals(item.getItemId(), bottomNavigationView.getSelectedItemId()))
                    return true;
                switch (item.getItemId()) {
                    case R.id.bottomNavigationView_Menu:
                        agregarFragmentNavegacion(new HomeFragment(), FRAGMENT_BOTTOM);
                        break;
                    case R.id.bottomNavigationView_Search:
                        agregarFragmentNavegacion(new SearchFragment(), FRAGMENT_BOTTOM);
                        break;
                    case R.id.bottomNavigationView_Favorites:
                        agregarFragmentNavegacion(favoritosFragment, FRAGMENT_BOTTOM);
                        break;
                }
                return true;
            }
        });
    }

    private boolean estaPegadoElFragmentNoInet(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment mCurrentFragment = fragments.get(fragments.size() - 1);
        String simpleName = mCurrentFragment.getClass().getSimpleName();
        return simpleName.equals("NoInetFragment");
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!Utils.hayInternet(this)){
            bottomNavigationView.setVisibility(View.GONE);
            setFragmentInicialNoInet(new NoInetFragment());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        shakeDetector.stop();
    }

    private void setFindViewsByIds() {
        drawerLayout = findViewById(R.id.activityMain_DrawerLayout);
        bottomNavigationView = findViewById(R.id.activityMain_BottomNavigationView);
        reproductorChico = findViewById(R.id.mainActivity_ReproductorChiquito);
        trackReproductorChico = findViewById(R.id.activityMainTextViewTrackPlayer);
        artistaReproductorChico = findViewById(R.id.activityMainTextViewArtistaPlayer);
        imagenReproductorChico = findViewById(R.id.activityMainImageViewPlayer);
        playPauseReproductorChico = findViewById(R.id.activityMainPlayPauseButtonPlayer);
        imageViewTrackSiguiente = findViewById(R.id.activityMainNextButtonPlayer);
        imageViewTrackAnterior = findViewById(R.id.activityMainPreviousButtonPlayer);
    }

    private void actualizarVistaReproductorChico(){
        Glide.with(this).load(drakePlayer.getTrackActual().getAlbum().getCover()).into(imagenReproductorChico);
        trackReproductorChico.setText(drakePlayer.getTrackActual().getTitle());
        artistaReproductorChico.setText(drakePlayer.getTrackActual().getArtist().getName());
    }

    private void setReproductorChico(final Track track, List<Track> trackList) {
        this.listaDeReproduccion.clear();
        this.listaDeReproduccion.addAll(trackList);
        this.trackSonando = drakePlayer.getTrackActual();
        this.playPauseReproductorChico.setChecked(false);
        this.playPauseReproductorChico.setBackground(getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));
        Glide.with(this).load(trackSonando.getAlbum().getCover()).into(imagenReproductorChico);
        trackReproductorChico.setText(trackSonando.getTitle());
        artistaReproductorChico.setText(trackSonando.getArtist().getName());

        trackReproductorChico.setSelected(true);
        artistaReproductorChico.setSelected(true);

        imageViewTrackSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    drakePlayer.next(MainActivity.this);
                    trackSonando = drakePlayer.getTrackActual();
                    Glide.with(MainActivity.this).load(trackSonando.getAlbum().getCover()).into(imagenReproductorChico);
                    trackReproductorChico.setText(trackSonando.getTitle());
                    artistaReproductorChico.setText(trackSonando.getArtist().getName());
                    agregarTrackAUltimosReproducidos(trackSonando);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        imageViewTrackAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    drakePlayer.prev(MainActivity.this);
                    trackSonando = drakePlayer.getTrackActual();
                    playPauseReproductorChico.setChecked(false);
                    playPauseReproductorChico.setBackground(getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));
                    Glide.with(MainActivity.this).load(trackSonando.getAlbum().getCover()).into(imagenReproductorChico);
                    trackReproductorChico.setText(trackSonando.getTitle());
                    artistaReproductorChico.setText(trackSonando.getArtist().getName());
                    agregarTrackAUltimosReproducidos(trackSonando);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        DrakePlayer.getInstance().getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                drakePlayer.setTemaSiguienteEnLista(MainActivity.this);
            }
        });
    }


    private void agregarTrackAUltimosReproducidos(Track track) {
        TrackController trackController = new TrackController();
        trackController.agregarTrackAUltimosReproducidos(track, firebaseUser, new ResultListener<Track>() {
            @Override
            public void finish(Track resultado) {

            }
        });
    }

    @Override
    public void noHayInternetHomeFragment() {
        bottomNavigationView.setVisibility(View.GONE);
        setFragmentInicialNoInet(new NoInetFragment());
        reproductorChico.setVisibility(View.GONE);
    }

    @Override
    public void onClickRecomendadosDesdeHomeFragment(Track track, List<Track> trackList) {
        if(Utils.hayInternet(this)){
            Intent mainAPlayer = new Intent(MainActivity.this, PlayerActivity.class);
            Bundle datos = new Bundle();
            datos.putSerializable("track", track);
            datos.putSerializable("lista", (ArrayList) trackList);
            mainAPlayer.putExtras(datos);
            startActivityForResult(mainAPlayer, GO_REPRODUCTOR);
        } else {
            noHayInternetHomeFragment();
        }

    }


    @Override
    public void onClickUltimosReproducidosDesdeHomeFragment(Track track, List<Track> trackList) {
        if(Utils.hayInternet(this)){
            Intent mainAPlayer = new Intent(MainActivity.this, PlayerActivity.class);
            Bundle datos = new Bundle();
            datos.putSerializable("track", track);
            datos.putSerializable("lista", (ArrayList) trackList);
            mainAPlayer.putExtras(datos);
            startActivityForResult(mainAPlayer, GO_REPRODUCTOR);
        } else {
            noHayInternetHomeFragment();
        }
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
    public void noHayInternetFragmentArtistDetail() {
        noHayInternetHomeFragment();
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
        if(Utils.hayInternet(this)){
            Intent mainAPlayer = new Intent(MainActivity.this, PlayerActivity.class);
            Bundle datos = new Bundle();
            datos.putSerializable("track", track);
            datos.putSerializable("lista", (ArrayList) trackList);
            mainAPlayer.putExtras(datos);
            startActivityForResult(mainAPlayer, GO_REPRODUCTOR);
        } else {
            noHayInternetHomeFragment();
        }
    }


    @Override
    public void noHayInternetFragmentTrackList() {
        noHayInternetHomeFragment();
    }

    //click a un track desde adentro del detalle de un album
    @Override
    public void onClickTrackFragmentTrackList(Track track, List<Track> trackList) {
        if(Utils.hayInternet(this)){
            Intent mainAPlayer = new Intent(MainActivity.this, PlayerActivity.class);
            Bundle datos = new Bundle();
            datos.putSerializable("track", track);
            datos.putSerializable("lista", (ArrayList) trackList);
            mainAPlayer.putExtras(datos);
            startActivityForResult(mainAPlayer, GO_REPRODUCTOR);
        } else {
            noHayInternetHomeFragment();
        }
    }

    @Override
    public void onClickAddAlbumFavFragmentTrackList(Album album, ToggleButton toggleButton) {
        if (!toggleButton.isChecked()) {

            AlbumController albumController = new AlbumController();
            albumController.eliminarAlbumFavoritos(album, firebaseUser, new ResultListener<Album>() {
                @Override
                public void finish(Album resultado) {
                }
            });
            toggleButton.setChecked(false);
        } else {

            AlbumController albumController = new AlbumController();
            albumController.agregarAlbumAFavoritos(album, firebaseUser, new ResultListener<Album>() {
                @Override
                public void finish(Album resultado) {
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
                }
            });
            toggleButton.setChecked(false);
        } else {

            ArtistController artistController = new ArtistController();
            artistController.agregarArtistAFavoritos(artist, firebaseUser, new ResultListener<Artist>() {
                @Override
                public void finish(Artist resultado) {
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

    private void setFragmentInicialNoInet(Fragment fragment) {

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

    private void agregarFragmentNavegacion(Fragment fragment, String id) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityMain_contenedorDeFragments, fragment);
        final int count = fragmentManager.getBackStackEntryCount();

        if (id.equals(FRAGMENT_BOTTOM)) {
            fragmentTransaction.addToBackStack(id);
        }

        fragmentTransaction.commit();

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // If the stack decreases it means I clicked the back button
                if (fragmentManager.getBackStackEntryCount() <= count) {
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
                                if (drakePlayer.getMediaPlayer().isPlaying()){
                                    drakePlayer.getMediaPlayer().stop();
                                }
                                if(PlayerActivity.getNotificationManager() != null){
                                    PlayerActivity.getNotificationManager().cancelAll();
                                }
                                startActivity(intent);
                                finish();
                            }
                        }


                );

    }

    @Override
    public void onClickTracksFavFragment(Track track, List<Track> trackList) {
        if(Utils.hayInternet(this)){
            Intent mainAPlayer = new Intent(MainActivity.this, PlayerActivity.class);
            Bundle datos = new Bundle();
            datos.putSerializable("track", track);
            datos.putSerializable("lista", (ArrayList) trackList);
            mainAPlayer.putExtras(datos);
            startActivityForResult(mainAPlayer, GO_REPRODUCTOR);
        } else {
            noHayInternetHomeFragment();
        }
    }

    @Override
    public void noHayInternetTracksFavFragment() {
        noHayInternetHomeFragment();
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
    public void noHayInternetAlbumFavFragment() {
        noHayInternetHomeFragment();
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
    public void noHayInternetArtistasFavFragment() {
        noHayInternetHomeFragment();
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
        if(Utils.hayInternet(this)){
            SearchInputFragment searchInputFragment = new SearchInputFragment();
            addFragment(searchInputFragment);
        } else noHayInternetHomeFragment();
    }

    @Override
    public void onClickHistorialSearchFragment(Busqueda busqueda) {
        if(Utils.hayInternet(this)){
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_BUSQUEDA, busqueda);
            SearchInputFragment searchInputFragment = new SearchInputFragment();
            searchInputFragment.setArguments(bundle);
            addFragment(searchInputFragment);
        } else noHayInternetHomeFragment();
    }


    @Override
    public void onClickFiltroVerTodo(String query, String type) {
        if(Utils.hayInternet(this)){
            Bundle bundle = new Bundle();
            bundle.putString(KEY_QUERY, query);
            bundle.putString(KEY_TYPE, type);
            SearchDetailFragment searchDetailFragment = new SearchDetailFragment();
            searchDetailFragment.setArguments(bundle);
            addFragment(searchDetailFragment);
        } else noHayInternetHomeFragment();
    }

    @Override
    public void onClickAlbumSearchInputFragment(Album album) {
        if(Utils.hayInternet(this)){
            Bundle bundle = new Bundle();
            bundle.putSerializable(FragmentTrackList.ALBUM, album);
            FragmentTrackList fragmentTrackList = new FragmentTrackList();
            fragmentTrackList.setArguments(bundle);
            addFragment(fragmentTrackList);
        } else noHayInternetHomeFragment();
    }

    @Override
    public void onClickArtistSearchInputFragment(Artist artist) {
        if(Utils.hayInternet(this)){
            Bundle bundle = new Bundle();
            bundle.putSerializable(DetailArtistFragment.ARTIST, artist);
            DetailArtistFragment detailArtistFragment = new DetailArtistFragment();
            detailArtistFragment.setArguments(bundle);
            addFragment(detailArtistFragment);
        } else noHayInternetHomeFragment();
    }

    @Override
    public void onClickTrackSearchInputFragment(Track track, List<Track> trackList) {
        if(Utils.hayInternet(this)){
            Intent searchToPlayer = new Intent(this, PlayerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_TRACK, track);
            bundle.putSerializable(KEY_LISTA, (ArrayList) trackList);
            searchToPlayer.putExtras(bundle);
            startActivityForResult(searchToPlayer, GO_REPRODUCTOR);
        } else {
            noHayInternetHomeFragment();
        }

    }

    @Override
    public void agregarBusquedaAlHistorial(Busqueda busqueda) {
        HistorialController historialController = new HistorialController();
        if(Utils.hayInternet(this)){
            historialController.agregarBusquedaAlHistorial(busqueda, firebaseUser, new ResultListener<Busqueda>() {
                @Override
                public void finish(Busqueda resultado) {
                }
            });
        } else noHayInternetHomeFragment();
    }

    @Override
    public void onClickAlbumSearchDetailFragment(Album album) {
        if(Utils.hayInternet(this)){
            Bundle bundle = new Bundle();
            bundle.putSerializable(FragmentTrackList.ALBUM, album);
            FragmentTrackList fragmentTrackList = new FragmentTrackList();
            fragmentTrackList.setArguments(bundle);
            addFragment(fragmentTrackList);
        } else noHayInternetHomeFragment();
    }

    @Override
    public void onClickArtistSearchDetailFragment(Artist artist) {
        if(Utils.hayInternet(this)){
            Bundle bundle = new Bundle();
            bundle.putSerializable(DetailArtistFragment.ARTIST, artist);
            DetailArtistFragment detailArtistFragment = new DetailArtistFragment();
            detailArtistFragment.setArguments(bundle);
            addFragment(detailArtistFragment);
        } else noHayInternetHomeFragment();
    }

    @Override
    public void onClickTrackSearchDetailFragment(Track track, List<Track> trackList) {
        if(Utils.hayInternet(this)){
            Intent searchToPlayer = new Intent(this, PlayerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_TRACK, track);
            bundle.putSerializable(KEY_LISTA, (ArrayList) trackList);
            searchToPlayer.putExtras(bundle);
            startActivityForResult(searchToPlayer, GO_REPRODUCTOR);
        } else {
            noHayInternetHomeFragment();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GO_REPRODUCTOR) {
            if (data != null) {
                Bundle datos = data.getExtras();
                List<Track> lista = (List<Track>) datos.getSerializable(KEY_LISTA);
                Track track = (Track) datos.getSerializable(KEY_TRACK);
                setReproductorChico(track, lista);
            }
        }
        if(drakePlayer.getMediaPlayer().isPlaying()){
            CreateNotification.createNotification(MainActivity.this, trackSonando,
                    R.drawable.ic_pause_circle_filled_black_24dp,
                    listaDeReproduccion.indexOf(trackSonando), listaDeReproduccion.size() - 1);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drakePlayer.getMediaPlayer() != null) {
            boolean isPlaying = false;
            try {
                isPlaying = drakePlayer.getMediaPlayer().isPlaying();
                actualizarVistaReproductorChico();
                //drakePlayer.crearNotificacion(MainActivity.this, drakePlayer.getTrackActual(), drakePlayer.getTrackList().indexOf(drakePlayer.getTrackActual()), drakePlayer.getTrackList().size() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (isPlaying) {
                reproductorChico.setVisibility(View.VISIBLE);
            } else {
                reproductorChico.setVisibility(View.GONE);
            }
        }
        shakeDetector.start(sensorManager);
    }

    @Override
    public void onClickReintentar() {
        if(Utils.hayInternet(this)){
            setFragmentInicial(new HomeFragment());
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNext() {
        actualizarVistaReproductorChico();
    }

    @Override
    public void onPrev() {
        actualizarVistaReproductorChico();
    }

    @Override
    public void hearShake() {
        if(!Utils.hayInternet(this)){
            return;
        }
        if(drakePlayer.getTrackList() != null && !drakePlayer.getTrackList().isEmpty()){
            int cantTemas = drakePlayer.getTrackList().size();
            Random r = new Random();
            int indiceTemaNuevo = r.nextInt(cantTemas);
            while (indiceTemaNuevo == drakePlayer.getTrackList().indexOf(drakePlayer.getTrackActual())){
                indiceTemaNuevo = r.nextInt(cantTemas);
            }
            try {
                drakePlayer.setPlayerTemaNuevo(this, indiceTemaNuevo);
                actualizarVistaReproductorChico();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}