package com.example.dell.kindergarten;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 3/22/2016.
 */
public class School {
    private String name;
    public School(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * A simple {@link Fragment} subclass.
     */
    public static class AboutusFragment extends Fragment {
        public AboutusFragment() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
           View parent= inflater.inflate(R.layout.fragment_aboutus, container, false);
            return parent;
        }

    }
}
