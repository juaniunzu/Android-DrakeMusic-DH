package com.example.projectointegrador.view;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectointegrador.R;
import com.example.projectointegrador.controller.AlbumController;


public class SearchInputFragment extends Fragment {

    //declarar atributos editText (o searchview), recyclerview y los tres textview
    private SearchView searchView;
    private TextView tvTracks;
    private TextView tvAlbums;
    private TextView tvArtists;
    private RecyclerView rvTracks;
    private RecyclerView rvAlbums;
    private RecyclerView rvArtists;

    public SearchInputFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //linkear los atributos y ponerle onClickListener a los tres textview,
        //los tres listener hacen lo mismo: crean y pegan un SearchDetailFragment y le pasan
        //la data de lo que está escrito en el editText (o searchview) como parametro en el constructor.
        //Segun cual de los tres textview se clickee, el SearchDetailFragment creado tiene que rellenar su lista con una
        //query del tipo correspondiente.

        View view = inflater.inflate(R.layout.fragment_search_input, container, false);

        searchView = view.findViewById(R.id.fragmentSearchInputSearchView);
        tvTracks = view.findViewById(R.id.fragmentSearchInputTextViewTracks);
        tvAlbums = view.findViewById(R.id.fragmentSearchInputTextViewAlbums);
        tvArtists = view.findViewById(R.id.fragmentSearchInputTextViewArtistas);
        rvTracks = view.findViewById(R.id.fragmentSearchInputRecyclerViewTracks);
        rvAlbums = view.findViewById(R.id.fragmentSearchInputRecyclerViewAlbums);
        rvArtists = view.findViewById(R.id.fragmentSearchInputRecyclerViewArtists);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //metodo para obtener la query que introduce el usuario, no hace una mierda pq
                //constantemente hago la consulta en el onQueryTextChange
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //crear un controller de cada tipo que busque y muestre 2 resultados, luego en el
                //onFinish del controller crear y setear adapters y layoutmanagers
                return true;
            }
        });


        return view;
    }
}
