package com.example.projectointegrador.view.adapter;

import android.bluetooth.le.ScanSettings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.util.Utils;

import org.w3c.dom.Text;

import java.util.List;

public class AlbumSearchAdapter extends RecyclerView.Adapter<AlbumSearchAdapter.ViewHolderAlbumSearch> {

    private List<Album> albumList;
    private Boolean listacompleta;
    private AlbumSearchAdapterListener albumSearchAdapterListener;

    public AlbumSearchAdapter(List<Album> albumList, Boolean listacompleta, AlbumSearchAdapterListener albumSearchAdapterListener) {
        this.albumList = albumList;
        this.listacompleta = listacompleta;
        this.albumSearchAdapterListener = albumSearchAdapterListener;
    }

    @NonNull
    @Override
    public ViewHolderAlbumSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_search_input_album, parent, false);
        return new ViewHolderAlbumSearch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAlbumSearch holder, int position) {
        Album album = this.albumList.get(position);
        holder.onBind(album);
    }

    @Override
    public int getItemCount() {

        if (albumList != null && listacompleta){
            return albumList.size();
        }
        if (albumList != null && albumList.size() > 1){
            return 2;
        }
        else if (albumList != null && albumList.size() == 1){
            return 1;
        }
        else return 0;
    }

    public class ViewHolderAlbumSearch extends RecyclerView.ViewHolder{

        private ImageView iv;
        private TextView tvtitulo;
        private TextView tvartista;

        public ViewHolderAlbumSearch(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.celdaSearchInputAlbumImageView);
            tvtitulo = itemView.findViewById(R.id.celdaSearchInputAlbumTextViewTitulo);
            tvartista = itemView.findViewById(R.id.celdaSearchInputAlbumTextViewArtista);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Album album = albumList.get(getAdapterPosition());
                    albumSearchAdapterListener.onClickAlbumSearchAdapter(album);
                }
            });
        }

        public void onBind(Album album) {

            Glide.with(itemView.getContext())
                    .setDefaultRequestOptions(Utils.requestOptionsCircularProgressBar(itemView.getContext()))
                    .load(album.getCover())
                    .into(iv);

            tvtitulo.setText(album.getTitle());
            tvartista.setText(album.getArtist().getName());
        }
    }

    public interface AlbumSearchAdapterListener{
        void onClickAlbumSearchAdapter (Album album);
    }
}
