package com.example.projectointegrador.dao;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.projectointegrador.model.Busqueda;
import com.example.projectointegrador.util.ResultListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistorialFirestoreDao {

    private FirebaseFirestore db;

    public static final String COLECC_HISTORY = "historial";
    public static final String MI_HISTORIAL = "mihistorial";

    public HistorialFirestoreDao() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void agregarBusquedaAlHistorial(final Busqueda busqueda, FirebaseUser firebaseUser, final ResultListener<Busqueda> listener){
        db.collection(COLECC_HISTORY)
                .document(firebaseUser.getUid())
                .collection(MI_HISTORIAL)
                .document(busqueda.getBusqueda())
                .set(busqueda)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.finish(busqueda);
                    }
                });
    }

    public void getHistorial(FirebaseUser firebaseUser, ResultListener<List<Busqueda>> listener){
        db.collection(COLECC_HISTORY)
                .document(firebaseUser.getUid())
                .collection(MI_HISTORIAL)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<Busqueda> historial = new ArrayList<>();
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                Busqueda busqueda = queryDocumentSnapshot.toObject(Busqueda.class);
                                historial.add(busqueda);
                            }
                            listener.finish(historial);
                        }
                    }
                });
    }

    public void borrarItemHistorial(FirebaseUser firebaseUser, final Context context, Busqueda busqueda, ResultListener<Task<Void>> listener){
        db.collection(COLECC_HISTORY)
                .document(firebaseUser.getUid())
                .collection(MI_HISTORIAL)
                .document(busqueda.getBusqueda())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.finish(task);
                    }
                });
    }
}
