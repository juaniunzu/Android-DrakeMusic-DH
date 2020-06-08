package com.example.projectointegrador.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Track;

import java.util.List;

public class TrackSearchAdapter extends RecyclerView.Adapter<TrackSearchAdapter.ViewHolderTrackSearch> {

    private List<Track> trackList;
    private Boolean listacompleta;
    private TrackSearchAdapterListener trackSearchAdapterListener;

    public TrackSearchAdapter(List<Track> trackList, Boolean listacompleta, TrackSearchAdapterListener trackSearchAdapterListener) {
        this.trackList = trackList;
        this.listacompleta = listacompleta;
        this.trackSearchAdapterListener = trackSearchAdapterListener;
    }

    @NonNull
    @Override
    public ViewHolderTrackSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_search_input_track, parent, false);
        return new ViewHolderTrackSearch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTrackSearch holder, int position) {
        Track track = this.trackList.get(position);
        holder.onBind(track);
    }

    @Override
    public int getItemCount() {
        if (trackList != null && listacompleta){
            return trackList.size();
        }
        if (trackList != null && trackList.size() > 1){
            return 2;
        }
        else if (trackList != null && trackList.size() == 1) {
            return 1;
        }
        else return 0;
    }

    public class ViewHolderTrackSearch extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tvnombre;
        private TextView tvalbum;
        private TextView tvartista;

        public ViewHolderTrackSearch(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.celdaSearchInputTrackImageView);
            tvnombre = itemView.findViewById(R.id.celdaSearchInputTrackTextViewTitulo);
            tvalbum = itemView.findViewById(R.id.celdaSearchInputTrackTextViewAlbum);
            tvartista = itemView.findViewById(R.id.celdaSearchInputTrackTextViewArtista);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Track track = trackList.get(getAdapterPosition());
                    trackSearchAdapterListener.onClickTrackSearchAdapter(track,trackList);
                }
            });
        }

        public void onBind(Track track) {
            Glide.with(itemView.getContext()).load(track.getAlbum().getCover()).into(iv);
            tvnombre.setText(track.getTitle());
            tvalbum.setText(track.getAlbum().getTitle());
            tvartista.setText(track.getArtist().getName());
        }
    }

    public interface TrackSearchAdapterListener{
        void onClickTrackSearchAdapter (Track track, List<Track> trackList);
    }
}
