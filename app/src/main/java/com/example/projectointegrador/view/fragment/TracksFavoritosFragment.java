package com.example.projectointegrador.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectointegrador.controller.TrackController;
import com.example.projectointegrador.databinding.FragmentTracksFavoritosBinding;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.view.adapter.TrackSearchAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TracksFavoritosFragment extends Fragment implements TrackSearchAdapter.TrackSearchAdapterListener {

    private FragmentTracksFavoritosBinding binding;
    private FirebaseUser firebaseUser;
    private TracksFavoritosFragmentListener listener;

    public TracksFavoritosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (TracksFavoritosFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTracksFavoritosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //listener = (TracksFavoritosFragmentListener) super.getContext();

        TrackController trackController = new TrackController();
        trackController.getTrackListFavoritos(firebaseUser, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> resultado) {
                TrackSearchAdapter trackSearchAdapter = new TrackSearchAdapter(resultado, true, TracksFavoritosFragment.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
                binding.fragmentTracksFavoritosRV.setAdapter(trackSearchAdapter);
                binding.fragmentTracksFavoritosRV.setLayoutManager(linearLayoutManager);
            }
        });
        return view;
    }

    @Override
    public void onClickTrackSearchAdapter(Track track, List<Track> trackList) {
        listener.onClickTracksFavFragment(track,trackList);
    }

    public interface TracksFavoritosFragmentListener{
        void onClickTracksFavFragment(Track track, List<Track> trackList);
    }
}
