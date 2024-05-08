package com.example.androidd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class patient_login extends AppCompatActivity {
    Button btn;
    private EditText eid, epassword;

    private String id, password;
    private String URL = Api.api+"login.php";
    String device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);
        id = password = "";
        eid = findViewById(R.id.patient_id);

        device = getDeviceName();
        epassword = findViewById(R.id.password);
        btn = findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = eid.getText().toString();
                password = epassword.getText().toString();
                if (!id.equals("") && !password.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                        // Inside the onResponse method of your StringRequest
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String status = jsonResponse.getString("status");

                                if (status.equals("success")) {
                                    Intent intent = new Intent(patient_login.this, patient_home.class);
                                    intent.putExtra("patient_id",id);
                                    startActivity(intent);

                                } else if (status.equals("failure")) {
                                    Toast.makeText(patient_login.this, "Invalid login", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(patient_login.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(patient_login.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("id",id);
                            data.put("password", password);
                            data.put("device_name",device);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(patient_login.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        }); // Missing closing parenthesis here
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