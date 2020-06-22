package com.example.projectointegrador.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectointegrador.R;
import com.example.projectointegrador.controller.ArtistController;
import com.example.projectointegrador.databinding.FragmentArtistasFavoritosBinding;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.view.adapter.AlbumSearchAdapter;
import com.example.projectointegrador.view.adapter.ArtistSearchAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistasFavoritosFragment extends Fragment implements ArtistSearchAdapter.ArtistSearchAdapterListener {

    private FragmentArtistasFavoritosBinding binding;
    private FirebaseUser firebaseUser;
    private ArtistasFavoritosFragmentListener artistasFavoritosFragmentListener;


    public ArtistasFavoritosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.artistasFavoritosFragmentListener = (ArtistasFavoritosFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentArtistasFavoritosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //artistasFavoritosFragmentListener = (ArtistasFavoritosFragmentListener) super.getContext();

        ArtistController artistController = new ArtistController();
        artistController.getArtistListFavoritos(firebaseUser, new ResultListener<List<Artist>>() {
            @Override
            public void finish(List<Artist> resultado) {
                ArtistSearchAdapter artistSearchAdapter = new ArtistSearchAdapter(resultado, true, ArtistasFavoritosFragment.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                binding.fragmentArtistasFavoritosRV.setAdapter(artistSearchAdapter);
                binding.fragmentArtistasFavoritosRV.setLayoutManager(linearLayoutManager);
            }
        });
        return view;
    }

    @Override
    public void onClickArtistSearchAdapter(Artist artist) {
        artistasFavoritosFragmentListener.onClickArtistasFavFragment(artist);
    }

    public interface ArtistasFavoritosFragmentListener{
        void onClickArtistasFavFragment(Artist artist);
    }
}
