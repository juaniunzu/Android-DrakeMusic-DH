package com.example.projectointegrador.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    public static boolean hayInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return cm.getActiveNetworkInfo() != null && netInfo.isConnected();
    }

    /**
     * interface que tiene que ser implementada por los objetos que queramos que puedan aparecer en el buscador.
     * tienen que sobreescribir esos métodos para que cuando se agreguen a la lista del buscador, puedan linkearse los recursos
     * a los elementos de cada celda.
     */
    public interface Searchable{
        String informarTitulo();
        String informarImagen();
        String informarDescripcion();
    }
}
