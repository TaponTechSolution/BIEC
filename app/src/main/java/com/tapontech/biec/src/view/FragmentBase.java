package com.tapontech.biec.src.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tapontech.biec.R;

/**
 * Created by sanjay on 12-02-2016.
 */
public class FragmentBase extends Fragment {

    // public constructor
    public FragmentBase(){
    }

    // create new instance of fragment
    public Fragment newInstance(){
        FragmentBase fragment = new FragmentBase();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, 	Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.home_screen_view, container, false);

        return rootView;
    }
}
