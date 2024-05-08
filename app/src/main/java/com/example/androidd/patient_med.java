package com.example.androidd;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class patient_med extends AppCompatActivity {
    String pid;
    private static String sourceLanguageCode = "en";
    private static String destinationLanguageCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_med);
        Intent intent = getIntent();
        pid = intent.getStringExtra("patient_id");

        destinationLanguageCode = intent.getStringExtra("des");
        if (destinationLanguageCode == null) {
            // Set default language code to English
            destinationLanguageCode = "en";
        }
        ListView courseListView = findViewById(R.id.courseListView);

        new FetchDataFromServer(courseListView).execute(pid);

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCourseNumber = (String) parent.getItemAtPosition(position);
                Log.d("patient_med.this", "selected" + selectedCourseNumber);

                String numericPart = selectedCourseNumber.replaceAll("[^\\d]", "");

                Intent intent = new Intent(patient_med.this, pt_tablets.class);
                intent.putExtra("COURSE_NUMBER", numericPart);
                intent.putExtra("patient_id", pid);
                intent.putExtra("des", destinationLanguageCode);

                startActivity(intent);
            }
        });
    }

    private static class FetchDataFromServer extends AsyncTask<String, Void, String> {

        private ListView courseListView;

        public FetchDataFromServer(ListView courseListView) {
            this.courseListView = courseListView;
        }

        @Override
        protected String doInBackground(String... params) {
            String urlStr = Api.api + "get_course_data.php";

            try {
                URL url = new URL(urlStr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);

                    String postData = "id=" + params[0];
                    urlConnection.getOutputStream().write(postData.getBytes());

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }

                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                Log.e("FetchDataFromServer", "Error fetching data", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                updateListView(result);
            } else {
                Log.e("FetchDataFromServer", "Failed to fetch data");
            }
        }

        private void updateListView(String jsonData) {
            try {
                JSONArray jsonArray = new JSONArray(jsonData);

                ArrayList<String> courseNumbersList = new ArrayList<>();
                HashSet<String> uniqueCourseNumbers = new HashSet<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String courseNumber = jsonObject.getString("Course_Name");

                    String numericPart = courseNumber.replaceAll("[^\\d]", "");

                    if (uniqueCourseNumbers.add(numericPart)) {
                        courseNumbersList.add("Course " + numericPart);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(courseListView.getContext(), android.R.layout.simple_list_item_1, courseNumbersList);
                courseListView.setAdapter(adapter);



            } catch (JSONException e) {
                Log.e("FetchDataFromServer", "Error parsing JSON data", e);
            }
        }



        // Inside patient_med class
        // Inside patient_med class


    }
}
