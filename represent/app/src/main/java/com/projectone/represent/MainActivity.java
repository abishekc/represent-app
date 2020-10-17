package com.projectone.represent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private ArrayList<TextView> view_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                //WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final EditText address_text = (EditText) findViewById(R.id.address_edit_text);
        final Button refresh_button = (Button) findViewById(R.id.refresh_button);
        /*final TextView first_name_view =
        final TextView second_name_view = (TextView) findViewById(R.id.name_second_view);
        final TextView third_name_view = (TextView) findViewById(R.id.name_third_view);*/
        view_list.add((TextView) findViewById(R.id.name_first_view));
        view_list.add((TextView) findViewById(R.id.name_second_view));
        view_list.add((TextView) findViewById(R.id.name_third_view));
        final RequestQueue queue = Volley.newRequestQueue(this);


        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<Office> offices_list = new ArrayList<>();
                final ArrayList<Official> officials_list = new ArrayList<>();

                String replaced = address_text.getText().toString();
                replaced = replaced.replaceAll(" ", "");
                String url = "https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyCa5SOrP4lGuotvRWYW9GPD_ZSn9lYQd-A&address=" + replaced;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("REQ", "SUCCESS");
                                //textView.setText("Response is: "+ response.substring(0,500));
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

                                    Office office_one = offices_list.get(0);
                                    //textView.setText(String.valueOf(office_one.getName()));

                                    //TODO: Finish setting up Officials
                                    JSONArray received_officials = received_object.getJSONArray("officials");
                                    for (int i = 0; i < received_officials.length(); i++) {
                                        JSONObject received_official_obj = (JSONObject) received_officials.get(i);
                                        Official received_official = new Official(received_official_obj);
                                        officials_list.add(received_official);
                                    }

                                    int count = 0;
                                    for (int i = 0; i < offices_list.size(); i++) {
                                        for (int j = 0; j < offices_list.get(i).getIndices().length; j++) {
                                            int pos = offices_list.get(i).getIndices()[j];
                                            view_list.get(count).setText(officials_list.get(pos).getName());
                                            count++;
                                        }
                                    }
                                    Official official_one = officials_list.get(office_one.getIndices()[0]);
                                    //textView.setText(official_one.getParty());

                                } catch (JSONException e) {
                                    Log.e("REQ", e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REQ", error.getMessage());
                        //textView.setText("That didn't work!");
                    }
                });

                queue.add(stringRequest);
            }
        });
    }
}