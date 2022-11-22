package com.example.cine.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cine.R;


public class InfoFragment extends Fragment {


    private static final String ARG_OVERVIEW = "overview";
    private static final String ARG_MULTIINFO = "multiinfo";

    private String overview;
    private String multiinfo;

    public InfoFragment() {
    }

    public static InfoFragment newInstance(String multiinfo, String overview) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MULTIINFO, multiinfo);
        args.putString(ARG_OVERVIEW, overview);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            multiinfo = getArguments().getString(ARG_MULTIINFO);
            overview = getArguments().getString(ARG_OVERVIEW);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_info, container, false);
        TextView overviewTV = (TextView) view.findViewById(R.id.overviewTV);
        TextView multiInfoTv = (TextView) view.findViewById(R.id.multiInfoTv);
        multiInfoTv.setText(multiinfo);
        overviewTV.setText(overview);
        return view;
    }


}