package com.example.projectointegrador.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectointegrador.R;
import com.example.projectointegrador.dao.SearchableDao;
import com.example.projectointegrador.databinding.FragmentSearchBinding;
import com.example.projectointegrador.util.Utils;
import com.example.projectointegrador.view.adapter.SearchAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchAdapter.SearchAdapterListener {

    private List<Utils.Searchable> searchableList;
    private SearchFragmentListener listener;

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

        //esta lista por el momento se llena con datos hardcodeados, en un futuro tiene que recibir data de Firebase
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
        
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        SearchAdapter searchAdapter = new SearchAdapter(searchableList, this);
//
//        binding.fragmentSearchRecyclerView.setAdapter(searchAdapter);
//        binding.fragmentSearchRecyclerView.setLayoutManager(linearLayoutManager);
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
