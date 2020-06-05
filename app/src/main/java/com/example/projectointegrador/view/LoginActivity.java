package com.example.projectointegrador.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.projectointegrador.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
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

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static final String EMAIL = "email";
    private static final int RC_SIGN_IN = 1;
    private Button buttonLogin;
    private TextInputEditText textInputEditTextUsername;
    private TextInputEditText textInputEditTextPassword;
    private SignInButton botonLogearConGoogle;
    private GoogleSignInClient mGoogleSignInClient;
    private LoginButton loginButtonGoogle;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setFindViewByIds();
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        botonLogearConGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        callbackManager = CallbackManager.Factory.create();

        loginButtonGoogle.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButtonGoogle.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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

    private void setFindViewByIds() {
        buttonLogin = findViewById(R.id.activityLogin_ButtonIniciarSesion);
        botonLogearConGoogle = findViewById(R.id.activityLogin_BotonLoginDeGoogle);
        loginButtonGoogle = findViewById(R.id.activityLogin_loginbuttonFacebook);
        textInputEditTextUsername = findViewById(R.id.activityLogin_TextInputEditTextUsername);
        textInputEditTextPassword = findViewById(R.id.activityLogin_TextInputEditTextPassword);
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

