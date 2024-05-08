package com.example.androidd;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class doc_questionnaireResponse extends AppCompatActivity {

    private TextView startDateTextView;
    private TextView endDateTextView;

    private Calendar startDateCalendar;
    private Calendar endDateCalendar;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_questionnaire_response);
        Intent intent = getIntent();
        id = intent.getStringExtra("patient_id");
        startDateTextView = findViewById(R.id.startDate);
        endDateTextView = findViewById(R.id.endDate);

        startDateCalendar = Calendar.getInstance();
        endDateCalendar = Calendar.getInstance();

        ImageView startCalendarImageView = findViewById(R.id.startCalendar);
        ImageView endCalendarImageView = findViewById(R.id.endCalendar);
        ImageView search = findViewById(R.id.send);

        startCalendarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(startDateCalendar, startDateTextView, endDateCalendar);
            }
        });

        endCalendarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(endDateCalendar, endDateTextView, startDateCalendar);
            }
        });

        // Add a button click listener to send dates to PHP
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compareStartAndEnd();
            }
        });
    }

    private void showDatePickerDialog(final Calendar calendar, final TextView dateTextView, final Calendar comparisonCalendar) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, monthOfYear);
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Calendar today = Calendar.getInstance();

                // Update the calendar only if the selected date is greater than the comparison date
                calendar.setTime(selectedDate.getTime());
                updateDateTextView(calendar, dateTextView);

                // Show an error message or handle the situation where the selected date is not valid
            }
        };

        new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void updateDateTextView(Calendar calendar, TextView dateTextView) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(calendar.getTime());
        dateTextView.setText(dateString);
    }

    private void compareStartAndEnd() {
        String startDate = startDateTextView.getText().toString();
        String endDate = endDateTextView.getText().toString();

        if (compareDates(startDate, endDate) <= 0) {
            sendDatesToPHP();
        } else {
            // Show an error message if endDate is not greater than startDate
            Toast.makeText(doc_questionnaireResponse.this, "End date must be greater than start date", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void sendDatesToPHP() {
        String startDate = startDateTextView.getText().toString();
        String endDate = endDateTextView.getText().toString();
        // Replace 'your_id' with the actual ID

        // Check if endDate is greater than startDate
        // Create a JSON object to send to the server
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("startDate", startDate);
            jsonRequest.put("endDate", endDate);
            jsonRequest.put("id", id);

            // Use AsyncTask or any other method to send data to your server
            // For simplicity, I'll use AsyncTask here
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    try {
                        // Your server URL
                        String url = Api.api + "get_responses.php";
                        // Send the JSON data to the server
                        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setDoOutput(true);
                        OutputStream outputStream = urlConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        bufferedWriter.write(jsonRequest.toString());
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();

                        // Get the server response
                        InputStream inputStream = urlConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            response.append(line);
                        }
                        bufferedReader.close();
                        inputStream.close();

                        // Return the server response
                        return response.toString();

                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(String result) {
                    // Handle the server response here
                    if (result != null) {
                        try {
                            JSONObject jsonResponse = new JSONObject(result);
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");

                            if ("Success".equals(status)) {
                                // Data retrieved successfully
                                JSONArray responseData = jsonResponse.getJSONArray("data");

                                if (responseData == null || responseData.length() == 0) {
                                    // Handle the case when "data" array is empty
                                    TextView resultTextView = findViewById(R.id.resultTextView);
                                    resultTextView.setText("No responses for this duration");
                                }else {
                                    // Group symptoms and responses by date
                                    Map<String, List<String>> groupedData = new LinkedHashMap<>();
                                    for (int i = 0; i < responseData.length(); i++) {
                                        JSONObject entry = responseData.getJSONObject(i);
                                        String date = entry.getString("date");
                                        String symptom = entry.getString("symptom");
                                        String response = entry.getString("response");

                                        // Create or retrieve the list for the date
                                        List<String> symptomsAndResponses = groupedData.get(date);
                                        if (symptomsAndResponses == null) {
                                            symptomsAndResponses = new ArrayList<>();
                                            groupedData.put(date, symptomsAndResponses);
                                        }

                                        // Add symptom and response to the list
                                        symptomsAndResponses.add(symptom + ": " + response);
                                    }

                                    // Display the grouped data
                                    StringBuilder displayText = new StringBuilder();
                                    for (Map.Entry<String, List<String>> entry : groupedData.entrySet()) {
                                        String date = entry.getKey();
                                        List<String> symptomsAndResponses = entry.getValue();

                                        // Display date
                                        displayText.append(date).append("\n");

                                        // Display symptoms and responses
                                        for (String symptomAndResponse : symptomsAndResponses) {
                                            displayText.append("- ").append(symptomAndResponse).append("\n");
                                        }

                                        // Add a line break between different dates
                                        displayText.append("\n");
                                    }

                                    // Now, 'displayText' contains the formatted data, and you can display it as needed
                                    // For example, you can show it in a TextView
                                    TextView resultTextView = findViewById(R.id.resultTextView);
                                    resultTextView.setText(displayText.toString());
                                }
                            } else {
                                // Error handling
                                Toast.makeText(doc_questionnaireResponse.this, message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Handle the case where result is null
                        Toast.makeText(doc_questionnaireResponse.this, "Error sending data to the server", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Helper method to compare two date strings
    private int compareDates(String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            startCalendar.setTime(dateFormat.parse(startDate));
            endCalendar.setTime(dateFormat.parse(endDate));
            return startCalendar.compareTo(endCalendar);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
