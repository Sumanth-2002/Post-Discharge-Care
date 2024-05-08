package com.example.androidd;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class pt_tablets extends AppCompatActivity {

    private String courseNumber, did;
    private RecyclerView medicineRecyclerView;
    private List<String> medicineList;
    private String sourceLanguageCode="en";
    private String destinationLanguageCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pt_tablets);

        Intent intent = getIntent();
        courseNumber = intent.getStringExtra("COURSE_NUMBER");
        did = intent.getStringExtra("patient_id");

        destinationLanguageCode = intent.getStringExtra("des");
        if (destinationLanguageCode == null) {
            // Set default language code to English
            destinationLanguageCode = "en";
        }
        medicineRecyclerView = findViewById(R.id.medicineListView);
        medicineList = new ArrayList<>();

        // Instantiate FetchDataFromServer
        FetchDataFromServer fetchDataFromServer = new FetchDataFromServer();
        fetchDataFromServer.execute(did, courseNumber);
    }

    // AsyncTask to fetch data from the server
    private class FetchDataFromServer extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            // The URL of your PHP script
            String urlStr = Api.api + "get_medicine.php";

            try {
                URL url = new URL(urlStr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    // Set up the connection for a POST request
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);

                    // Create the request parameters
                    String id = params[0];
                    String courseNumber = params[1];
                    String postData = "id=" + did + "&Course_Name=" + courseNumber;

                    // Write the parameters to the request body
                    OutputStream outputStream = urlConnection.getOutputStream();
                    outputStream.write(postData.getBytes());
                    outputStream.flush();
                    outputStream.close();

                    // Read the response
                    InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }

                    bufferedReader.close();

                    // Parse the raw JSON data into a JSONArray
                    return new JSONArray(stringBuilder.toString());
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException | JSONException e) {
                Log.e("FetchDataFromServer", "Error fetching data", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            // Handle the result here
            if (result != null) {
                // Update the RecyclerView with the fetched medicine and duration
                updateRecyclerView(result);
            } else {
                Log.e("FetchDataFromServer", "Failed to fetch data");
            }
        }

        // Update the RecyclerView with the fetched medicine and duration
        private void updateRecyclerView(JSONArray jsonArray) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Assuming you have "Medicine_Name" and "Duration" keys in your JSON
                    String medicineName = jsonObject.getString("Medicine_Name");
                    String duration = jsonObject.getString("Duration");

                    // Concatenate medicine name and duration for display
                    String displayText = "Medicine             : " + medicineName + "\nDuration              : " + duration;

                    // Add the display text to the list
                    medicineList.add(displayText);
                }

                // Update the RecyclerView with the fetched medicine and duration
                MedicineAdapter adapter = new MedicineAdapter(medicineList);
                medicineRecyclerView.setLayoutManager(new LinearLayoutManager(pt_tablets.this));
                medicineRecyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                Log.e("FetchDataFromServer", "Error parsing JSON data", e);
            }
        }
    }

    // RecyclerView Adapter
    private class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

        private List<String> dataList;

        public MedicineAdapter(List<String> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_cardview_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String item = dataList.get(position);
            holder.bind(item);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Extract MedicineInfo from the display text
                    MedicineInfo medicineInfo = extractMedicineInfo(item);

                    // Create an Intent to navigate to the next activity
                    Intent intent = new Intent(pt_tablets.this, viewTablets.class);

                    // Pass data to the next activity using Intent extras
                    intent.putExtra("Medicine_Name", medicineInfo.getMedicineName());
                    intent.putExtra("Duration", medicineInfo.getDuration());
                    intent.putExtra("patient_id", did);
                    intent.putExtra("src",sourceLanguageCode);
                    intent.putExtra("des",destinationLanguageCode);
                    // Start the next activity
                    startActivity(intent);
                }
            });
        }
        // Add this method to your pt_tablets class
        private MedicineInfo extractMedicineInfo(String displayText) {
            String[] parts = displayText.split("\n");
            if (parts.length == 2) {
                String medicineName = parts[0].replace("Medicine             : ", "");
                String duration = parts[1].replace("Duration              : ", "");
                return new MedicineInfo(medicineName, duration);
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView medicineTextView,dur;

            public ViewHolder(View itemView) {
                super(itemView);

                medicineTextView = itemView.findViewById(R.id.medicineDoc);
                dur=itemView.findViewById(R.id.durationDoc);
            }

            public void bind(String item) {
                String[] parts = item.split("\n");
                if (parts.length == 2) {
                    languageTranslation languageTranslation = new languageTranslation(pt_tablets.this,sourceLanguageCode,destinationLanguageCode);
                    languageTranslation.startTranslations(parts[0],medicineTextView);
//                    medicineTextView.setText(parts[0]);
//                    dur.setText(parts[1]);
                    languageTranslation.startTranslations(parts[1],dur);
                }
            }
        }
    }

    // Data model class for MedicineInfo
    public class MedicineInfo {
        private String medicineName;
        private String duration;

        public MedicineInfo(String medicineName, String duration) {
            this.medicineName = medicineName;
            this.duration = duration;
        }

        public String getMedicineName() {
            return medicineName;
        }

        public String getDuration() {
            return duration;
        }
    }
}
