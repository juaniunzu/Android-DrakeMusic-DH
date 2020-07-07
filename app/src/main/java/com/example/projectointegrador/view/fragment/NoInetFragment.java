package com.example.projectointegrador.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoInetFragment extends Fragment {

    private ImageView gif;
    private NoInetFragmentListener listener;
    private Button reintentar;

    public NoInetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_no_inet, container, false);

        gif = view.findViewById(R.id.fragmentNoInetImageView);
        reintentar = view.findViewById(R.id.fragmentNoInetBotonReintentar);

        Glide.with(getContext()).asGif().load(R.drawable.no_internet).into(gif);

        reintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickReintentar();
            }
        });

        return view;
    }

    public interface NoInetFragmentListener{
        void onClickReintentar();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (NoInetFragmentListener) context;
    }
}
