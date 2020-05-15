package com.example.projectointegrador.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Artist;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistaViewHolder> {

    private List<Artist> listaDeArtistas;

    public ArtistAdapter(List<Artist> listaDeArtistas) {
        this.listaDeArtistas = listaDeArtistas;
    }

    @NonNull
    @Override
    public ArtistaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_artista, parent, false);
        ArtistaViewHolder artistaViewHolderADevolver = new ArtistaViewHolder(view);

        return artistaViewHolderADevolver;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistaViewHolder holder, int position) {
        Artist artist = listaDeArtistas.get(position);
        holder.darValores(artist);
    }

    @Override
    public int getItemCount() {
        return listaDeArtistas.size();
    }

    protected class ArtistaViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewImagenArtist;
        private TextView textViewNombreArtist;

        public ArtistaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewImagenArtist = itemView.findViewById(R.id.celdaArtista_ImageviewArtista);
            textViewNombreArtist = itemView.findViewById(R.id.celdaArtista_TextviewNombreArtista);
        }

        public void darValores(Artist artist) {
            imageViewImagenArtist.setImageResource(artist.getPicture());
            textViewNombreArtist.setText(artist.getName());
        }
    }
}
