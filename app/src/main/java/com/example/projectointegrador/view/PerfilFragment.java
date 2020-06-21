package com.example.projectointegrador.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectointegrador.R;
import com.example.projectointegrador.databinding.FragmentPerfilBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private ImageView fotoPerfil;
    private TextView nombreUsuario;
    private LinearLayout favoritos;
    private LinearLayout cerrarSesion;
    private FragmentPerfilBinding binding;
    private PerfilFragmentListener listener;

    public PerfilFragment() {
        // Required empty public constructor
    }

    public PerfilFragment(PerfilFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        findViews();

        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickFavoritos();
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickCerrarSesion();
            }
        });


        return view;
    }

    private void findViews() {
        fotoPerfil = binding.fragmentPerfilImageViewUsuario;
        nombreUsuario = binding.fragmentPerfilTextViewNombreUsuario;
        favoritos = binding.fragmentPerfilLinearLayoutFavoritos;
        cerrarSesion = binding.fragmentPerfilLinearLayoutCerrarSesion;
    }

    public interface PerfilFragmentListener{
        void onClickFavoritos();
        void onClickCerrarSesion();
    }

}
