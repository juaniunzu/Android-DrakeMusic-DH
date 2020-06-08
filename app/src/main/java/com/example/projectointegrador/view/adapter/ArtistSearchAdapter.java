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
import com.example.projectointegrador.model.Artist;

import org.w3c.dom.Text;

import java.util.List;

public class ArtistSearchAdapter extends RecyclerView.Adapter<ArtistSearchAdapter.ViewHolderArtistSearch> {

    private List<Artist> artistList;
    private Boolean listacompleta;
    private ArtistSearchAdapterListener artistSearchAdapterListener;

    public ArtistSearchAdapter(List<Artist> artistList, Boolean listacompleta, ArtistSearchAdapterListener artistSearchAdapterListener) {
        this.artistList = artistList;
        this.listacompleta = listacompleta;
        this.artistSearchAdapterListener = artistSearchAdapterListener;
    }

    @NonNull
    @Override
    public ViewHolderArtistSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_search_input_artista, parent, false);
        return new ViewHolderArtistSearch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderArtistSearch holder, int position) {
        Artist artist = this.artistList.get(position);
        holder.onBind(artist);
    }

    @Override
    public int getItemCount() {
        if (artistList != null && listacompleta){
            return artistList.size();
        }
        if (artistList != null && artistList.size() > 1){
            return 2;
        }
        else if (artistList != null && artistList.size() == 1){
            return 1;
        }
        else return 0;
    }

    public class ViewHolderArtistSearch extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tv;

        public ViewHolderArtistSearch(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.celdaSearchInputArtistaImageView);
            tv = itemView.findViewById(R.id.celdaSearchInputArtistaTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Artist artist = artistList.get(getAdapterPosition());
                    artistSearchAdapterListener.onClickArtistSearchAdapter(artist);
                }
            });
        }

        public void onBind(Artist artist) {
            Glide.with(itemView.getContext()).load(artist.getPicture()).into(iv);
            tv.setText(artist.getName());
        }
    }

    public interface ArtistSearchAdapterListener{
        void onClickArtistSearchAdapter (Artist artist);
    }
}
