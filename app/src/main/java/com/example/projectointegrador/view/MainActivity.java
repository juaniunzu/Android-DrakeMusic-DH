package com.example.projectointegrador.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;

import com.example.projectointegrador.R;
import com.example.projectointegrador.dao.TrackDao;
import com.example.projectointegrador.model.Track;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeFragment.FragmentHomeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragment homeFragment = new HomeFragment();

        setFragmentInicial(homeFragment);

    }


    @Override
    public void fragmentOnClickRecomendados(Track track, List<Track> trackList) {

        //al momento de hacer click en un track de recomendados, me traigo tanto el track clickeado
        //como la lista completa perteneciente al adapter. me la llevo a una nueva activity donde
        //esta el viewpager que se encargara de crear una lista de fragments con cada track de
        //la lista inicial
        Intent mainADetail = new Intent(MainActivity.this, DetailActivity.class);
        Bundle datos = new Bundle();
        datos.putSerializable("track", track);
        datos.putSerializable("lista", (ArrayList)trackList);
        mainADetail.putExtras(datos);
        startActivity(mainADetail);

    }

    @Override
    public void fragmentOnClickUltimosReproducidos(Track track) {
        DetailTrackFragment detailTrackFragment = DetailTrackFragment.crearDetailTrackFragment(track);
        pegarFragment(detailTrackFragment);
    }

    private void setFragmentInicial(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activityMain_contenedorDeFragments, fragment);
        fragmentTransaction.commit();
    }
    private void pegarFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityMain_contenedorDeFragments, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
