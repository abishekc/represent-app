package com.projectone.represent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class OnboardingActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private String passedAddress = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        Button location_button = (Button) findViewById(R.id.location_button);
        ImageView intent_button = (ImageView) findViewById(R.id.intent_button);


        location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLocationPermission() != false) {
                    getLocation();
                }
            }
        });

        intent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText location_text_view = (EditText) findViewById(R.id.location_text_view);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("FORMATTED_ADDRESS", location_text_view.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

    public void updateAddress(double latitude, double longitude) {
        final RequestQueue queue = Volley.newRequestQueue(this);
        final EditText location_text_view = (EditText) findViewById(R.id.location_text_view);
        final ImageView check_mark_image = (ImageView) findViewById(R.id.check_mark_image);
        final LinearLayout location_linear = (LinearLayout) findViewById(R.id.location_linear);
        final View bg_rounded = (View) findViewById(R.id.bg_round);
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + String.valueOf(latitude) + "," + String.valueOf(longitude) + "&key=AIzaSyCa5SOrP4lGuotvRWYW9GPD_ZSn9lYQd-A";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("REQ", "SUCCESS");
                        //textView.setText("Response is: "+ response.substring(0,500));
                        try {
                            JSONObject received_object = new JSONObject(response);
                            JSONArray received_results = received_object.getJSONArray("results");
                            JSONObject received_address = (JSONObject) received_results.get(0);
                            String formatted_address = received_address.getString("formatted_address");
                            Log.e("LOC", formatted_address);
                            location_text_view.setText(formatted_address);
                            int colorFrom = getResources().getColor(R.color.blueGray);
                            int colorTo = getResources().getColor(R.color.gold);
                            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                            ValueAnimator textColorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorTo, colorFrom);
                            colorAnimation.setDuration(800); // milliseconds
                            ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    location_linear.setBackgroundColor((int) animation.getAnimatedValue());
                                }
                            };

                            ValueAnimator.AnimatorUpdateListener listener_two = new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    location_text_view.setTextColor((int) animation.getAnimatedValue());
                                }
                            };
                            colorAnimation.addUpdateListener(listener);
                            textColorAnimation.addUpdateListener(listener_two);
                            colorAnimation.start();
                            textColorAnimation.start();
                            check_mark_image.setVisibility(ImageView.VISIBLE);
                            passedAddress = formatted_address;
                        } catch (JSONException e) {
                            Log.e("LOC_E", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("REQ", error.getLocalizedMessage());
                //textView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        } else {
            return true;
        }
    }

    public void getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Logic to handle location object
                    Log.e("LOC", String.valueOf(location.getLatitude()));
                    updateAddress(location.getLatitude(), location.getLongitude());
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        getLocation();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}