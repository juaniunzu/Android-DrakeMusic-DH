package com.example.projectointegrador.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projectointegrador.R;
import com.example.projectointegrador.dao.TrackDao;
import com.example.projectointegrador.model.Track;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeFragment.FragmentHomeListener {

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFindViewsByIds();

        HomeFragment homeFragment = new HomeFragment();
        setFragmentInicial(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavigationView_Menu:
                        pegarFragment(new HomeFragment());
                        break;
                    case R.id.bottomNavigationView_Search:
                        Intent mainASearch = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(mainASearch);
                        break;
                    case R.id.bottomNavigationView_Favorites:
                        Toast.makeText(MainActivity.this, "En Construccion.", Toast.LENGTH_SHORT).show();
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
    public void fragmentOnClickRecomendados(Track track, List<Track> trackList) {

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
    public void fragmentOnClickUltimosReproducidos(Track track, List<Track> trackList) {
        Intent mainAPlayer = new Intent(MainActivity.this, PlayerActivity.class);
        Bundle datos = new Bundle();
        datos.putSerializable("track", track);
        datos.putSerializable("lista", (ArrayList) trackList);
        mainAPlayer.putExtras(datos);
        startActivity(mainAPlayer);
    }

    private void setFragmentInicial(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activityMain_contenedorDeFragments, fragment);
        fragmentTransaction.commit();
    }

    private void pegarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityMain_contenedorDeFragments, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
