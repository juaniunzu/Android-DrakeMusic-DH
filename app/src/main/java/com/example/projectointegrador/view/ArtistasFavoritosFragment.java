package com.example.projectointegrador.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectointegrador.R;
import com.example.projectointegrador.databinding.FragmentArtistasFavoritosBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistasFavoritosFragment extends Fragment {

    private FragmentArtistasFavoritosBinding binding;

    public ArtistasFavoritosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentArtistasFavoritosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment
        return view;
    }
}
