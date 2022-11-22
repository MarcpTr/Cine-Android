package com.example.cine.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cine.R;
import com.example.cine.adapter.CastCardAdapter;
import com.example.cine.adapter.MovieCardAdapter;
import com.example.cine.pojo.Cast;

import java.util.ArrayList;
import java.util.List;


public class CastFragment extends Fragment {


    private static final String ARG_CAST = "cast";

    private List<Cast> cast;

    private RecyclerView recyclerCastView;
    private CastCardAdapter castCardAdapter;

    public CastFragment() {
    }

    public static CastFragment newInstance(List<Cast> cast ) {
        CastFragment fragment = new CastFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_CAST, (ArrayList<? extends Parcelable>) cast);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            cast = getArguments().getParcelableArrayList(ARG_CAST);}
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cast, container, false);
        recyclerCastView = (RecyclerView) view.findViewById(R.id.recyclerCast);
        recyclerCastView.setHasFixedSize(false);
        recyclerCastView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        castCardAdapter = new CastCardAdapter(cast);
        recyclerCastView.setAdapter(castCardAdapter);
        return view;
    }


}