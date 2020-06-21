package com.example.projectointegrador.dao;

import androidx.annotation.NonNull;

import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.util.ResultListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class UltimosReproducidosFirestoreDao {

    private FirebaseFirestore db;

    public static final String COLECC_ULTIMOS_REP = "ultimosrep";
    public static final String MIS_ULT_REPRODUCIDOS = "misultimosrep";


    public UltimosReproducidosFirestoreDao() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void agregarTrackAUltimosReproducidos (final Track track, FirebaseUser firebaseUser, final ResultListener<Track> listener){
        db.collection(COLECC_ULTIMOS_REP)
                .document(firebaseUser.getUid())
                .collection(MIS_ULT_REPRODUCIDOS)
                .document(track.getId().toString())
                .set(track)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.finish(track);
                    }
                });
    }

    public void getUltimosReproducidos(FirebaseUser firebaseUser, final ResultListener<List<Track>> listener){
        db.collection(COLECC_ULTIMOS_REP)
                .document(firebaseUser.getUid())
                .collection(MIS_ULT_REPRODUCIDOS)
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(10)
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



}
