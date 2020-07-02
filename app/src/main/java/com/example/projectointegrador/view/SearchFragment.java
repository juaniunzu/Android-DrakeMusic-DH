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

import com.example.projectointegrador.controller.HistorialController;
import com.example.projectointegrador.databinding.FragmentSearchBinding;
import com.example.projectointegrador.util.Utils;
import com.example.projectointegrador.view.adapter.HistorialAdapter;
import com.example.projectointegrador.view.adapter.SearchAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchAdapter.SearchAdapterListener {

    private List<Utils.Searchable> searchableList;
    private SearchFragmentListener listener;
    private FirebaseUser firebaseUser;

    private FragmentSearchBinding binding;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //Lista de busquedas
        setBusquedasRecientesList(view);

        //Crea y pega un SearchInputFragment
        binding.fragmentSearchCardViewBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickSearchFragment();
            }
        });

        return view;
    }

    private void setBusquedasRecientesList(View view) {
        HistorialController historialController = new HistorialController();
        historialController.getHistorial(firebaseUser, resultado -> {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            HistorialAdapter historialAdapter = new HistorialAdapter(resultado);
            binding.fragmentSearchRecyclerView.setLayoutManager(linearLayoutManager);
            binding.fragmentSearchRecyclerView.setAdapter(historialAdapter);
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (SearchFragmentListener) context;
    }

    @Override
    public void onClickSearchAdapter(Utils.Searchable searchable) {
        listener.onClickSearchFragment(searchable);
    }

    public interface SearchFragmentListener{
        void onClickSearchFragment(Utils.Searchable searchable);
        void onClickSearchFragment();
    }

    @Override
    public String toString() {
        return "2";
    }
}
