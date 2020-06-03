package com.example.projectointegrador.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.projectointegrador.R;
import com.example.projectointegrador.databinding.ActivityPlayerBinding;
import com.example.projectointegrador.model.Track;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private ActivityPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewPager = binding.activityPlayerViewPager;
        toolbar = binding.activityPlayerToolbar;

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
            Fragment fragment = PlayerFragment.crearPlayerFragment(track);
            listaADevolver.add(fragment);
        }
        return listaADevolver;
    }

    private void pegarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityPlayerFragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
}
