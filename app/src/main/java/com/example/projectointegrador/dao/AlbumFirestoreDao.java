package com.example.projectointegrador.dao;

import androidx.annotation.NonNull;

import com.example.projectointegrador.model.Album;
import com.example.projectointegrador.model.Artist;
import com.example.projectointegrador.model.Track;
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

public class AlbumFirestoreDao {

    private FirebaseFirestore db;

    public static final String COLECC_ALBUMES = "albumes";
    public static final String MIS_ALBUMES = "misalbumes";

    public AlbumFirestoreDao() {
        this.db = FirebaseFirestore.getInstance();
    }


    public void agregarAlbumAFavoritos(final Album album, FirebaseUser firebaseUser, final ResultListener<Album> listener){
        db.collection(COLECC_ALBUMES)
                .document(firebaseUser.getUid())
                .collection(MIS_ALBUMES)
                .document(album.getId().toString())
                .set(album)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.finish(album);
                    }
                });
    }

    public void getAlbumListFavoritos(FirebaseUser firebaseUser, final ResultListener<List<Album>> listener){
        db.collection(COLECC_ALBUMES)
                .document(firebaseUser.getUid())
                .collection(MIS_ALBUMES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<Album> albumList = new ArrayList<>();
                            for (QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()) {
                                Album album = queryDocumentSnapshot.toObject(Album.class);
                                albumList.add(album);
                            }
                            listener.finish(albumList);
                        }
                    }
                });
    }

    public void searchAlbumFavoritos(Album album, FirebaseUser firebaseUser, final ResultListener<List<Album>> listener){
        db.collection(COLECC_ALBUMES)
                .document(firebaseUser.getUid())
                .collection(MIS_ALBUMES)
                .whereEqualTo("id", album.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<Album> albumList = new ArrayList<>();
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                Album albumsearch = queryDocumentSnapshot.toObject(Album.class);
                                albumList.add(albumsearch);
                            }
                            listener.finish(albumList);
                        }
                    }
                });
    }

    public void eliminarAlbumFavoritos(final Album album, FirebaseUser firebaseUser, final ResultListener<Album> listener){
        db.collection(COLECC_ALBUMES)
                .document(firebaseUser.getUid())
                .collection(MIS_ALBUMES)
                .document(album.getId().toString())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            listener.finish(album);
                        }
                        else {
                            task.getException().printStackTrace();
                        }
                    }
                });
    }

}
