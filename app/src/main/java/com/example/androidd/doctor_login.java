package com.example.androidd;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

public class doctor_login extends AppCompatActivity {
    Button btn;
    private EditText eid, epassword;


    private String id, password, device;
    private String URL = Api.api + "doctor_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        id = password = "";
        eid = findViewById(R.id.id);
        epassword = findViewById(R.id.dpassword);
        btn = findViewById(R.id.dsubmit);

        device = getDeviceName();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = eid.getText().toString();
                password = epassword.getText().toString();
                if (!id.equals("") && !password.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String status = jsonResponse.getString("status");

                                if (status.equals("success")) {
                                    Intent intent = new Intent(doctor_login.this, doctor_home.class);
                                    intent.putExtra("doctor_id", id);
                                    startActivity(intent);
                                    finish();
                                } else if (status.equals("failure")) {
                                    Toast.makeText(doctor_login.this, "Invalid login", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(doctor_login.this, "Error in response", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(doctor_login.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(doctor_login.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("id", id);
                            params.put("password", password);
                            params.put("device_name", device);
                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(doctor_login.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
