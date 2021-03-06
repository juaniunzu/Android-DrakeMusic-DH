package com.example.projectointegrador.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectointegrador.controller.ArtistController;
import com.example.projectointegrador.databinding.FragmentArtistasFavoritosBinding;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;
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
        if(Utils.hayInternet(getContext())){
            artistController.getArtistListFavoritos(firebaseUser, new ResultListener<List<Artist>>() {
                @Override
                public void finish(List<Artist> resultado) {
                    ArtistSearchAdapter artistSearchAdapter = new ArtistSearchAdapter(resultado, true, ArtistasFavoritosFragment.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    binding.fragmentArtistasFavoritosRV.setAdapter(artistSearchAdapter);
                    binding.fragmentArtistasFavoritosRV.setLayoutManager(linearLayoutManager);
                }
            });
        } else {
            artistasFavoritosFragmentListener.noHayInternetArtistasFavFragment();
        }

        return view;
    }

    @Override
    public void onClickArtistSearchAdapter(Artist artist) {
        artistasFavoritosFragmentListener.onClickArtistasFavFragment(artist);
    }

    public interface ArtistasFavoritosFragmentListener{
        void onClickArtistasFavFragment(Artist artist);
        void noHayInternetArtistasFavFragment();
    }
}
