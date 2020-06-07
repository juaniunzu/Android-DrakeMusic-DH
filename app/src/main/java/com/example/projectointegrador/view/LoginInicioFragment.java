package com.example.projectointegrador.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projectointegrador.databinding.FragmentLoginInicioBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginInicioFragment extends Fragment {

    private FragmentLoginInicioBinding binding;
    private Button iniciarSesion;
    private Button registrarse;
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
        View view = binding.getRoot();

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
