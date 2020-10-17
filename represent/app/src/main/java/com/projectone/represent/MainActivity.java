package com.projectone.represent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Office> offices_list = new ArrayList<>();
    private ArrayList<Official> officials_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText textView = (EditText) findViewById(R.id.address_edit_text);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyCa5SOrP4lGuotvRWYW9GPD_ZSn9lYQd-A&address=1263%20Pacific%20Ave.%20Kansas%20City%20KS";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("REQ", "SUCCESS");
                        textView.setText("Response is: "+ response.substring(0,500));
                        try {
                            JSONObject received_object = new JSONObject(response);
                            JSONArray received_offices = received_object.getJSONArray("offices");
                            for (int i = 0; i < received_offices.length(); i++) {
                                JSONObject received_office_obj = (JSONObject) received_offices.get(i);
                                String position = received_office_obj.getString("name");
                                if (position.equals("U.S. Senator") || position.equals("U.S. Representative")) {
                                    Office received_office = new Office(received_office_obj);
                                    offices_list.add(received_office);
                                }
                            }

                            Office office_one = offices_list.get(1);
                            textView.setText(String.valueOf(office_one.getName()));

                            //TODO: Finish setting up Officials
                            JSONArray received_officials = received_object.getJSONArray("officials");
                            for (int i = 0; i < received_officials.length(); i++) {
                                JSONObject received_official_obj = (JSONObject) received_officials.get(i);
                                Official received_official = new Official(received_official_obj);
                                officials_list.add(received_official);
                            }

                            Official official_one = officials_list.get(office_one.getIndices()[0]);
                            textView.setText(official_one.getName());

                        } catch (JSONException e) {
                            Log.e("REQ", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("REQ", error.getMessage());
                textView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }
}