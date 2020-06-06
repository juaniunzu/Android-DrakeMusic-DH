package com.example.projectointegrador.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectointegrador.R;


public class SearchInputFragment extends Fragment {

    //declarar atributos editText (o searchview), recyclerview y los tres textview

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



        return inflater.inflate(R.layout.fragment_search_input, container, false);
    }
}
