package com.example.dell.kindergarten;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolDetailFragment extends Fragment {
    HomeFragment homeFragment;
    int schoolId;
    ListView listView;
    //FrameLayout progressLayout;
    TextView schoolName, schoolAddress, schoolEmail, schoolContact;
    PreschoolList preschoolDetail;
    CustomAdapter adapter;
    FrameLayout progressLayout;
    ArrayList<Review> reviews = new ArrayList<>();

    public void setDetails(PreschoolList preschoolDetail) {
        this.preschoolDetail = preschoolDetail;
    }

    public SchoolDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_school_detail, container, false);
        listView = (ListView) parent.findViewById(R.id.listView);
        homeFragment = new HomeFragment();
        progressLayout = (FrameLayout) parent.findViewById(R.id.progressLayout);
        schoolName = (TextView) parent.findViewById(R.id.SchoolName);
        schoolAddress = (TextView) parent.findViewById(R.id.Address);
        schoolEmail = (TextView) parent.findViewById(R.id.Email);
        schoolContact = (TextView) parent.findViewById(R.id.Contact);
        //  getDetails();
        schoolId = preschoolDetail.getSchoolId();
        schoolName.setText(preschoolDetail.getSchoolName());
        schoolAddress.setText(preschoolDetail.getSchoolAddress());
        schoolEmail.setText(preschoolDetail.getSchoolEmail());
        schoolContact.setText(preschoolDetail.getSchoolPhone());

        search();
        adapter = new CustomAdapter(getActivity(), R.layout.review_layout, reviews);
        listView.setAdapter(adapter);

        return parent;
    }

    void search() {
        progressLayout.setVisibility(View.VISIBLE);
        SearchReviewsTask task = new SearchReviewsTask();
        task.execute();

    }

    public class SearchReviewsTask extends AsyncTask<String, Void, String> {
        SchoolDetailFragment schoolDetailFragment;

        @Override
        protected String doInBackground(String... search) {
            //     String searchTerm = search[0];
            String result = null;
            try {
                result = callAPI();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
     //           Log.d("API Result", result);
                reviews.clear();
                //Parsing
                try {
                    //JSONObject root = new JSONObject(result);
                    JSONArray resultSet = new JSONArray(result);
                    int n = resultSet.length();
                    for (int i = 0; i < resultSet.length(); i++) {
                        JSONObject preschoolObject = resultSet.getJSONObject(i);
                        Review preschoolReviews = new Review();
                        int id = preschoolObject.getInt("schoolId");
                        if (schoolId == id) {
                            preschoolReviews.setSchoolId(preschoolObject.getInt("schoolId"));
                            preschoolReviews.setSchoolName(preschoolObject.getString("schoolName"));
                            preschoolReviews.setName(preschoolObject.getString("name"));
                            preschoolReviews.setRating(preschoolObject.getInt("rating"));
                            preschoolReviews.setFeedback(preschoolObject.getString("feedback"));
                            reviews.add(preschoolReviews);
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
            String originalUrl = "http://preschoolers-indore.us-east-1.elasticbeanstalk.com/Review";
            //String finalUrl = originalUrl + "?ProductName=" + searchTerm;
            URL url = new URL(originalUrl);
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
