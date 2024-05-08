package com.example.androidd;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class view_discharge extends AppCompatActivity {
    TextView Id, Name, Gender, Department, Consultant, Address, Date_of_Admission, Date_of_Discharge, Disease, Chief_complaints, History_of_present_illness, past_history,
            antennal_history, natal_history, postnatal_history, Gross_motor, Fine_motor, Language, Social_and_congnitiont, Immunisation_history, Anthropometry, Weightt, Heightt, Heart_rate, Temperature, crt, rr,
            spo2, Head_to_toe_examination, General_examination, Systematic_examination, Treatment_given, Course_in_Hospital, Course_in_Picu, Course_in_ward, Advice_on_discharge, Review;
    String id ;
    Button viewImg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_discharge);
        Intent intent = getIntent();
        id=intent.getStringExtra("patient_id");
        Id = findViewById(R.id.vid);
        Name = findViewById(R.id.vname);
        Gender = findViewById(R.id.vgender);
        Department = findViewById(R.id.vdepartment);
        Consultant = findViewById(R.id.vconsultant);
        Address = findViewById(R.id.vaddress);
        Date_of_Admission = findViewById(R.id.vdateofad);
        Date_of_Discharge = findViewById(R.id.vdateofdi);
        Disease = findViewById(R.id.vdiagnosis);
        viewImg=findViewById(R.id.vimages);
        viewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view_discharge.this, view_images.class);
                intent.putExtra("patient_id",id);
                startActivity(intent);
            }
        });
        Chief_complaints = findViewById(R.id.vchief);
        History_of_present_illness = findViewById(R.id.vhistory);
        past_history = findViewById(R.id.vpast);
        antennal_history = findViewById(R.id.vantennal);
        natal_history = findViewById(R.id.vnatal_history);
        postnatal_history = findViewById(R.id.vpost);
        Gross_motor = findViewById(R.id.vgross);
        Fine_motor = findViewById(R.id.vfine);
        Language = findViewById(R.id.vlan);
        Social_and_congnitiont = findViewById(R.id.social_and_congnitiont);
        Immunisation_history = findViewById(R.id.vimmunisation_history);
        Anthropometry = findViewById(R.id.vanthropometry);
        Weightt = findViewById(R.id.vweightt);
        Heightt = findViewById(R.id.vheightt);
        Heart_rate = findViewById(R.id.vheart_rate);
        Temperature = findViewById(R.id.vtemperature);
        crt = findViewById(R.id.vcrt);
        rr = findViewById(R.id.vrr);
        spo2 = findViewById(R.id.vspo2);
        Head_to_toe_examination = findViewById(R.id.vhead_to_toe_examination);
        General_examination = findViewById(R.id.vgeneral_examination);
        Systematic_examination = findViewById(R.id.vsystematic_examination);
        Treatment_given = findViewById(R.id.vtreatment_given);
        Course_in_Hospital = findViewById(R.id.vcourse_in_hospital);
        Course_in_Picu = findViewById(R.id.vcourse_in_picu);
        Course_in_ward = findViewById(R.id.vcourse_in_ward);
        Advice_on_discharge = findViewById(R.id.vadvice_on_discharge);
        Review = findViewById(R.id.vreview);

        fetchDetailsFromServer(id);
    }

    private void fetchDetailsFromServer(final String patientId) {
        String url = Api.api+"get_docDischarge.php";

        // Create a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Create a string request to fetch the details
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Parse the JSON response
//                    Toast.makeText(view_discharge.this, "response" + response, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    // Extract details from JSON object
                    Id.setText(jsonObject.getString("id"));
                    Name.setText(jsonObject.getString("Name"));
                    Gender.setText(jsonObject.getString("Sex"));
                    Address.setText(jsonObject.getString("Address"));
                    Department.setText(jsonObject.getString("Department"));
                    Consultant.setText(jsonObject.getString("Consultant"));
                    Date_of_Admission.setText(jsonObject.getString("Date_of_admission"));
                    Date_of_Discharge.setText(jsonObject.getString("Date_of_Discharge"));
                    Disease.setText(jsonObject.getString("Diagnosis"));
                    Chief_complaints.setText(jsonObject.getString("chief_complaints"));
                    History_of_present_illness.setText(jsonObject.getString("illness"));
                    past_history.setText(jsonObject.getString("Past_history"));
                    antennal_history.setText(jsonObject.getString("Antennal_history"));
                    natal_history.setText(jsonObject.getString("Natal_history"));
                    postnatal_history.setText(jsonObject.getString("Postnatal_history"));
                    Gross_motor.setText(jsonObject.getString("Gross_Motor"));
                    Fine_motor.setText(jsonObject.getString("Fine_Motor"));
                    Language.setText(jsonObject.getString("Language"));
                    Social_and_congnitiont.setText(jsonObject.getString("Social_and_Cognition"));
                    Immunisation_history.setText(jsonObject.getString("history"));
                    Anthropometry.setText(jsonObject.getString("Anthropometry"));
                    Weightt.setText(jsonObject.getString("weightt"));
                    Heightt.setText(jsonObject.getString("height"));
                    Heart_rate.setText(jsonObject.getString("Heart_rate"));
                    Temperature.setText(jsonObject.getString("Temperature"));
                    crt.setText(jsonObject.getString("CRT"));
                    rr.setText(jsonObject.getString("RR"));
                    spo2.setText(jsonObject.getString("SPO2"));
                    Head_to_toe_examination.setText(jsonObject.getString("Head_to_toe_examination"));
                    General_examination.setText(jsonObject.getString("General_Examination"));
                    Systematic_examination.setText(jsonObject.getString("Systematic_Examination"));
                    Treatment_given.setText(jsonObject.getString("Treatment_given"));
                    Course_in_Hospital.setText(jsonObject.getString("Course_in_Hospital"));
                    Course_in_Picu.setText(jsonObject.getString("Course_in_PICU"));
                    Course_in_ward.setText(jsonObject.getString("Course_in_Ward"));
                    Advice_on_discharge.setText(jsonObject.getString("Advice_on_Discharge"));
                    Review.setText(jsonObject.getString("Review"));
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