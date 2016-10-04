package com.example.dell.kindergarten;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dell on 4/15/2016.
 */
public class CustomAdapter extends ArrayAdapter<View> {
    SchoolDetailFragment schoolDetailFragment=new SchoolDetailFragment();
    ArrayList<Review> mdata;
    int mResource;
    MainActivity mContext=new MainActivity();

    public CustomAdapter(Context context, int resource, ArrayList<Review> review) {
        super(context, resource);
        mdata=review;
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

        
        Review reviews = mdata.get(position);
        TextView nameText = (TextView)convertView.findViewById(R.id.Name);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.schoolRatingbar);
        TextView feedback = (TextView)convertView.findViewById(R.id.ShoolFeedback);
        nameText.setText(reviews.getName());
       // ratingBar.setNumStars(reviews.getRating());
        ratingBar.setRating(reviews.getRating());

        feedback.setText(reviews.getFeedback());
        return convertView;





    }
}
