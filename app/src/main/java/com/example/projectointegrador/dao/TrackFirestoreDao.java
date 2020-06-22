package com.example.projectointegrador.dao;

import androidx.annotation.NonNull;

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

public class TrackFirestoreDao {

    private FirebaseFirestore db;

    public static final String COLECC_TRACKS = "tracks";
    public static final String MIS_TRACKS = "mistracks";

    public TrackFirestoreDao() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void agregarTrackAFavoritos(final Track track, FirebaseUser firebaseUser, final ResultListener<Track> listener){
        db.collection(COLECC_TRACKS)
                .document(firebaseUser.getUid())
                .collection(MIS_TRACKS)
                .document(track.getId().toString())
                .set(track)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.finish(track);
                    }
                });
    }

    public void getTrackListFavoritos(FirebaseUser firebaseUser, final ResultListener<List<Track>> listener){
        db.collection(COLECC_TRACKS)
                .document(firebaseUser.getUid())
                .collection(MIS_TRACKS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<Track> trackList = new ArrayList<>();
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                Track track = queryDocumentSnapshot.toObject(Track.class);
                                trackList.add(track);
                            }
                            listener.finish(trackList);
                        }
                    }
                });
    }

    public void searchTrackFavoritos(Track track, FirebaseUser firebaseUser, final ResultListener<List<Track>> listener){
        db.collection(COLECC_TRACKS)
                .document(firebaseUser.getUid())
                .collection(MIS_TRACKS)
                .whereEqualTo("id", track.getId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<Track> trackList = new ArrayList<>();
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                Track tracksearch = queryDocumentSnapshot.toObject(Track.class);
                                trackList.add(tracksearch);
                            }
                            listener.finish(trackList);
                        }
                    }
                });
    }

}
