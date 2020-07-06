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

import com.example.projectointegrador.controller.HistorialController;
import com.example.projectointegrador.databinding.FragmentSearchBinding;
import com.example.projectointegrador.model.Busqueda;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.util.Utils;
import com.example.projectointegrador.view.adapter.HistorialAdapter;
import com.example.projectointegrador.view.adapter.SearchAdapter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchAdapter.SearchAdapterListener, HistorialAdapter.HistorialAdapterListener {

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
            HistorialAdapter historialAdapter = new HistorialAdapter(resultado, this);
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

    @Override
    public void onClickBusqueda(Busqueda busqueda) {
        listener.onClickHistorialSearchFragment(busqueda);
    }

    @Override
    public void onClickBorrarBusqueda(Busqueda busqueda) {
        HistorialController historialController = new HistorialController();
        historialController.borrarItemHistorial(firebaseUser, getContext(), busqueda, new ResultListener<Task<Void>>() {
            @Override
            public void finish(Task<Void> resultado) {
                historialController.getHistorial(firebaseUser, new ResultListener<List<Busqueda>>() {
                    @Override
                    public void finish(List<Busqueda> resultado) {
                        HistorialAdapter historialAdapter = new HistorialAdapter(resultado, SearchFragment.this);
                        LinearLayoutManager llm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        binding.fragmentSearchRecyclerView.setAdapter(historialAdapter);
                        binding.fragmentSearchRecyclerView.setLayoutManager(llm);
                    }
                });
            }
        });
    }

    public interface SearchFragmentListener{
        void onClickSearchFragment(Utils.Searchable searchable);
        void onClickSearchFragment();
        void onClickHistorialSearchFragment(Busqueda busqueda);

    }

    @Override
    public String toString() {
        return "2";
    }
}
