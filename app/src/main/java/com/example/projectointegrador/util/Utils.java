package com.example.projectointegrador.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

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

    public static void setFragmentBackground(Context context, final View view, String url) {
        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(@Nullable Palette palette) {
                        int dominantColor = palette.getDominantColor(resource.getPixel(0, 0));

                        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{dominantColor, 0xFF000000});
                        gd.setCornerRadius(0f);

                        view.setBackground(gd);
                    }
                });
            }
        });
    }
    /**
    Cuando se agrega un placeholder a un glide con estas Request Options, el placeholder sera un circularProgressDrawable.
     */
    public static RequestOptions requestOptionsCircularProgressBar(Context context){
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(circularProgressDrawable);
        return requestOptions;
    }
}

