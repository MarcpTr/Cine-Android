package com.example.cine.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.cine.pojo.Movie;
import com.example.cine.pojo.Similar;

import java.util.ArrayList;
import java.util.List;


public class SimilarFragment extends Fragment {
    public SimilarFragment() {
    }

    private static final String ARG_SIMILAR = "similar";

    private List<Movie> movies;

    private RecyclerView recyclerMovieView;
    private MovieCardAdapter movieCardAdapter;


    public static SimilarFragment newInstance(List<Movie> movies) {
        SimilarFragment fragment = new SimilarFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_SIMILAR, (ArrayList<? extends Parcelable>) movies);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movies = getArguments().getParcelableArrayList(ARG_SIMILAR);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_similar, container, false);
        recyclerMovieView = (RecyclerView) view.findViewById(R.id.recyclerSimilar);
        recyclerMovieView.setHasFixedSize(false);
        recyclerMovieView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        movieCardAdapter = new MovieCardAdapter((ArrayList<Movie>) movies);
        recyclerMovieView.setAdapter(movieCardAdapter);
        return view;
    }
}