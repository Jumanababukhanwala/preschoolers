package com.example.dell.kindergarten;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mxn.soul.flowingdrawer_core.MenuFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyMenuFragment extends MenuFragment {


    public MyMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent =  inflater.inflate(R.layout.fragment_my_menu, container, false);
        return setupReveal(parent);
    }

}
