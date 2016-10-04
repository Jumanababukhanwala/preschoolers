package com.example.dell.kindergarten;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

import static android.content.Context.ACCESSIBILITY_SERVICE;
import static android.location.LocationManager.GPS_PROVIDER;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback {

    PreschoolList list;
    ArrayList<PreschoolList> preschoolList = new ArrayList<>();
    private SupportMapFragment fragment;
    private GoogleMap map;
    FrameLayout progressLayout;
    public static View parent;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;


    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        parent = inflater.inflate(R.layout.fragment_location, container, false);
        FragmentManager fm = getChildFragmentManager();
        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).addToBackStack(null).commit();
        }     fragment.getMapAsync(this);

   return parent;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (map == null) {
//            map = fragment.getMap();
//        }
//        SearchTask task = new SearchTask();
//        task.execute();
//        int size = preschoolList.size();
//        for (int i = 0; i < size; i++) {
//            list = preschoolList.get(i);
//            LatLng school = new LatLng(list.getLatitude(), list.getLongitude());
//            map.addMarker(new MarkerOptions().position(school).title(list.getSchoolName()));
//            //   map.moveCamera(CameraUpdateFactory.newLatLng(school));
//            map.animateCamera(CameraUpdateFactory.newLatLngZoom(school, 14));
//
//
//        }
//    }



    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
     //   progressLayout.setVisibility(View.VISIBLE);

//        LatLng sydney = new LatLng(-34, 151);
//        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        SearchTask task=new SearchTask();
        task.execute();
        int size = preschoolList.size();
        for (int i = 0; i < size; i++) {
            list = preschoolList.get(i);

            LatLng school = new LatLng(list.getLatitude(), list.getLongitude());
            map.addMarker(new MarkerOptions().position(school).title(list.getSchoolName()));
            map.moveCamera(CameraUpdateFactory.newLatLng(school));
            //map.animateCamera(CameraUpdateFactory.newLatLngZoom(school, 14));

        }
       // progressLayout.setVisibility(View.GONE);
    }

    public class SearchTask extends AsyncTask<String, Void, String> {

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
                Log.d("API Result map", result);
                preschoolList.clear();
                //Parsing
                try {
                    JSONArray resultSet = new JSONArray(result);
                    for (int i = 0; i < resultSet.length(); i++) {
                        Log.d("API Result map", result);
                        JSONObject preschoolObject = resultSet.getJSONObject(i);
                        PreschoolList preschool = new PreschoolList();

                        preschool.setSchoolId(preschoolObject.getInt("id"));
                        preschool.setSchoolName(preschoolObject.getString("schoolName"));
                        preschool.setSchoolAddress(preschoolObject.getString("schoolAddress"));
                        preschool.setSchoolEmail(preschoolObject.getString("schoolEmail"));
                        preschool.setSchoolWebsite(preschoolObject.getString("schoolWebsite"));
                        preschool.setSchoolPhone(preschoolObject.getString("schoolPhone"));
                        preschool.setLatitude(preschoolObject.getDouble("latitude"));
                        preschool.setLongitude(preschoolObject.getDouble("longitude"));
//api.setText(preschool.getSchoolAddress());

                        LatLng school = new LatLng(preschool.getLatitude(), preschool.getLongitude());
                        map.addMarker(new MarkerOptions().position(school).title(preschool.getSchoolName()));
                        map.moveCamera(CameraUpdateFactory.newLatLng(school));

                        preschoolList.add(preschool);
                    }

                    //progressLayout.setVisibility(View.GONE);

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
    }

}
