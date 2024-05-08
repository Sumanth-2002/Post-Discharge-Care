package com.example.androidd;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class view_report extends AppCompatActivity {
    String id;
    TextView reportTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        Intent intent=getIntent();
        id=intent.getStringExtra("patient_id");

        reportTimeTextView = findViewById(R.id.reportTimeTextView);

        // Execute AsyncTask to get report times
        new GetReportTimesTask().execute(id);
    }

    private class GetReportTimesTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            String id = params[0];
            String apiUrl = Api.api+"get_reportTime.php";

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                // Set up the connection for a POST request
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);

                // Create the request parameters
                String postData = "id=" + id;

                // Write the parameters to the request body
                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(postData.getBytes());
                outputStream.flush();
                outputStream.close();

                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                reader.close();

                // Parse the JSON response
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());

                // Extract report times from the JSON array
                List<String> reportTimesList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject reportObject = jsonArray.getJSONObject(i);
                    String reportTime = reportObject.getString("report_time");
                    String reportDate = reportObject.getString("report_date");
                    String formattedReportTime = "Report Time " + reportTime + " : " + reportDate + "\n";
                    reportTimesList.add(formattedReportTime);
                }

                return reportTimesList;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<String> result) {

            // Update UI with the formatted report times
            if (result != null && !result.isEmpty()) {
                // Join the list of formatted report times into a single string
                String reportTimesString = TextUtils.join("\n", result);
                reportTimeTextView.setText(reportTimesString);
            } else {
                reportTimeTextView.setText("Error fetching report times");
            }
        }
    }
}
