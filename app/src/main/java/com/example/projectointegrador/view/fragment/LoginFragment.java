package com.example.projectointegrador.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projectointegrador.databinding.FragmentLoginBinding;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginFragmentListener listener;

    public LoginFragment() {

    }

    public LoginFragment(LoginFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.fragmentLoginButtonIniciarSesion.setOnClickListener(v -> listener.onClickLoginFragmentBotonLogin(binding.fragmentLoginTextInputEditTextUsername.getText().toString(), binding.fragmentLoginTextInputEditTextPassword.getText().toString()));

        binding.fragmentLoginBotonLoginDeGoogle.setOnClickListener(v -> listener.onClickLoginFragmentBotonLoginConGoogle());

        binding.fragmentLoginLoginbuttonFacebook.setOnClickListener(v-> listener.onClickLoginFragmentBotonLoginConFacebook(binding.fragmentLoginLoginbuttonFacebook));

        return view;
    }

    public interface LoginFragmentListener {
        void onClickLoginFragmentBotonLogin(String username, String password);

        void onClickLoginFragmentBotonLoginConGoogle();

        void onClickLoginFragmentBotonLoginConFacebook(LoginButton loginButton);
    }
}
