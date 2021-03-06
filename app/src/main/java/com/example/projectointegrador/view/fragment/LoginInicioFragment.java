package com.example.projectointegrador.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import androidx.fragment.app.Fragment;

import com.example.projectointegrador.databinding.FragmentLoginInicioBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginInicioFragment extends Fragment {

    private FragmentLoginInicioBinding binding;
    private LoginInicioFragmentListener listener;

    public LoginInicioFragment() {
        // Required empty public constructor
    }

    public LoginInicioFragment(LoginInicioFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginInicioBinding.inflate(inflater, container, false);
        final View view = binding.getRoot();


        AlphaAnimation fadeInSlogan = new AlphaAnimation(0.0f, 1.0f);
        binding.fragmentLoginInicioSlogan.startAnimation(fadeInSlogan);
        fadeInSlogan.setDuration(1500);
        fadeInSlogan.setFillAfter(true);
        AlphaAnimation fadeInBotonIniciarSesion = new AlphaAnimation(0.0f, 1.0f);
        AlphaAnimation fadeInBotonRegistrarse = new AlphaAnimation(0.0f, 1.0f);
        fadeInBotonIniciarSesion.setDuration(2000);
        fadeInBotonRegistrarse.setDuration(3000);
        fadeInBotonIniciarSesion.setFillAfter(true);
        fadeInBotonRegistrarse.setFillAfter(true);
        binding.fragmentLoginInicioButtonLogin.startAnimation(fadeInBotonIniciarSesion);
        binding.fragmentLoginInicioButtonRegister.startAnimation(fadeInBotonRegistrarse);




        binding.fragmentLoginInicioButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickLoginInicioBotonIniciarSesion();
            }
        });

        binding.fragmentLoginInicioButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickLoginInicioBotonRegistrarse();
            }
        });


        return view;
    }

    public interface LoginInicioFragmentListener{
        void onClickLoginInicioBotonIniciarSesion();
        void onClickLoginInicioBotonRegistrarse();
    }
}
