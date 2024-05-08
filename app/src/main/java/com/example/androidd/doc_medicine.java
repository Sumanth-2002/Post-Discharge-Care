package com.example.androidd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class doc_medicine extends AppCompatActivity {
    private String id;
    private ArrayList<String> Ids = new ArrayList<>();
    private ArrayList<String> medicineNames = new ArrayList<>();
    private ArrayList<String> courseNames = new ArrayList<>();
    private ArrayList<String> courseDurations = new ArrayList<>();
    private ArrayList<String> frequencies = new ArrayList<>();
    private ArrayList<String> guidelinesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_medicine);
        Intent intent = getIntent();
        id = intent.getStringExtra("patient_id");
    }

    public void showMedicineDialog(View view) {
        MedicineInputDialog dialog = new MedicineInputDialog(this);
        dialog.show();
    }

    public void displayMedicineInformation(
            String courseName, String medicineName, String courseDuration, String frequency, String guidelines) {
        // Add details to the ArrayLists
        Ids.add(id);
        courseNames.add(courseName);
        medicineNames.add(medicineName);
        courseDurations.add(courseDuration);
        frequencies.add(frequency);
        guidelinesList.add(guidelines);

        // Display details on the screen
        displayMedicineDetails();
    }

    private void displayMedicineDetails() {
        Log.e("hi", "course_number" + courseNames.toString());
        LinearLayout linearLayout = findViewById(R.id.medicineLayout);
        // Clear the existing views to avoid duplication
        linearLayout.removeAllViews();

        for (int i = 0; i < medicineNames.size(); i++) {
            // Create CardView for each medicine entry
            CardView cardView = getFormattedTitleText(
                    courseNames.get(i),
                    medicineNames.get(i),
                    courseDurations.get(i),
                    frequencies.get(i),
                    guidelinesList.get(i)
            );

            // Set top margin for CardView
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 30, 0, 0);
            cardView.setLayoutParams(layoutParams);

            // Add the CardView to the LinearLayout
            linearLayout.addView(cardView);
        }

        // Example: Enable the Save button when a medicine is added
        enableSaveButton();
    }

    private CardView getFormattedTitleText(
            String courseName, String medicineName, String courseDuration, String frequency, String guidelines) {
        CardView cardView = (CardView) getLayoutInflater().inflate(R.layout.medicine_card_view, null);

        // Find TextViews in the custom layout
        TextView medicineTextView = cardView.findViewById(R.id.medicineDoc);
        TextView courseNameTextView = cardView.findViewById(R.id.courseNum);
        TextView durationTextView = cardView.findViewById(R.id.durationDoc);
        TextView frequencyTextView = cardView.findViewById(R.id.FrequencyDoc);
        TextView guidelinesTextView = cardView.findViewById(R.id.Guidelines);

        // Set the text for each TextView
        medicineTextView.setText("Medicine             : " + medicineName);
        courseNameTextView.setText("Course Number : " + courseName);
        durationTextView.setText("Duration              : " + courseDuration);
        frequencyTextView.setText("Frequency          : " + frequency);
        guidelinesTextView.setText("Guidelines          : " + guidelines);

        return cardView;
    }

    public void enableSaveButton() {
        // Enable the Save button when a medicine is added
        Button saveMedButton = findViewById(R.id.saveMed);
        saveMedButton.setEnabled(true);
        saveMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMedicineToDatabase();
            }
        });
    }

    public void saveMedicineToDatabase() {
        // Create a JSON object to store the medicine details
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);

        // Create a JSON array to store the 'data' array
        JsonArray dataArray = new JsonArray();

        for (int i = 0; i < medicineNames.size(); i++) {
            JsonObject medicineJson = new JsonObject();
            medicineJson.addProperty("id", Ids.get(i));
            medicineJson.addProperty("course_name", courseNames.get(i));
            medicineJson.addProperty("medicine_name", medicineNames.get(i));
            medicineJson.addProperty("course_duration", courseDurations.get(i));
            medicineJson.addProperty("frequency", frequencies.get(i));
            medicineJson.addProperty("guidelines", guidelinesList.get(i));

            // Add the medicineJson to the 'data' array
            dataArray.add(medicineJson);
        }

        // Add the 'id' and 'data' array to the main jsonObject
        jsonObject.add("data", dataArray);

        // Call a method to send the JSON object to the PHP file for database insertion using Retrofit
        sendToPhpFile(jsonObject);
    }

    private void sendToPhpFile(JsonObject jsonObject) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.api)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Medicine apiService = retrofit.create(Medicine.class);
        Call<JsonElement> call = apiService.saveMedicine(jsonObject);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    JsonElement jsonResponse = response.body();

                    if (jsonResponse != null) {
                        if (jsonResponse.isJsonObject()) {
                            // Handle JSON object
                            JsonObject jsonObject = jsonResponse.getAsJsonObject();

                            if (jsonObject.has("status")) {
                                String status = jsonObject.get("status").getAsString();
                                if ("success".equals(status)) {
                                    // Handle success
                                    Toast.makeText(doc_medicine.this, "Successfully inserted", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.putExtra("patient_id", id);

                                    // Set the result and finish the current activity
                                    setResult(doc_medicine.RESULT_OK, intent);
                                    finish();
                                } else {
                                    // Handle error
                                    if (jsonObject.has("message")) {
                                        String message = jsonObject.get("message").getAsString();
                                        Toast.makeText(doc_medicine.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(doc_medicine.this, "Unknown error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                // Handle unexpected response format
                                Toast.makeText(doc_medicine.this, "Unexpected response format", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle unexpected response format
                            Toast.makeText(doc_medicine.this, "Unexpected response format", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle unexpected response format
                        Toast.makeText(doc_medicine.this, "Unexpected response format", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the error case
                    Toast.makeText(doc_medicine.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                // Handle the failure case
                Toast.makeText(doc_medicine.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("doc_medicine", "Error: " + t.getMessage());
            }
        });
    }
}
