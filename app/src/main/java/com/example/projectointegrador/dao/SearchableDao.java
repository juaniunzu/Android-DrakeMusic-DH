package com.example.projectointegrador.dao;

import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class SearchableDao {

    public static List<Utils.Searchable> getSearchables(){
        List<Utils.Searchable> listaADevolver = new ArrayList<>();
        listaADevolver.add(new Album(407,"Fear of the Dark", "https://i1.wp.com/www.scienceofnoise.net/wp-content/uploads/2020/04/Iron-Maiden_Iron-Maiden.jpg"));
        listaADevolver.add(new Album(502,"Master of Puppets", "https://i2.wp.com/hipersonica.com/wp-content/uploads/2019/08/3986151.jpeg"));
        listaADevolver.add(new Artist(200,"Eminem", "https://images.clarin.com/2020/02/26/eminem-y-su-challenger-mas___E9xdWT_h_0x750__1.jpg"));
        listaADevolver.add(new Artist(208,"Queen", "https://www.biografiasyvidas.com/biografia/q/fotos/queen.jpg"));
        listaADevolver.add(new Track(1,"Dance of Death",400,new Artist(202,"Iron Maiden", "iron_maiden"),new Album(401,"Dance of death", "https://icarusmusicstore.com/1013-large_default/iron-maiden-dance-of-death-cd-.jpg")));
        listaADevolver.add(new Track(2,"Fear of the Dark",600,new Artist(202,"Iron Maiden", "iron_maiden"),new Album(407,"Fear of the Dark", "https://i1.wp.com/www.scienceofnoise.net/wp-content/uploads/2020/04/Iron-Maiden_Iron-Maiden.jpg")));

        return listaADevolver;
    }
}
