package com.example.projectointegrador.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Track;

import org.w3c.dom.Text;

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

        public UltimosReproducidosViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAlbumDelTrack = itemView.findViewById(R.id.celdaultimosReproducidos_ImageviewTrackAlbum);
            textViewNombreDelTrack = itemView.findViewById(R.id.celdaultimosReproducidos_textviewNombreDelTrack);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Track track = listaDeTracks.get(getAdapterPosition());
                    ultimosReproducidosAdapterListener.onClickUltimosReproducidos(track);
                }
            });
        }

        public void darValores(Track track) {
            imageViewAlbumDelTrack.setImageResource(track.getAlbum().getCover());
            textViewNombreDelTrack.setText(track.getTitle());
        }
    }
    public interface UltimosReproducidosAdapterListener{
        void onClickUltimosReproducidos(Track track);
    }
}
