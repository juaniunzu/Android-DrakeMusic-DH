package com.example.projectointegrador.controller;

import android.content.Context;

import com.example.projectointegrador.dao.HistorialFirestoreDao;
import com.example.projectointegrador.model.Busqueda;
import com.example.projectointegrador.util.ResultListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class HistorialController {

    private HistorialFirestoreDao historialFirestoreDao;

    public HistorialController() {
        this.historialFirestoreDao = new HistorialFirestoreDao();
    }

    public void agregarBusquedaAlHistorial (Busqueda busqueda, FirebaseUser firebaseUser, ResultListener<Busqueda> listener){
        historialFirestoreDao.agregarBusquedaAlHistorial(busqueda, firebaseUser, new ResultListener<Busqueda>() {
            @Override
            public void finish(Busqueda resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void getHistorial (FirebaseUser firebaseUser, ResultListener<List<Busqueda>> listener){
        historialFirestoreDao.getHistorial(firebaseUser, new ResultListener<List<Busqueda>>() {
            @Override
            public void finish(List<Busqueda> resultado) {
                listener.finish(resultado);
            }
        });
    }

    public void borrarItemHistorial(FirebaseUser firebaseUser, Context context, Busqueda busqueda, ResultListener<Task<Void>> listener){
        historialFirestoreDao.borrarItemHistorial(firebaseUser, context, busqueda, new ResultListener<com.google.android.gms.tasks.Task<Void>>() {
            @Override
            public void finish(com.google.android.gms.tasks.Task<Void> resultado) {
                listener.finish(resultado);
            }
        });
    }
}
