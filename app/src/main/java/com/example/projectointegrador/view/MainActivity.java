package com.example.projectointegrador.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.icu.util.BuddhistCalendar;
import android.os.Bundle;

import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Track;

public class MainActivity extends AppCompatActivity implements HomeFragment.FragmentHomeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragmentInicial(new HomeFragment());
    }


    @Override
    public void fragmentOnClickRecomendados(Track track) {
        DetailTrackFragment detailTrackFragment = new DetailTrackFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailTrackFragment.KEY_DETAIL_TRACK,track);
        detailTrackFragment.setArguments(bundle);
        pegarFragment(detailTrackFragment);
    }

    @Override
    public void fragmentOnClickUltimosReproducidos(Track track) {
        DetailTrackFragment detailTrackFragment = new DetailTrackFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailTrackFragment.KEY_DETAIL_TRACK,track);
        detailTrackFragment.setArguments(bundle);
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
