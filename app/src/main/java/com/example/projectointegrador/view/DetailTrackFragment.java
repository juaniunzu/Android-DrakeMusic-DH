package com.example.projectointegrador.view;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.Utils;

import org.w3c.dom.Text;


public class DetailTrackFragment extends Fragment {

    public static final String KEY_DETAIL_TRACK = "track";

    private ImageView imageViewImagenTrack;
    private TextView textViewNombreDelTrack;
    private TextView textViewDuracionDelTrack;
    private TextView textViewNombreArtistDelTrack;
    private TextView textViewNombreAlbumDelTrack;


    /**
     * Nuevo constructor. Setea el fragment segun el {@param track} parametro
     * @return
     */
    public static DetailTrackFragment crearDetailTrackFragment(Track track){
        DetailTrackFragment fragment = new DetailTrackFragment();
        Bundle datosDeTrack = new Bundle();
        datosDeTrack.putSerializable("track", track);
        fragment.setArguments(datosDeTrack);
        return fragment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_track, container, false);

        findViews(view);

        Bundle bundle = getArguments();
        Track trackRecibido = (Track) bundle.getSerializable(KEY_DETAIL_TRACK);

        setResources(trackRecibido);

        return view;
    }

    /**
     * setea los recursos recibidos del pojo en los elementos xml correspondientes
     * @param trackRecibido
     */
    private void setResources(Track trackRecibido) {
        // Forma de obtener el id con solo el nombre del drawabale (Magia negra)
        //int id = getContext().getResources().getIdentifier("drawable/" + trackRecibido.getAlbum().getCover(), null, getContext().getPackageName());
        //imageViewImagenTrack.setImageResource(id);
        // Esto Puede ir en un if que checke si hay o no Internet y cargar datos hardcodeados en caso de que no haya internet.


        //con este metodo se da la imagen al detalle, solo funciona cuando el detalle que se quiere abrir es
        //de un track que viene de la API, ya que si el track es local la imagen no es un string sino un int guardado
        //en los recursos. Tema a resolver cuando veamos qué se hace con la app en modo avion
        Glide.with(getActivity()).load(trackRecibido.getAlbum().getCover()).into(imageViewImagenTrack);

        textViewNombreDelTrack.setText(String.format(getString(R.string.template_titulo), trackRecibido.getTitle()));
        textViewDuracionDelTrack.setText(String.format(getString(R.string.template_duracion), trackRecibido.getDuration().toString()));
        textViewNombreAlbumDelTrack.setText(String.format(getString(R.string.template_album), trackRecibido.getAlbum().getTitle()));
        textViewNombreArtistDelTrack.setText(String.format(getString(R.string.template_artista), trackRecibido.getArtist().getName()));
    }

    /**
     * setea las views en el fragment detalle
     * @param view
     */
    private void findViews(View view) {
        imageViewImagenTrack = view.findViewById(R.id.fdetailivtrack);
        textViewNombreDelTrack = view.findViewById(R.id.fdetailtvtitulo);
        textViewDuracionDelTrack = view.findViewById(R.id.fdetailtvduracion);
        textViewNombreArtistDelTrack = view.findViewById(R.id.fdetailtvartista);
        textViewNombreAlbumDelTrack = view.findViewById(R.id.fdetailtvalbum);
    }
}
