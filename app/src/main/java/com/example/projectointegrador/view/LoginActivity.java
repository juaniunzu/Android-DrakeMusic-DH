package com.example.projectointegrador.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.R;
import com.example.projectointegrador.databinding.ActivityLoginBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener,
        LoginInicioFragment.LoginInicioFragmentListener, SignUpFragment.SignUpFragmentListener {

    private static final String EMAIL = "email";
    private static final int RC_SIGN_IN = 1;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        Glide.with(this).asGif().load("https://im6.ezgif.com/tmp/ezgif-6-1cdb0b8b0782.gif").into(binding.imagenFondo);

        pegarFragmentInicial(new LoginInicioFragment(this));

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        callbackManager = CallbackManager.Factory.create();


    }
    private void pegarFragmentInicial(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activityLogin_fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
    private void pegarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityLogin_fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        //Actualizar la UI
        updateUIGoogle(account);
        updateUIFacebook(isLoggedInOnFacebook());
    }

    private void updateUIGoogle(GoogleSignInAccount account) {
        if (account != null) {
            //Si estas Logueado con Google hace esto.
            pasarALaMainActivityMatandoActividadActual();
        }
    }

    private void updateUIFacebook(Boolean isLoggedInOnFacebook) {
        if (isLoggedInOnFacebook) {
            pasarALaMainActivityMatandoActividadActual();
        }
    }

    private void pasarALaMainActivityMatandoActividadActual() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

        // LoginManager.getInstance().logOut();

    }

    private boolean isLoggedInOnFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUIGoogle(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("GOOGLE", "signInResult:failed code=" + e.getStatusCode());
            updateUIGoogle(null);
        }
    }

    @Override
    public void onClickLoginInicioBotonIniciarSesion() {
        //pega un fragment LoginFragment
        pegarFragment(new LoginFragment(this));
    }

    @Override
    public void onClickLoginInicioBotonRegistrarse() {
        //pega un fragment SignUpFragment
        pegarFragment(new SignUpFragment(this));
    }

    @Override
    public void onClickLoginFragmentBotonLogin(String username, String password) {
        //cuando tengamos firebase authentication, se toca este modulo
        pasarALaMainActivityMatandoActividadActual();
    }

    @Override
    public void onClickLoginFragmentBotonLoginConGoogle() {
        signIn();
    }

    @Override
    public void onClickLoginFragmentBotonLoginConFacebook(LoginButton loginButton) {

        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                pasarALaMainActivityMatandoActividadActual();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }


    @Override
    public void onClickSignUpFragmentBotonSignUpGoogle(SignInButton button) {
        //editar, esto es crear cuenta con google por 1era vez
        pasarALaMainActivityMatandoActividadActual();
    }

    @Override
    public void onClickSignUpFragmentBotonSignUpFacebook(LoginButton button) {
        //editar, esto es crear cuenta con facebook por 1era vez
        pasarALaMainActivityMatandoActividadActual();
    }

    @Override
    public void onClickSignUpFragmentBotonRegistrarse(String username, String password) {
        //editar, esto es crear cuenta con datos, cuando tengamos firebase authentication, se toca este modulo
        pasarALaMainActivityMatandoActividadActual();
    }

//    Metodo para conseguir la hashkey.
//    public void getHashkey() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.example.projectointegrador",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
//    }
}

