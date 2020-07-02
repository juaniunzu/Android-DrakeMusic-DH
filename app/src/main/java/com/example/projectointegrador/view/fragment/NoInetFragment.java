package com.example.projectointegrador.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.projectointegrador.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoInetFragment extends Fragment {

    private ImageView gif;

    public NoInetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_no_inet, container, false);

        gif = view.findViewById(R.id.fragmentNoInetImageView);

        Glide.with(getContext()).asGif().load(R.drawable.no_internet).into(gif);

        return view;
    }
}
