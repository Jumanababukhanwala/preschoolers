package com.example.dell.kindergarten;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Dell on 4/6/2016.
 */
public class SearchAdapter extends ArrayAdapter<View> {
    SchoolDetailFragment schoolDetailFragment=new SchoolDetailFragment();
ArrayList<PreschoolList> mdata;
    int mResource;
    MainActivity mContext=new MainActivity();

    public SearchAdapter(Context context, int resource, ArrayList<PreschoolList> preschoolList) {
        super(context, resource);
    mdata=preschoolList;
        //mContext=context;
        mResource=resource;
    }

    @Override
    public int getCount() {
        return mdata.size();
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView =((Activity) getContext()).getLayoutInflater().inflate(mResource, null);
        }

        int rem= position%4;
        String color=null;
        switch (rem) {
            case 0:
                color = "#4285F4";
                break;
            case 1:
                color = "#00C853";
                break;
            case 2:
                color = "#FFC107";
                break;
            case 3:
                color = "#F06292";
                break;
        }

        PreschoolList preschool = mdata.get(position);
        PreTextView nameText = (PreTextView)convertView.findViewById(R.id.SchoolName_Details);
//        PreButtonView nameText = (PreButtonView)convertView.findViewById(R.id.name_button);

        nameText.setBackgroundColor(Color.parseColor(color));
        nameText.setText(preschool.getSchoolName());

        return convertView;





    }
}


