package com.example.projectointegrador.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.util.Utils;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>{

    private List<Album> listaDeAlbums;
    private AlbumAdapterListener albumAdapterListener;

    public AlbumAdapter(List<Album> listaDeAlbums,AlbumAdapterListener listener) {
        this.listaDeAlbums = listaDeAlbums;
        this.albumAdapterListener = listener;
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
        private TextView textViewNombreArtista;
        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewImagenAlbum = itemView.findViewById(R.id.celdaAlbum_ImageviewAlbum);
            textViewNombreAlbum = itemView.findViewById(R.id.celdaAlbum_TextViewNombreAlbum);
            textViewNombreArtista = itemView.findViewById(R.id.celdaAlbum_TextViewAlbum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Album album = listaDeAlbums.get(getAdapterPosition());
                    albumAdapterListener.onClickAlbumAlbumAdapter(album);
                }
            });
        }
        public void darValores(Album album){
            // Forma de obtener el id con solo el nombre del drawabale. Esto hace que no rompan los datos hardcodeados.
            //int id = itemView.getContext().getResources().getIdentifier("drawable/" + album.getCover(), null, itemView.getContext().getPackageName());
            //imageViewImagenAlbum.setImageResource(id);

            Glide.with(itemView)
                    .setDefaultRequestOptions(Utils.requestOptionsCircularProgressBar(itemView.getContext()))
                    .load(album.getCover())
                    .into(imageViewImagenAlbum);

            textViewNombreAlbum.setText(album.getTitle());
            textViewNombreArtista.setText(album.getArtist().getName());
        }
    }
    public interface AlbumAdapterListener{
        void
        onClickAlbumAlbumAdapter(Album album);
    }
}
