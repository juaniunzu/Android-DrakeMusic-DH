package com.example.projectointegrador.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.projectointegrador.R;
import com.example.projectointegrador.util.Utils;

public class SearchActivity extends AppCompatActivity implements SearchFragment.SearchFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setFragmentInicial(new SearchFragment());

    }

    /**
     * setea fragment inicial en el onCreate de la actividad
     * @param fragment
     */
    private void setFragmentInicial(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activitySearchFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClickSearchFragment(Utils.Searchable searchable) {

    }
}
