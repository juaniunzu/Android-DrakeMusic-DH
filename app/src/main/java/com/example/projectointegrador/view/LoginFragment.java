package com.example.projectointegrador.view;

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

    private static final String EMAIL = "email";
    private static final int RC_SIGN_IN = 1;
    private Button botonLogin;
    private SignInButton botonLoginConGoogle;
    private LoginButton botonLoginConFacebook;
    private TextInputEditText textInputEditTextUsername;
    private TextInputEditText textInputEditTextPassword;
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


        binding.fragmentLoginButtonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickLoginFragmentBotonLogin(binding.fragmentLoginTextInputEditTextUsername.getText().toString(), binding.fragmentLoginTextInputEditTextPassword.getText().toString());
            }
        });

        binding.fragmentLoginBotonLoginDeGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickLoginFragmentBotonLoginConGoogle();
            }
        });

        binding.fragmentLoginLoginbuttonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickLoginFragmentBotonLoginConFacebook(binding.fragmentLoginLoginbuttonFacebook);
            }
        });

        return view;
    }

    private void irAMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    public interface LoginFragmentListener {
        void onClickLoginFragmentBotonLogin(String username, String password);

        void onClickLoginFragmentBotonLoginConGoogle();

        void onClickLoginFragmentBotonLoginConFacebook(LoginButton loginButton);
    }
}
