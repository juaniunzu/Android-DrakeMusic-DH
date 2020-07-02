package com.example.projectointegrador.controller;

import android.content.Context;

import com.example.projectointegrador.dao.HistorialFirestoreDao;
import com.example.projectointegrador.model.Busqueda;
import com.example.projectointegrador.util.ResultListener;
import com.google.firebase.auth.FirebaseUser;

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

    public void borrarHistorial(FirebaseUser firebaseUser, Context context){
        historialFirestoreDao.borrarHistorial(firebaseUser, context);
    }
}
