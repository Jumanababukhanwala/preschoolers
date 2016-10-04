package com.example.dell.kindergarten;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    GridView mGridView;
    ArrayList<PreschoolList> schools = new ArrayList<>();
    SearchAdapter adapter;
    FrameLayout progressLayout;
    PreschoolList preschoolDetail;
    boolean ratingClicked=false,dataSearch=false ;
    SchoolDetailFragment schoolDetailFragment;
    EditText searchView;
    String searchSchool;
    HomeFragment homeFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View parent = inflater.inflate(R.layout.fragment_home, container, false);
        progressLayout = (FrameLayout) parent.findViewById(R.id.progressLayout);
        searchView= (EditText) parent.findViewById(R.id.searchBar);
      //  String searchData=searchView.getQueryHint();
        searchSchool=searchView.getText().toString();

        search();
        schoolDetailFragment = new SchoolDetailFragment();
        mGridView = (GridView) parent.findViewById(R.id.grid_view);

        adapter = new SearchAdapter(getActivity(), R.layout.item_school_grid, schools);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parentView, View v, int position, long id) {
                preschoolDetail= schools.get(position);
                schoolDetailFragment.setDetails(preschoolDetail);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, schoolDetailFragment).addToBackStack(null).commit();
            }
        });
        return parent;
    }


    void search() {
        progressLayout.setVisibility(View.VISIBLE);
        SearchTask task = new SearchTask();
        task.execute();

    }

//    public ArrayList<PreschoolList> getSchools() {
//        return schools;
//    }

    public void onRatingClick() {
ratingClicked=true;
    search();
    }

    public void onSearchClick() {
        if(searchSchool!=null||searchSchool!="") {
            dataSearch = true;

        }
        search();
    }
//
//    public void onLocationClick() {
//      //  task.execute();
//        location.setPreschoolList(schools);
//    }


    public class SearchTask extends AsyncTask<String, Void, String> {
        String review = null;
        @Override
        protected String doInBackground(String... search) {
            //     String searchTerm = search[0];
            String result = null;
            try {
                review = callReviewAPI();
                result = callAPI();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

@Override
        protected void onPostExecute(String result) {
            if (result != null) {
                //Log.d("API Result", result);
                schools.clear();
                //Parsing
                try {
                    //JSONObject root = new JSONObject(result);
                    JSONArray resultSet = new JSONArray(result);
                    JSONArray resultSet2 = new JSONArray(review);
                    int n = resultSet.length();
                    int  schoolId=0,id=0;
                    int reviewsCtr[]=new int[n];
                    int rating[]=new int[n];
                    reviewsCtr[0]=0;
                    for (int i = 0; i < resultSet.length(); i++) {

                         JSONObject preschoolObject = resultSet.getJSONObject(i);
                        PreschoolList preschool = new PreschoolList();
                       schoolId=preschoolObject.getInt("id");
                        //rating[i]=0;
                        preschool.setSchoolId(preschoolObject.getInt("id"));
                        preschool.setSchoolName(preschoolObject.getString("schoolName"));
                        preschool.setSchoolAddress(preschoolObject.getString("schoolAddress"));
                        preschool.setSchoolEmail(preschoolObject.getString("schoolEmail"));
                        preschool.setSchoolWebsite(preschoolObject.getString("schoolWebsite"));
                        preschool.setSchoolPhone(preschoolObject.getString("schoolPhone"));
                        preschool.setLatitude(preschoolObject.getDouble("latitude"));
                        preschool.setLongitude(preschoolObject.getDouble("longitude"));
//api.setText(preschool.getSchoolAddress());

                        reviewsCtr[i]=0;
                        for (int j = 0; j < resultSet2.length(); j++) {

                            JSONObject preschoolObject2 = resultSet2.getJSONObject(j);
                            Review preschoolReviews = new Review();
                            id = preschoolObject2.getInt("schoolId");

                            if (schoolId == id) {
                                reviewsCtr[i]=reviewsCtr[i]+1;
                                int ratings= preschoolObject2.getInt("rating");
                                preschoolReviews.setSchoolId(preschoolObject2.getInt("schoolId"));
                                preschoolReviews.setSchoolName(preschoolObject2.getString("schoolName"));
                                preschoolReviews.setName(preschoolObject2.getString("name"));
                                preschoolReviews.setRating(preschoolObject2.getInt("rating"));
                                preschoolReviews.setFeedback(preschoolObject2.getString("feedback"));
                            rating[i]=rating[i]+ratings;
                            }
                            else{
                                continue;
                            }


                        }
                        Log.d("API ", reviewsCtr[i] + "");
                        rating[i]=(int)rating[i]/reviewsCtr[i];
                        Log.d("API ", rating[i] + "");
if(dataSearch==true) {
    if(preschool.getSchoolName().equals(searchSchool))
    schools.add(preschool);
}                       else
                            schools.add(preschool);
                    }

                    if(ratingClicked==true)
                    {
                        int temp;
                            for(int i=0;i<rating.length;i++){
                                for(int j=0;j<i;j++){
                                    if(rating[i]>rating[j]){
                                        temp=rating[i];
                                        rating[i]=rating[j];
                                        rating[j]=temp;
                                        Collections.swap(schools,i,j);

                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    progressLayout.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else
                Log.d("API Results", "Null Result");
        }

        private String callAPI() throws IOException {
            String originalUrl1 = "http://preschoolers-indore.us-east-1.elasticbeanstalk.com/schoolList";
            //String Url2 ="http://192.168.1.125:8080/Preshoolers/Review";
            URL url = new URL(originalUrl1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder str = new StringBuilder();

            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                str.append(line + "\n");
            }
            return str.toString();
        }
        private String callReviewAPI() throws IOException {
            String originalUrl1 = "http://preschoolers-indore.us-east-1.elasticbeanstalk.com/Review";
            //String Url2 ="http://192.168.1.125:8080/Preshoolers/Review";
            URL url = new URL(originalUrl1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder str = new StringBuilder();

            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                str.append(line + "\n");
            }
            return str.toString();
        }

    }
}
