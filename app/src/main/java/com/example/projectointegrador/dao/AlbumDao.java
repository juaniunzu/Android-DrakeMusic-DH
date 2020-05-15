package com.example.projectointegrador.dao;

import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Track;

import java.util.ArrayList;
import java.util.List;

public abstract class AlbumDao {
    public static List<Album> getAlbums() {
        List<Album> albumsADevolver = new ArrayList<>();
        albumsADevolver.add(new Album(407,"Fear of the Dark", R.drawable.iron_maiden_fear_of_the_dark));
        albumsADevolver.add(new Album(401,"Dance of death", R.drawable.iron_maiden_dance_of_death));
        albumsADevolver.add(new Album(820,"Bailando en una Pata", R.drawable.la_renga_bailando_en_una_pata));
        albumsADevolver.add(new Album(502,"Master of Puppets", R.drawable.metallica_master_of_puppets));
        albumsADevolver.add(new Album(920,"Hot Space", R.drawable.queen_hot_space));
        return albumsADevolver;
    }
}
