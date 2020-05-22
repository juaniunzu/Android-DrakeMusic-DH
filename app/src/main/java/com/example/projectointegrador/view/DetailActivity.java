package com.example.projectointegrador.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Track;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = findViewById(R.id.activityDetailToolbar);
        viewPager = findViewById(R.id.activityDetailViewPager);

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("");
        }

        Intent desdeMain = getIntent();
        Bundle datosDesdeMain = desdeMain.getExtras();
        Track trackClickeado = (Track) datosDesdeMain.getSerializable("track");
        ArrayList<Track> trackArrayList = (ArrayList<Track>) datosDesdeMain.getSerializable("lista");
        List<Fragment> listaFragments = generarFragments(trackArrayList);

        Integer indice = trackArrayList.indexOf(trackClickeado);


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), listaFragments);

        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setCurrentItem(indice);

    }

    private List<Fragment> generarFragments(List<Track> listaDeTracks){
        List<Fragment> listaADevolver = new ArrayList<>();
        for (Track track : listaDeTracks) {
            Fragment fragment = DetailTrackFragment.crearDetailTrackFragment(track);
            listaADevolver.add(fragment);
        }
        return listaADevolver;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
}
