package com.example.projectointegrador.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.R;
import com.example.projectointegrador.databinding.ActivityLoginBinding;
import com.example.projectointegrador.util.Utils;
import com.example.projectointegrador.view.fragment.LoginFragment;
import com.example.projectointegrador.view.fragment.LoginInicioFragment;
import com.example.projectointegrador.view.fragment.NoInetFragment;
import com.example.projectointegrador.view.fragment.SignUpFragment;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener,
        LoginInicioFragment.LoginInicioFragmentListener, SignUpFragment.SignUpFragmentListener {

    private static final String TAG = "FIREBASE";
    private static final String EMAIL = "email";
    private static final int RC_SIGN_IN = 1;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //getHashkey();


        Glide.with(this).asGif().load(R.drawable.giffondologin).into(binding.imagenFondo);

        pegarFragmentInicial(new LoginInicioFragment(this));

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_idClient))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        callbackManager = CallbackManager.Factory.create();

        mAuth = FirebaseAuth.getInstance();


    }

    private void pegarFragmentInicial(Fragment fragment) {
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

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUIFirebase(currentUser);
    }

    //Recibe un user de firebase, facebook, o google y pasa a la main
    private void updateUIFirebase(FirebaseUser currentUser) {
        if (currentUser != null){
            pasarALaMainActivityMatandoActividadActual();
        } else if (currentUser == null && !Utils.hayInternet(this)){
            //binding.activityLoginFragmentContainer.setVisibility(View.GONE);
            binding.imagenFondo.setVisibility(View.GONE);
            pegarFragmentInicial(new NoInetFragment());
        }
    }

    private void updateUIFirebaseNuevoUsuario(FirebaseUser currentUser){
        //170 y 176
        if (currentUser != null){
            Intent intent = new Intent(this, OnboardingActivity.class);
            startActivity(intent);
        }
    }

    //login mail y contrasena
    private void loginFirebaseUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUIFirebase(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUIFirebase(null);
                            // ...
                        }

                        // ...
                    }
                });
    }

    //register mail y contrasena
    private void createFirebaseUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUIFirebaseNuevoUsuario(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUIFirebaseNuevoUsuario(null);
                        }

                        // ...
                    }
                });

    }

    private void pasarALaMainActivityMatandoActividadActual() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

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
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
            // Signed in successfully, show authenticated UI.
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("GOOGLE", "signInResult:failed code=" + e.getStatusCode());
            //updateUIGoogle(null);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                boolean newUser = task.getResult().getAdditionalUserInfo().isNewUser();
                                if(newUser){
                                    updateUIFirebaseNuevoUsuario(user);
                                } else {
                                    updateUIFirebase(user);
                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                                updateUIFirebase(null);
                            }

                            // ...
                        }
                    });
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
        loginFirebaseUser(username, password);
    }

    @Override
    public void onClickLoginFragmentBotonLoginConGoogle() {
        signIn();
    }

    @Override
    public void onClickLoginFragmentBotonLoginConFacebook(LoginButton loginButton) {

        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG, "facebook:onError", exception);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            boolean newUser = task.getResult().getAdditionalUserInfo().isNewUser();
                            if(newUser){
                                updateUIFirebaseNuevoUsuario(user);
                            } else {
                                updateUIFirebase(user);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUIFirebase(null);
                        }
                    }
                });
    }



    @Override
    public void onClickSignUpFragmentBotonSignUpGoogle(SignInButton button) {
        signIn();
    }

    @Override
    public void onClickSignUpFragmentBotonSignUpFacebook(LoginButton button) {
        onClickLoginFragmentBotonLoginConFacebook(button);
    }

    //chequeos de mail y contraseña firebase
    @Override
    public void onClickSignUpFragmentBotonRegistrarse(String username, String password) {
        if (username.contains(" ") && !username.contains("@") && (!username.contains(".com") || !username.contains(".net") || !username.contains(".org"))) {
            Toast.makeText(this, "El Email no es Valido", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6){
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        createFirebaseUser(username, password);
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