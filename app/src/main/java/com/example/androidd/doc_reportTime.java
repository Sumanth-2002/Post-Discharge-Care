package com.example.androidd;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class doc_reportTime extends AppCompatActivity {

    private TextView reportTimeTextView;
    private ArrayList<String> datesList;
    private ArrayList<String> countsList;
    private Button reset, save;
    private String id; // Replace with your actual ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_report_time);
        Intent intent = getIntent();
        id = intent.getStringExtra("patient_id");
        reset = findViewById(R.id.reset);
        save = findViewById(R.id.save);
        reportTimeTextView = findViewById(R.id.report_time_text_view);
        datesList = new ArrayList<>();
        countsList = new ArrayList<>();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear all entered text and reset lists
                reportTimeTextView.setText("");
                datesList.clear();
                countsList.clear();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the method to send data to the PHP file
                sendDataToServer();
            }
        });

        ImageView plusIcon = findViewById(R.id.Report_time_plus);
        plusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }

    public void onReportTimePlusClick(View view) {
        showDatePicker();
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // Adjust month + 1 to get the correct month value (since it's zero-based)
                        String formattedMonth = (month + 1 < 10) ? "0" + (month + 1) : String.valueOf(month + 1);
                        String formattedDay = (day < 10) ? "0" + day : String.valueOf(day);

                        // Format the date as "yyyy-MM-dd"
                        String date = year + "-" + formattedMonth + "-" + formattedDay;

                        // Append the new text based on the counter with spacing
                        String newText = "Report Time " + (countsList.size() + 1) + ": " + date + "\n";

                        // Add spacing between each report time
                        if (!reportTimeTextView.getText().toString().isEmpty()) {
                            newText = "\n" + newText;
                        }

                        // Update the TextView with the combined text
                        reportTimeTextView.append(newText);

                        // Add date and count to the respective lists
                        datesList.add(date);
                        countsList.add(String.valueOf(countsList.size() + 1));

                        // Apply margin to the TextView
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) reportTimeTextView.getLayoutParams();
                        params.setMarginStart((int) getResources().getDimension(R.dimen.report_time_margin_start));
                        reportTimeTextView.setLayoutParams(params);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void sendDataToServer() {
        // Check if there are dates and counts to send
        if (!datesList.isEmpty() && !countsList.isEmpty()) {
            // Log the values of datesList and countsList
            Log.d("doc_reportTime", "Dates: " + datesList.toString());
            Log.d("doc_reportTime", "Counts: " + countsList.toString());

            // Convert the lists to arrays
            String[] datesArray = datesList.toArray(new String[0]);
            String[] countsArray = countsList.toArray(new String[0]);

            // Use Retrofit to send the data to your PHP file
            sendPostRequest(Integer.parseInt(id), datesArray, countsArray);
        } else {
            // Handle the case where datesList or countsList is empty
            Log.e("Error", "No data to send");
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendPostRequest(int id, String[] dates, String[] counts) {
        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.api) // Update the base URL
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        // Create an instance of your Retrofit interface
        YourApiService apiService = retrofit.create(YourApiService.class);

        // Create a Call object for your API method
        Call<JsonObject> call = apiService.sendData(id, dates, counts);

        // Make an asynchronous request
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response here
                    Toast.makeText(doc_reportTime.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("patient_id", id);

                    // Set the result and finish the current activity
                    setResult(doc_reportTime.RESULT_OK, intent);
                    finish();
                } else {
                    // Handle the error response here
                    handleError(response);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // Handle the failure here
                Log.e("API", "Error: " + t.getMessage());

                // Check if it's a network error or another type of error
                if (t instanceof IOException) {
                    Toast.makeText(doc_reportTime.this, "Network error", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle other types of errors
                    Toast.makeText(doc_reportTime.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }

                // Add more logs
                t.printStackTrace();
            }
        });
    }

    private void handleError(Response<JsonObject> response) {
        // Handle specific error cases based on HTTP status codes
        if (response.code() == 400) {
            // Bad request
            Toast.makeText(doc_reportTime.this, "Bad request: Invalid data", Toast.LENGTH_SHORT).show();
        } else if (response.code() == 401) {
            // Unauthorized
            Toast.makeText(doc_reportTime.this, "Unauthorized: Authentication failed", Toast.LENGTH_SHORT).show();
        } else {
            // Generic error message for other status codes
            Toast.makeText(doc_reportTime.this, "Failed to save data", Toast.LENGTH_SHORT).show();
        }
    }
}
