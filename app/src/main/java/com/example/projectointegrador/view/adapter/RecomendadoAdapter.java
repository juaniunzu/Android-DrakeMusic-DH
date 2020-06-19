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
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.Utils;

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
        private TextView textViewArtistaDelTrack;
        private TextView textViewAlbumDelTrack;

        public RecomendadoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewImagenAlbumDelTrack = itemView.findViewById(R.id.celdaRecomendado_ImageviewRecomendado);
            textViewNombreDelTrack = itemView.findViewById(R.id.celdaRecomendado_TextviewNombreRecomendado);
            textViewArtistaDelTrack = itemView.findViewById(R.id.celdaRecomendado_TextViewArtistaRecomendado);
            textViewAlbumDelTrack = itemView.findViewById(R.id.celdaRecomendado_TextViewAlbumRecomendado);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Track track = listaDeTracks.get(getAdapterPosition());
                    List<Track> trackList = listaDeTracks;
                    recomendadoAdapterListener.onClickRecomendadoRecomendadoAdapter(track, trackList);
                }
            });
        }

        public void darValores(Track track) {
            Glide.with(itemView)
                    .setDefaultRequestOptions(Utils.requestOptionsCircularProgressBar(itemView.getContext()))
                    .load(track.getAlbum().getCover())
                    .into(imageViewImagenAlbumDelTrack);
            //imageViewImagenAlbumDelTrack.setImageResource(R.drawable.charizard_tomando_cafe);
            textViewNombreDelTrack.setText(track.getTitle());
            textViewArtistaDelTrack.setText(track.getArtist().getName());
            textViewAlbumDelTrack.setText("Single");
        }
    }
    public interface RecomendadoAdapterListener{
        void onClickRecomendadoRecomendadoAdapter(Track track, List<Track> trackList);
    }
}
