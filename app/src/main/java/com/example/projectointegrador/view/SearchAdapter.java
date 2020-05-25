package com.example.projectointegrador.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.R;
import com.example.projectointegrador.util.Utils;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Utils.Searchable> listaSearch;
    private SearchAdapterListener listener;

    public SearchAdapter(List<Utils.Searchable> listaSearch, SearchAdapterListener listener) {
        this.listaSearch = listaSearch;
        this.listener = listener;
    }



    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.celda_search, parent, false);

        return (new SearchViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        Utils.Searchable searchable = this.listaSearch.get(position);
        holder.onBind(searchable);

    }

    @Override
    public int getItemCount() {
        return this.listaSearch.size();
    }

    protected class SearchViewHolder extends RecyclerView.ViewHolder{

        private ImageView celdaSearchImageView;
        private TextView celdaSearchTextViewTitulo;
        private TextView celdaSearchTextViewDescripcion;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            celdaSearchImageView = itemView.findViewById(R.id.celdaSearchImageView);
            celdaSearchTextViewTitulo = itemView.findViewById(R.id.celdaSearchTextViewTitulo);
            celdaSearchTextViewDescripcion = itemView.findViewById(R.id.celdaSearchTextViewDescripcion);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.Searchable searchable = listaSearch.get(getAdapterPosition());
                    listener.onClickSearchAdapter(searchable);
                }
            });
        }
        public void onBind(Utils.Searchable searchable){
            Glide.with(itemView.getContext())
                    .load(searchable.informarImagen())
                    .into(celdaSearchImageView);
            celdaSearchTextViewTitulo.setText(searchable.informarTitulo());
            celdaSearchTextViewDescripcion.setText(searchable.informarDescripcion());
        }
    }

    public interface SearchAdapterListener{
        void onClickSearchAdapter(Utils.Searchable searchable);
    }
}
