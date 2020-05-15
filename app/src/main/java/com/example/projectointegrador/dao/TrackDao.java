package com.example.projectointegrador.dao;

import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Track;

import java.util.ArrayList;
import java.util.List;

public abstract class TrackDao {
    public static List<Track> getUltimosReproducidos(){
        List<Track> tracksADevolver = new ArrayList<>();

        tracksADevolver.add(new Track(1,"Dance of Death",400,new Artist(202,"Iron Maiden", R.drawable.iron_maiden),new Album(401,"Dance of death", R.drawable.iron_maiden_dance_of_death)));
        tracksADevolver.add(new Track(2,"Fear of the Dark",600,new Artist(202,"Iron Maiden", R.drawable.iron_maiden),new Album(407,"Fear of the Dark", R.drawable.iron_maiden_fear_of_the_dark)));
        tracksADevolver.add(new Track(3,"Master of Puppets",520,new Artist(206,"Metallica", R.drawable.metallica),new Album(502,"Master of Puppets", R.drawable.metallica_master_of_puppets)));
        tracksADevolver.add(new Track(4,"Nacido para ser Salvaje",320,new Artist(204,"La Renga", R.drawable.la_renga),new Album(820,"Bailando en una Pata", R.drawable.la_renga_bailando_en_una_pata)));
        tracksADevolver.add(new Track(5,"Staying Power",430,new Artist(208,"Queen", R.drawable.queen),new Album(920,"Hot Space", R.drawable.queen_hot_space)));


        return tracksADevolver;
    }
    public static List<Track> getRecomendados(){
        List<Track> tracksADevolver = new ArrayList<>();

        tracksADevolver.add(new Track(1,"Dance of Death",400,new Artist(202,"Iron Maiden", R.drawable.iron_maiden),new Album(401,"Dance of death", R.drawable.iron_maiden_dance_of_death)));
        tracksADevolver.add(new Track(2,"Fear of the Dark",600,new Artist(202,"Iron Maiden", R.drawable.iron_maiden),new Album(407,"Fear of the Dark", R.drawable.iron_maiden_fear_of_the_dark)));
        tracksADevolver.add(new Track(3,"Master of Puppets",520,new Artist(206,"Metallica", R.drawable.metallica),new Album(502,"Master of Puppets", R.drawable.metallica_master_of_puppets)));
        tracksADevolver.add(new Track(4,"Nacido para ser Salvaje",320,new Artist(204,"La Renga", R.drawable.la_renga),new Album(820,"Bailando en una Pata", R.drawable.la_renga_bailando_en_una_pata)));
        tracksADevolver.add(new Track(5,"Staying Power",430,new Artist(208,"Queen", R.drawable.queen),new Album(920,"Hot Space", R.drawable.queen_hot_space)));


        return tracksADevolver;
    }
}
