package com.example.projectointegrador.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projectointegrador.databinding.FragmentSignUpBinding;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private Button botonSignup;
    private SignInButton botonSignupConGoogle;
    private LoginButton botonSignupConFacebook;
    private TextInputEditText textInputEditTextUsername;
    private TextInputEditText textInputEditTextPassword;
    private FragmentSignUpBinding binding;
    private SignUpFragmentListener listener;


    public SignUpFragment() {
        // Required empty public constructor
    }

    public SignUpFragment(SignUpFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.fragmentSignupButtonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickSignUpFragmentBotonRegistrarse(binding.fragmentSignupTextInputEditTextUsername.getText().toString(), binding.fragmentSignupTextInputEditTextPassword.getText().toString());
            }
        });

        binding.fragmentSignupBotonSignupDeGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickSignUpFragmentBotonSignUpGoogle(botonSignupConGoogle);
            }
        });

        binding.fragmentSignupSignupButtonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickSignUpFragmentBotonSignUpFacebook(binding.fragmentSignupSignupButtonFacebook);
            }
        });


        return view;
    }

    private void setViews() {

    }

    public interface SignUpFragmentListener {
        void onClickSignUpFragmentBotonSignUpGoogle(SignInButton button);

        void onClickSignUpFragmentBotonSignUpFacebook(LoginButton button);

        void onClickSignUpFragmentBotonRegistrarse(String username, String password);
    }
}
