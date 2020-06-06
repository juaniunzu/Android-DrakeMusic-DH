package com.example.projectointegrador.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectointegrador.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchDetailFragment extends Fragment {

    //declarar atributos textview y recyclerview, ademas de un string para el constructor

    //hacer constructor con atributo string

    public SearchDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //despues de linkear el textview, setearle el %s con el string atributo que se recibe del fragment anterior

        //la lista que rellene el recyclerview tiene que venir de una busqueda correspondiente a track, album o artista
        //segun en cual de los tres textview anteriores de haya clickeado



        return inflater.inflate(R.layout.fragment_search_detail, container, false);
    }
}
