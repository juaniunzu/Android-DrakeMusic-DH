package com.example.projectointegrador.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.R;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slideImages = {
            R.drawable.charizardmusicpng,
            R.drawable.agregarfavoritos,
            R.drawable.favoritos,
            R.drawable.charizardmusicpng
    };

    public String[] slideTextView = {
            "Estamos felices de tenerte en DrakeMusic, la mejor aplicación de streaming de música del mundo." + "\n" + "\n" + "Te haremos compañía mientras te ejercitás, estudiás o simplemente disfrutás de un café con tu banda favorita.",
            "Guardá tus favoritos y accedé a ellos en cualquier momento, incluso en modo offline!\n" + "\n" + "\n" +
                    "Nosotros nos encargaremos de sugerirte la música que te encante, descubrimientos semanales y todos los nuevos lanzamientos de tus artistas preferidos.",
            "Buscá tu música favorita por artista, álbum o track.",
            "Drake te desea una feliz estadía!"
    };

    public String[] slideTextViewTitulo = {
        "Hola!",
            "Tu música preferida, a mano",
            "Fácil de encontrar",
            "Es genial que estés por acá."
    };

    @Override
    public int getCount() {
        return slideImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView imageView = view.findViewById(R.id.slideLayoutImageView);
        TextView textView = view.findViewById(R.id.slideLayoutTextView);
        TextView textViewTitulo = view.findViewById(R.id.slideLayoutTextViewTitulo);


        switch (position){
            case 0:
                imageView.setImageResource(slideImages[position]);
                break;
            case 1:
                Glide.with(context).asGif().load(slideImages[position]).into(imageView);
                break;
            case 2:
                Glide.with(context).asGif().load(slideImages[position]).into(imageView);
                break;
            case 3:
                imageView.setImageResource(slideImages[position]);
                break;
        }


        textView.setText(slideTextView[position]);
        textViewTitulo.setText(slideTextViewTitulo[position]);

        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
