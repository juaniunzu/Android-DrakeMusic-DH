package com.example.projectointegrador.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectointegrador.R;
import com.example.projectointegrador.dao.SearchableDao;
import com.example.projectointegrador.util.Utils;
import com.example.projectointegrador.view.adapter.SearchAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchAdapter.SearchAdapterListener {

    private List<Utils.Searchable> searchableList;
    private RecyclerView fragmentSearchRecyclerView;
    private SearchFragmentListener listener;
    private CardView cardView;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        //Setear OnClickListener a este cardview que cree y pegue un SearchInputFragment
        cardView = view.findViewById(R.id.fragmentSearchCardViewBuscar);

        //esta lista por el momento se llena con datos hardcodeados, en un futuro tiene que recibir data de Firebase
        setBusquedasRecientesList(view);


        return view;
    }

    private void setBusquedasRecientesList(View view) {
        searchableList = SearchableDao.getSearchables();
        fragmentSearchRecyclerView = view.findViewById(R.id.fragmentSearchRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        SearchAdapter searchAdapter = new SearchAdapter(searchableList, this);

        fragmentSearchRecyclerView.setAdapter(searchAdapter);
        fragmentSearchRecyclerView.setLayoutManager(linearLayoutManager);
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
    }
}
