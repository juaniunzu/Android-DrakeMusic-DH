package com.example.projectointegrador.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.R;
import com.example.projectointegrador.databinding.FragmentPerfilBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private FirebaseUser currentUser;

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

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

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

        nombreUsuario.setText(currentUser.getEmail());
        if(currentUser.getPhotoUrl() != null){
            Glide.with(getContext()).load(currentUser.getPhotoUrl()).into(fotoPerfil);
        }

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (PerfilFragmentListener) context;
    }
}
