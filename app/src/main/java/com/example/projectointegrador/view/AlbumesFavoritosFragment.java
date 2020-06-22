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
import com.example.projectointegrador.controller.AlbumController;
import com.example.projectointegrador.databinding.FragmentAlbumesFavoritosBinding;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.view.adapter.AlbumSearchAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumesFavoritosFragment extends Fragment implements AlbumSearchAdapter.AlbumSearchAdapterListener {

    private FragmentAlbumesFavoritosBinding binding;
    private RecyclerView recyclerView;
    private FirebaseUser currentUser;
    private AlbumesFavoritosFragmentListener listener;


    public AlbumesFavoritosFragment() {
        // Required empty public constructor
    }

    public AlbumesFavoritosFragment(AlbumesFavoritosFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        binding = FragmentAlbumesFavoritosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        AlbumController albumController = new AlbumController();
        albumController.getAlbumListFavoritos(currentUser, new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> resultado) {
                AlbumSearchAdapter adapter = new AlbumSearchAdapter(resultado, true, AlbumesFavoritosFragment.this);
                LinearLayoutManager llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                binding.fragmentAlbumesFavoritosRV.setAdapter(adapter);
                binding.fragmentAlbumesFavoritosRV.setLayoutManager(llm);
            }
        });




        return view;
    }

    @Override
    public void onClickAlbumSearchAdapter(Album album) {
        listener.onClickAlbumFavFragment(album);
    }


    public interface AlbumesFavoritosFragmentListener{
        void onClickAlbumFavFragment(Album album);
    }

}
