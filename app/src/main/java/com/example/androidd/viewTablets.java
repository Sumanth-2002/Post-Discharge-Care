package com.example.androidd;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class viewTablets extends AppCompatActivity {
    TextView medhead, med_name, get_duration, get_frequency, get_guidelines, getDate;
    String id;

    private ProgressDialog progressDialog;
    private static final String TAG = "VIEW_TABLETS_TAG";
    private String sourceLanguageCode="en"; // Set default source language code
    private String destinationLanguageCode;

    String duration;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tablets);
        medhead = findViewById(R.id.getMedicine);
        med_name = findViewById(R.id.viewMedicine);
        get_duration = findViewById(R.id.viewDuration);
        get_frequency = findViewById(R.id.viewFrequency);
        get_guidelines = findViewById(R.id.viewGuidelines);
        getDate = findViewById(R.id.viewDate);

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        // Retrieve data from the Intent
        Intent intent = getIntent();
        String medicineName = intent.getStringExtra("Medicine_Name");
        id = intent.getStringExtra("patient_id");
        duration = intent.getStringExtra("Duration");

        destinationLanguageCode = intent.getStringExtra("des");
        if (destinationLanguageCode == null) {
            // Set default language code to English
            destinationLanguageCode = "en";
        }
        // Set the retrieved values to TextViews
        medhead.setText(medicineName);
        med_name.setText(medicineName);
        get_duration.setText(duration);

        // Fetch additional details (frequency and guidelines) from the server using Volley
        fetchDataUsingVolley(medicineName);
    }

    private void fetchDataUsingVolley(String medicineName) {
        String url = Api.api + "get_complete_medicine.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Create a JSONObject for the POST parameters
        JSONObject postData = new JSONObject();
        try {
            postData.put("Medicine_Name", medicineName);
            postData.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Use JsonObjectRequest for a JSON Object response
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Parse the JSON data and update the TextViews
                        updateTextViews(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyRequest", "Error fetching data", error);
                        // Notify the user about the failure, for example, show a Toast message
                        Toast.makeText(viewTablets.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the queue
        requestQueue.add(jsonObjectRequest);
    }

    // Parse the JSON data and update the TextViews
    private void updateTextViews(JSONObject jsonData) {
        try {
            Log.d("VolleyRequest", "Response received: " + jsonData.toString());

            // Check if the response contains an error key
            if (jsonData.has("error")) {
                String errorMessage = jsonData.getString("error");
                Log.e("VolleyRequest", "Error: " + errorMessage);
                // Handle the error message (if needed)
            } else {
                // Get the values for Frequency and Guidelines
                String frequency = jsonData.getString("Frequency");
                String guidelines = jsonData.getString("Guidelines");
                String date = jsonData.getString("date");

                // Set the retrieved values to TextViews
                translateTextAndSetValues(frequency, guidelines, date, duration);
            }
        } catch (JSONException e) {
            Log.e("VolleyRequest", "Error parsing JSON data", e);
        }
    }

    private void translateTextAndSetValues(String frequency, String guidelines, String date, String duration) {
        // Translate frequency, guidelines, and date
        languageTranslation li = new languageTranslation(viewTablets.this, sourceLanguageCode, destinationLanguageCode);
//        Toast.makeText(this, "" + destinationLanguageCode, Toast.LENGTH_SHORT).show();
        li.startTranslations(frequency, get_frequency);
        li.startTranslations(guidelines, get_guidelines);
        li.startTranslations(date, getDate);
        li.startTranslations(duration, get_duration);

    }


}
