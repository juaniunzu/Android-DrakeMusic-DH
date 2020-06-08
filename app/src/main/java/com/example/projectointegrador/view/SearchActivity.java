package com.example.projectointegrador.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projectointegrador.R;
import com.example.projectointegrador.util.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.projectointegrador.view.SearchDetailFragment.KEY_QUERY;
import static com.example.projectointegrador.view.SearchDetailFragment.KEY_TYPE;

public class SearchActivity extends AppCompatActivity implements SearchFragment.SearchFragmentListener, SearchInputFragment.SearchInputFragmentListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setFragmentInicial(new SearchFragment());

        //TODO USAR BINDING
        bottomNavigationView = findViewById(R.id.activitySearch_BottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottomNavigationView_Search);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottomNavigationView_Menu:
                        Intent searchAMain = new Intent(SearchActivity.this, MainActivity.class);
                        startActivity(searchAMain);
                        break;
                    case R.id.bottomNavigationView_Search:
                        setFragmentInicial(new SearchFragment());
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
    private void setFragmentInicial(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activitySearchFragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    private void setFragmentReplace(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activitySearchFragmentContainer, fragment);
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
        Fragment fragment = new SearchDetailFragment();
        fragment.setArguments(bundle);
        setFragmentInicial(fragment);
    }

    @Override
    public void onClickFiltroVerTodo(String query, String type) {
        pegarSearchDetailFragment(query,type);
    }
}