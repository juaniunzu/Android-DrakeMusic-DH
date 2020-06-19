package com.example.projectointegrador.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Track;

import java.util.List;

public class UltimosReproducidosAdapter extends RecyclerView.Adapter<UltimosReproducidosAdapter.UltimosReproducidosViewHolder> {

    private List<Track> listaDeTracks;
    private UltimosReproducidosAdapterListener ultimosReproducidosAdapterListener;

    public UltimosReproducidosAdapter(List<Track> listaDeTracks,UltimosReproducidosAdapterListener listener) {
        this.listaDeTracks = listaDeTracks;
        this.ultimosReproducidosAdapterListener = listener;
    }

    @NonNull
    @Override
    public UltimosReproducidosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_ultimos_reproducidos, parent, false);
        UltimosReproducidosViewHolder ultimosReproducidosViewHolderADevolver = new UltimosReproducidosViewHolder(view);

        return ultimosReproducidosViewHolderADevolver;
    }

    @Override
    public void onBindViewHolder(@NonNull UltimosReproducidosViewHolder holder, int position) {
        Track track = listaDeTracks.get(position);
        holder.darValores(track);
    }

    @Override
    public int getItemCount() {
        return listaDeTracks.size();
    }

    protected class UltimosReproducidosViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewAlbumDelTrack;
        private TextView textViewNombreDelTrack;
        private TextView textViewNombreDelAlbum;
        private TextView textViewNombreDelArtista;

        public UltimosReproducidosViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAlbumDelTrack = itemView.findViewById(R.id.celdaultimosReproducidos_ImageviewTrackAlbum);
            textViewNombreDelTrack = itemView.findViewById(R.id.celdaultimosReproducidos_textviewNombreDelTrack);
            textViewNombreDelAlbum = itemView.findViewById(R.id.celdaUltimosReproducidos_TextViewAlbumRecomendado);
            textViewNombreDelArtista = itemView.findViewById(R.id.celdaUltimosReproducidos_TextViewArtistaRecomendado);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Track track = listaDeTracks.get(getAdapterPosition());
                    List<Track> trackList = listaDeTracks;
                    ultimosReproducidosAdapterListener.onClickUltimosReproducidosAdapter(track, listaDeTracks);
                }
            });
        }

        public void darValores(Track track) {
            // Forma de obtener el id con solo el nombre del drawabale. Esto hace que no Rompa los datos Hardcodeados.
            int id = itemView.getContext().getResources().getIdentifier("drawable/" + track.getAlbum().getCover(), null, itemView.getContext().getPackageName());
            imageViewAlbumDelTrack.setImageResource(id);
            textViewNombreDelTrack.setText(track.getTitle());

        }
    }
    public interface UltimosReproducidosAdapterListener{
        void onClickUltimosReproducidosAdapter(Track track, List<Track> trackList);
    }
}
