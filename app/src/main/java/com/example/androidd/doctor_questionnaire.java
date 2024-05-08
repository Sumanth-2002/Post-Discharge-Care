package com.example.androidd;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class doctor_questionnaire extends AppCompatActivity {
    private EditText ges1, ges2, ges3, ges4, ges5, des1, des2, des3, des4, des5, des6;
    private Button saveButton;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_questionnaire);
        Intent intent = getIntent();
        id = intent.getStringExtra("patient_id");
        ges1 = findViewById(R.id.ges1);
        ges2 = findViewById(R.id.ges2);
        ges3 = findViewById(R.id.ges3);
        ges4 = findViewById(R.id.ges4);
        ges5 = findViewById(R.id.ges5);
        des1 = findViewById(R.id.des1);
        des2 = findViewById(R.id.des2);
        des3 = findViewById(R.id.des3);
        des4 = findViewById(R.id.des4);
        des5 = findViewById(R.id.des5);
        des6 = findViewById(R.id.des6);

        saveButton = findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void saveData() {
        String generalSymptoms = "general";
        String dangerSymptoms = "danger";


        Map<String, String> data = new HashMap<>();

        // Check if text is not changed, then send default text
        data.put(generalSymptoms + "_ges1", (ges1.getText().toString().trim().equals("Not Drinking") ? "Not Drinking" : ges1.getText().toString().trim()));
        data.put(generalSymptoms + "_ges2", (ges2.getText().toString().trim().equals("Vomits Everything") ? "Vomits Everything" : ges2.getText().toString().trim()));
        data.put(generalSymptoms + "_ges3", (ges3.getText().toString().trim().equals("Convulsions") ? "Convulsions" : ges3.getText().toString().trim()));
        data.put(generalSymptoms + "_ges4", (ges4.getText().toString().trim().equals("Lethargy") ? "Lethargy" : ges4.getText().toString().trim()));
        data.put(generalSymptoms + "_ges5", (ges5.getText().toString().trim().equals("Unconsciousness") ? "Unconsciouness" : ges5.getText().toString().trim()));

        data.put(dangerSymptoms + "_des1", (des1.getText().toString().trim().equals("Fast Breathing") ? "Fast Breathing" : des1.getText().toString().trim()));
        data.put(dangerSymptoms + "_des2", (des2.getText().toString().trim().equals("Fatigue") ? "Fatigue" : des2.getText().toString().trim()));
        data.put(dangerSymptoms + "_des3", (des3.getText().toString().trim().equals("Fever with rash") ? "Fever with rash" : des3.getText().toString().trim()));
        data.put(dangerSymptoms + "_des4", (des4.getText().toString().trim().equals("Ear with discharge/pain") ? "Ear with discharge/pain" : des4.getText().toString().trim()));
        data.put(dangerSymptoms + "_des5", (des5.getText().toString().trim().equals("Irritable") ? "Irritable" : des5.getText().toString().trim()));
        data.put(dangerSymptoms + "_des6", (des6.getText().toString().trim().equals("Abnormal Body movements") ? "Abnormal Body Movements" : des6.getText().toString().trim()));

        // Send the data to your PHP file
        new SaveDataTask().execute(data);
    }

    private class SaveDataTask extends AsyncTask<Map<String, String>, Void, String> {
        @Override
        protected String doInBackground(Map<String, String>... params) {
            if (params.length == 0) {
                return null;
            }

            Map<String, String> data = params[0];
            String phpUrl = Api.api+"questionnaire.php"; // Replace with the actual URL of your PHP file

            StringBuilder responseBuilder = new StringBuilder();

            try {
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    String category = entry.getKey().toLowerCase();
                    String text = entry.getValue();
                    // Replace with your desired ID

                    // Create a URL object with the PHP endpoint
                    URL url = new URL(phpUrl);

                    // Create a HttpURLConnection object
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // Set the request method to POST
                    connection.setRequestMethod("POST");

                    // Enable input/output streams
                    connection.setDoOutput(true);
                    connection.setDoInput(true);

                    // Create a data output stream from the connection
                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

                    // Create a query string to send to the PHP file
                    String query = String.format("id=%s&category=%s&text=%s", id, category, text);

                    // Write the query string to the output stream
                    outputStream.writeBytes(query);

                    // Flush and close the output stream
                    outputStream.flush();
                    outputStream.close();

                    // Get the response code from the connection
                    int responseCode = connection.getResponseCode();

                    // Check if the request was successful (HTTP 200)
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Read the response from the server
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            responseBuilder.append(line);
                        }

                        // Close the reader
                        reader.close();
                    } else {
                        // Print the error message
                        responseBuilder.append("Error: ").append(responseCode);
                    }

                    // Disconnect the connection
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return responseBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            // Display the server response as a Toast message
            Toast.makeText(doctor_questionnaire.this, result, Toast.LENGTH_SHORT).show();

            finish();
        }
    }
}
