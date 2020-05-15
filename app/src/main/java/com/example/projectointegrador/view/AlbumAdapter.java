package com.example.projectointegrador.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Album;

import org.w3c.dom.Text;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>{

    private List<Album> listaDeAlbums;

    public AlbumAdapter(List<Album> listaDeAlbums) {
        this.listaDeAlbums = listaDeAlbums;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_album,parent,false);
        AlbumViewHolder albumViewHolderADevolver = new AlbumViewHolder(view);

        return albumViewHolderADevolver;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = listaDeAlbums.get(position);
        holder.darValores(album);
    }

    @Override
    public int getItemCount() {
        return listaDeAlbums.size();
    }

    protected class AlbumViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewImagenAlbum;
        private TextView textViewNombreAlbum;
        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewImagenAlbum = itemView.findViewById(R.id.celdaAlbum_ImageviewAlbum);
            textViewNombreAlbum = itemView.findViewById(R.id.celdaAlbum_TextViewNombreAlbum);
        }
        public void darValores(Album album){
            imageViewImagenAlbum.setImageResource(album.getCover());
            textViewNombreAlbum.setText(album.getTitle());
        }
    }
}
