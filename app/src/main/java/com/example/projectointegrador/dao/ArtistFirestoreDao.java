package com.example.projectointegrador.dao;

import androidx.annotation.NonNull;

import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.util.ResultListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ArtistFirestoreDao {

    private FirebaseFirestore db;

    public static final String COLECC_ARTISTAS = "artistas";
    public static final String MIS_ARTISTAS = "misartistas";

    public ArtistFirestoreDao() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void agregarArtistAFavoritos(final Artist artist, FirebaseUser firebaseUser, final ResultListener<Artist> listener){
        db.collection(COLECC_ARTISTAS)
                .document(firebaseUser.getUid())
                .collection(MIS_ARTISTAS)
                .document(artist.getId().toString())
                .set(artist)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.finish(artist);
                    }
                });
    }

    public void getArtistListFavoritos(FirebaseUser firebaseUser, final ResultListener<List<Artist>> listener){
        db.collection(COLECC_ARTISTAS)
                .document(firebaseUser.getUid())
                .collection(MIS_ARTISTAS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<Artist> artistList = new ArrayList<>();
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                Artist artist = queryDocumentSnapshot.toObject(Artist.class);
                                artistList.add(artist);
                            }
                            listener.finish(artistList);
                        }
                    }
                });
    }

}
