package com.example.projectointegrador.dao;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.projectointegrador.model.Busqueda;
import com.example.projectointegrador.util.ResultListener;
import com.example.projectointegrador.view.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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
                .document()
                .set(busqueda)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.finish(busqueda);
                    }
                });
    }

    public void borrarHistorial(FirebaseUser firebaseUser, final Context context){
        db.collection(COLECC_HISTORY)
                .document(firebaseUser.getUid())
                .collection(MI_HISTORIAL)
                .document()
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Historial eliminado", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
