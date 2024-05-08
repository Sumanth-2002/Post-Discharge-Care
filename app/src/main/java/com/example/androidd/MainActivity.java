package com.example.androidd;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ImageView bodi;
    String url = Api.api + "loginCheck.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bodi = findViewById(R.id.nav1);
        String deviceName = getDeviceName();     // Get device name
        bodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display Toast message with the device name
//                Toast.makeText(MainActivity.this, "Device Name: " + deviceName, Toast.LENGTH_SHORT).show();
                checkLogin(deviceName);

            }
        });
    }

    public void checkLogin(String device) {

        // Request to your server for login check
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            // Check the status in the JSON response
                            String status = jsonResponse.getString("status");
                            if ("Success".equals(status)) {
                                // Login successful
                                String category = jsonResponse.getString("category");
                                String id = jsonResponse.getString("id");

                                // Navigate based on category
                                if ("patient".equals(category)) {
                                    Intent patientIntent = new Intent(MainActivity.this, patient_home.class);
                                    patientIntent.putExtra("patient_id", id);
                                    startActivity(patientIntent);
                                } else if ("doctor".equals(category)) {
                                    Intent doctorIntent = new Intent(MainActivity.this, doctor_home.class);
                                    doctorIntent.putExtra("doctor_id", id);
                                    startActivity(doctorIntent);
                                } else {
                                    // Handle other categories or conditions as needed
                                }
                            } else {
                                // Login failed, handle accordingly
                                Intent intent = new Intent(MainActivity.this,types_of_login.class);
                                intent.putExtra("device",device);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                            Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle Volley error
                        Toast.makeText(MainActivity.this, "Volley Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Add parameters to the request
                Map<String, String> params = new HashMap<>();
                params.put("device_name", device);
                return params;
            }
        };

        // Add the request to the Volley queue
        Volley.newRequestQueue(MainActivity.this).add(stringRequest);
    }

    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char firstChar = s.charAt(0);
        if (Character.isUpperCase(firstChar)) {
            return s;
        } else {
            return Character.toUpperCase(firstChar) + s.substring(1);
        }
    }
}
