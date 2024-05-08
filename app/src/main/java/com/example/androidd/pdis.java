package com.example.androidd;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class pdis extends AppCompatActivity {
    String pId;
TextView id,name,gender,age,department,consultant,address,dateofad,dateofdis,disease,treatGiven,coursehsp,coursepic,coursewar,advod,rev,viewImages;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        pId=intent.getStringExtra("patient_id");
        setContentView(R.layout.activity_pdis);
        id=findViewById(R.id.hospid);
        name=findViewById(R.id.disname);
        gender=findViewById(R.id.disgender);
        department=findViewById(R.id.disdepartment);
        consultant=findViewById(R.id.discons);
        address=findViewById(R.id.disaddress);
        dateofad=findViewById(R.id.disdateOfAd);
        dateofdis=findViewById(R.id.disdateOfDi);
        disease=findViewById(R.id.disdise);
        treatGiven=findViewById(R.id.distreatg);
        coursehsp=findViewById(R.id.dishsp);
        coursepic=findViewById(R.id.dispic);
        coursewar=findViewById(R.id.disw);
        advod=findViewById(R.id.disadv);
        rev=findViewById(R.id.disrev);
        viewImages=findViewById(R.id.viewDisImg);
        viewImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(pdis.this,view_images.class);
                intent.putExtra("patient_id",pId);
                startActivity(intent);
            }
        });
        fetchDetailsFromServer(pId);
    }
    private void fetchDetailsFromServer(final String patientId) {
        String url = Api.api+"get_discharge.php";

        // Create a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Create a string request to fetch the details
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parse the JSON response
//                    Toast.makeText(pdis.this, "response"+response, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    // Extract details from JSON object
                    id.setText(jsonObject.getString("id"));
                    name.setText(jsonObject.getString("Name"));
                    gender.setText(jsonObject.getString("Sex"));
                    address.setText(jsonObject.getString("Address"));
                    department.setText(jsonObject.getString("Department"));
                    consultant.setText(jsonObject.getString("Consultant"));

                    dateofad.setText(jsonObject.getString("Date_of_admission"));
                    dateofdis.setText(jsonObject.getString("Date_of_Discharge"));
                    disease.setText(jsonObject.getString("Diagnosis"));
                    treatGiven.setText(jsonObject.getString("treatment"));
                    coursehsp.setText(jsonObject.getString("Course_in_Hospital"));
                    coursepic.setText(jsonObject.getString("Course_in_PICU"));
                    coursewar.setText(jsonObject.getString("Course_in_Ward"));
                    advod.setText(jsonObject.getString("Advice_on_Discharge"));
                    rev.setText(jsonObject.getString("Review"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors here
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Pass parameters to the PHP script
                Map<String, String> params = new HashMap<>();
                params.put("id", patientId);
                return params;
            }
        };

        // Add the request to the queue
        requestQueue.add(stringRequest);
    }
}
