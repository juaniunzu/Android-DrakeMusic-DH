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

import java.util.List;

public class RecomendadoAdapter extends RecyclerView.Adapter<RecomendadoAdapter.RecomendadoViewHolder> {

    private List<Track> listaDeTracks;
    private RecomendadoAdapterListener recomendadoAdapterListener;

    public RecomendadoAdapter(List<Track> listaDeTracks,RecomendadoAdapterListener listener) {
        this.listaDeTracks = listaDeTracks;
        this.recomendadoAdapterListener = listener;
    }

    @NonNull
    @Override
    public RecomendadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_recomendado,parent,false);
        RecomendadoViewHolder recomendadoViewHolderADevolver = new RecomendadoViewHolder(view);

        return recomendadoViewHolderADevolver;
    }

    @Override
    public void onBindViewHolder(@NonNull RecomendadoViewHolder holder, int position) {
        Track track = listaDeTracks.get(position);
        holder.darValores(track);
    }

    @Override
    public int getItemCount() {
        return listaDeTracks.size();
    }

    protected class RecomendadoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewImagenAlbumDelTrack;
        private TextView textViewNombreDelTrack;

        public RecomendadoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewImagenAlbumDelTrack = itemView.findViewById(R.id.celdaRecomendado_ImageviewRecomendado);
            textViewNombreDelTrack = itemView.findViewById(R.id.celdaRecomendado_TextviewNombreRecomendado);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Track track = listaDeTracks.get(getAdapterPosition());
                    recomendadoAdapterListener.adapterRecomendadoOnClickRecomendados(track);
                }
            });
        }

        public void darValores(Track track) {
            imageViewImagenAlbumDelTrack.setImageResource(track.getAlbum().getCover());
            textViewNombreDelTrack.setText(track.getTitle());
        }
    }
    public interface RecomendadoAdapterListener{
        void adapterRecomendadoOnClickRecomendados(Track track);
    }
}
