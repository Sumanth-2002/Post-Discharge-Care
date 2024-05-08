package com.example.androidd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class followup extends AppCompatActivity {
    private TextView geq1, geq2, geq3, geq4, geq5, deq1, deq2, deq3, deq4, deq5, deq6, selectedDatais;
    RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5, radioGroup6, radioGroup7, radioGroup8, radioGroup9, radioGroup10, radioGroup11;
    private List<TextView> generalSymptomTextViews;
    private List<TextView> dangerSymptomTextViews;
    Button submit;
    private String sourceLanguageCode = "en";
    private  String destinationLanguageCode;
    private String pid;
    private PopupWindow popupWindow;
    String text1, text2, text3, text4, text5, text6, text7, text8, text9, text10, text11;
    EditText Others;
    String id,o;
    String date;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String LAST_ENABLED_DATE_KEY = "lastEnabledDate";
    private RadioGroup[] generalRadioGroups;
    private RadioGroup[] dangerRadioGroups;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followup);
        Intent intent = getIntent();
        id = intent.getStringExtra("patient_id");
        date = intent.getStringExtra("selected_date");
        Others = findViewById(R.id.complaints);
        destinationLanguageCode = intent.getStringExtra("des");
        if (destinationLanguageCode == null) {
            // Set default language code to English
            destinationLanguageCode = "en";
        }
        submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendResponseToPhp();

            }

        });
        selectedDatais = findViewById(R.id.dateSelected);
        selectedDatais.setText(date);

        radioGroup1 = findViewById(R.id.general1);
        radioGroup2 = findViewById(R.id.general2);
        radioGroup3 = findViewById(R.id.general3);
        radioGroup4 = findViewById(R.id.general4);
        radioGroup5 = findViewById(R.id.general5);
        radioGroup6 = findViewById(R.id.danger1);
        radioGroup7 = findViewById(R.id.danger2);
        radioGroup8 = findViewById(R.id.danger3);
        radioGroup9 = findViewById(R.id.danger4);
        radioGroup10 = findViewById(R.id.danger5);
        radioGroup11 = findViewById(R.id.danger6);
        geq1 = findViewById(R.id.gq1);
        geq2 = findViewById(R.id.gq2);
        geq3 = findViewById(R.id.gq3);
        geq4 = findViewById(R.id.gq4);
        geq5 = findViewById(R.id.gq5);
        deq1 = findViewById(R.id.dq1);
        deq2 = findViewById(R.id.dq2);
        deq3 = findViewById(R.id.dq3);
        deq4 = findViewById(R.id.dq4);
        deq5 = findViewById(R.id.dq5);
        deq6 = findViewById(R.id.dq6);

        generalSymptomTextViews = new ArrayList<>();
        generalSymptomTextViews.add(geq1);
        generalSymptomTextViews.add(geq2);
        generalSymptomTextViews.add(geq3);
        generalSymptomTextViews.add(geq4);
        generalSymptomTextViews.add(geq5);

        dangerSymptomTextViews = new ArrayList<>();
        dangerSymptomTextViews.add(deq1);
        dangerSymptomTextViews.add(deq2);
        dangerSymptomTextViews.add(deq3);
        dangerSymptomTextViews.add(deq4);
        dangerSymptomTextViews.add(deq5);
        dangerSymptomTextViews.add(deq6);

        generalRadioGroups = new RadioGroup[]{radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5};
        dangerRadioGroups = new RadioGroup[]{radioGroup6, radioGroup7, radioGroup8, radioGroup9, radioGroup10, radioGroup11};

        // Fetch data when the activity is created
        new FetchQuestionnaireTask().execute(id); // Pass the desired ID to fetch data for

        // Check and enable/disable radio buttons based on the date
//        checkAndEnableRadioButtons();
    }

    private void showCustomPopup() {
        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_layout, null);

        // Set up the PopupWindow
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        // Set your custom message in the TextView inside the popup
        TextView popupText = popupView.findViewById(R.id.popupText);
        english en = new english(this, "en", destinationLanguageCode);
        en.translateText(popupText.getText().toString(), new TranslationCallback() {
            @Override
            public void onTranslationCompleted(String translatedText) {
                 popupText.setText(translatedText);
            }

            @Override
            public void onTranslationFailed() {
                // Handle translation failure if needed
            }
        });
        // Set up the close button in the popup
        Button closeButton = popupView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the popup when the close button is clicked
                popupWindow.dismiss();
                translateAllTexts();
            }
        });

        // Show the popup at the center of the screen
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }

    private void showCustomAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Create and show the alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // Set your custom layout for the alert dialog
        View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        builder.setView(customLayout);

        // Find the TextView in the custom layout
        TextView okButton = customLayout.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the dialog
                alertDialog.dismiss();
            }
        });


    }

    private void checkAndEnableRadioButtons() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String lastEnabledDate = preferences.getString(LAST_ENABLED_DATE_KEY, "");

        if (!isToday(lastEnabledDate)) {
            // Enable radio buttons since it's a new day
            enableRadioButtons();
            // Save the current date
            saveCurrentDate();
        } else {
            // Disable radio buttons since they were already enabled today
            disableRadioButtons();
        }
    }

    private void enableRadioButtons() {
        for (RadioGroup radioGroup : generalRadioGroups) {
            setRadioGroupEnabled(radioGroup, true);
        }

        for (RadioGroup radioGroup : dangerRadioGroups) {
            setRadioGroupEnabled(radioGroup, true);
        }
    }
//
    private void disableRadioButtons() {
        for (RadioGroup radioGroup : generalRadioGroups) {
            setRadioGroupEnabled(radioGroup, false);
        }

        for (RadioGroup radioGroup : dangerRadioGroups) {
            setRadioGroupEnabled(radioGroup, false);
        }
    }
//
    private void setRadioGroupEnabled(RadioGroup radioGroup, boolean enabled) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(enabled);
        }
    }

        private boolean isToday(String storedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        return currentDate.equals(storedDate);
    }
//
    private void saveCurrentDate() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        editor.putString(LAST_ENABLED_DATE_KEY, currentDate);
        editor.apply();
    }
    public void sendResponseToPhp() {

        String selectedOption1 = getSelectedOption(radioGroup1);
        String selectedOption2 = getSelectedOption(radioGroup2);
        String selectedOption3 = getSelectedOption(radioGroup3);
        String selectedOption4 = getSelectedOption(radioGroup4);
        String selectedOption5 = getSelectedOption(radioGroup5);
        String selectedOption6 = getSelectedOption(radioGroup6);
        String selectedOption7 = getSelectedOption(radioGroup7);
        String selectedOption8 = getSelectedOption(radioGroup8);
        String selectedOption9 = getSelectedOption(radioGroup9);
        String selectedOption10 = getSelectedOption(radioGroup10);
        String selectedOption11 = getSelectedOption(radioGroup11);
         o = Others.getText().toString();
        if(o.isEmpty()){
            o="Nothing";
        }else{
            english en = new english(this, destinationLanguageCode, "en");
            en.translateText(Others.getText().toString(), new TranslationCallback() {
                @Override
                public void onTranslationCompleted(String translatedText) {
                    o = translatedText;
                }

                @Override
                public void onTranslationFailed() {
                    // Handle translation failure if needed
                }
            });
        }

//        Toast.makeText(followup.this, "d"+text4, Toast.LENGTH_SHORT).show();
        if (selectedOption1.equalsIgnoreCase("Yes") || selectedOption2.equalsIgnoreCase("Yes") ||
                selectedOption3.equalsIgnoreCase("Yes") || selectedOption4.equalsIgnoreCase("Yes") ||
                selectedOption5.equalsIgnoreCase("Yes") || selectedOption6.equalsIgnoreCase("Yes") ||
                selectedOption7.equalsIgnoreCase("Yes") || selectedOption8.equalsIgnoreCase("Yes") ||
                selectedOption9.equalsIgnoreCase("Yes") || selectedOption10.equalsIgnoreCase("Yes") ||
                selectedOption11.equalsIgnoreCase("Yes")) {
            showCustomPopup();
            // Display popup methodshowCustomAlertDialog();
        } else {
            translateAllTexts();

        }
    }

    private void translateAllTexts() {
        translateText(geq1, "text1");
    }

    private void translateText(TextView textView, String variableName) {
        english en = new english(this, destinationLanguageCode, "en");
        en.translateText(textView.getText().toString(), new TranslationCallback() {
            @Override
            public void onTranslationCompleted(String translatedText) {

                assignVariable(variableName, translatedText);

            }

            @Override
            public void onTranslationFailed() {
                // Handle translation failure if needed
            }
        });
    }

    private void assignVariable(String variableName, String translatedText) {
        switch (variableName) {
            case "text1":
                text1 = translatedText;
                // Continue with other assignments or actions
                translateText(geq2, "text2");
                break;

            case "text2":
                text2 = translatedText;
                // Continue with other assignments or actions
                translateText(geq3, "text3");
                break;

            case "text3":
                text3 = translatedText;
                translateText(geq4, "text4");
                break;
            // Continue this pattern for text3 to text11
            case "text4":
                text4 = translatedText;
                translateText(geq5, "text5");
                break;
            case "text5":
                text5 = translatedText;
                translateText(deq1, "text6");
                break;
            case "text6":
                text6 = translatedText;
                translateText(deq2, "text7");
                break;
            case "text7":
                text7 = translatedText;
                translateText(deq3, "text8");
                break;
            case "text8":
                text8 = translatedText;
                translateText(deq4, "text9");
                break;
            case "text9":
                text9 = translatedText;
                translateText(deq5, "text10");
                break;
            case "text10":
                text10 = translatedText;
                translateText(deq6, "text11");
                break;
            case "text11":
                text11 = translatedText;
                sendDatatoDatabase();
                // All translations completed, perform any final actions
                break;

        }
    }

    private void sendDatatoDatabase() {
        String selectedOption1 = getSelectedOption(radioGroup1);
        String selectedOption2 = getSelectedOption(radioGroup2);
        String selectedOption3 = getSelectedOption(radioGroup3);
        String selectedOption4 = getSelectedOption(radioGroup4);
        String selectedOption5 = getSelectedOption(radioGroup5);
        String selectedOption6 = getSelectedOption(radioGroup6);
        String selectedOption7 = getSelectedOption(radioGroup7);
        String selectedOption8 = getSelectedOption(radioGroup8);
        String selectedOption9 = getSelectedOption(radioGroup9);
        String selectedOption10 = getSelectedOption(radioGroup10);
        String selectedOption11 = getSelectedOption(radioGroup11);

        JSONObject jsonData = new JSONObject();

        try {
            jsonData.put("date", date);
            jsonData.put("id", id);
            jsonData.put("response1", selectedOption1);
            jsonData.put("response2", selectedOption2);
            jsonData.put("response3", selectedOption3);
            jsonData.put("response4", selectedOption4);
            jsonData.put("response5", selectedOption5);
            jsonData.put("response6", selectedOption6);
            jsonData.put("response7", selectedOption7);
            jsonData.put("response8", selectedOption8);
            jsonData.put("response9", selectedOption9);
            jsonData.put("response10", selectedOption10);
            jsonData.put("response11", selectedOption11);

            jsonData.put("text1", text1);
            jsonData.put("text2", text2);
            jsonData.put("text3", text3);
            jsonData.put("text4", text4);
            jsonData.put("text5", text5);
            jsonData.put("text6", text6);
            jsonData.put("text7", text7);
            jsonData.put("text8", text8);
            jsonData.put("text9", text9);
            jsonData.put("text10", text10);
            jsonData.put("text11", text11);
            jsonData.put("others",o);
//            Toast.makeText(followup.this, "11" + text11, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String phpScriptUrl = Api.api + "questionnaire_response.php";
        new SendDataToServerTask(this).execute(phpScriptUrl, jsonData.toString(), id);
    }


    private String getSelectedOption(RadioGroup radioGroup) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            return selectedRadioButton.getText().toString();
        } else {
            return "";
        }
    }

    private class FetchQuestionnaireTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String apiUrl = Api.api + "get_questionnaire.php";

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    // Set the request method to POST
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);

                    // Write the ID to the output stream
                    urlConnection.getOutputStream().write(("id=" + id).getBytes("UTF-8"));

                    // Read the response
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
                Log.e("FetchQuestionnaireTask", "Error:", e);
                return null;
            }
        }


        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d("followup", "Got response" + result);
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    // Separate counters for general and danger categories
                    int generalCounter = 0;
                    int dangerCounter = 0;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String questionCategory = jsonObject.getString("Question_Category");
                        String question = jsonObject.getString("Question");

                        // Set data to TextViews based on question category
                        if ("general".equals(questionCategory)) {
                            setGeneralSymptomData(generalCounter, question);
                            generalCounter++;
                        } else if ("danger".equals(questionCategory)) {
                            setDangerSymptomData(dangerCounter, question);
                            dangerCounter++;
                        }
                    }
                } catch (JSONException e) {
                    Log.e("FetchQuestionnaireTask", "Error parsing JSON:", e);
                }
            }
        }

        private void setGeneralSymptomData(int index, String data) {
            if (index >= 0 && index < generalSymptomTextViews.size()) {
                TextView textView = generalSymptomTextViews.get(index);

                languageTranslation li = new languageTranslation(followup.this, "en", destinationLanguageCode);
                li.startTranslations(data, textView);
            }
        }

        private void setDangerSymptomData(int index, String data) {
            if (index >= 0 && index < dangerSymptomTextViews.size()) {
                TextView textView = dangerSymptomTextViews.get(index);
                languageTranslation li = new languageTranslation(followup.this, "en", destinationLanguageCode);
                li.startTranslations(data, textView);
            }
        }
    }

    private  class SendDataToServerTask extends AsyncTask<String, Void, String> {
        private WeakReference<followup> activityReference;

        // Constructor to get a reference to the activity
        public SendDataToServerTask(followup activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(String... params) {
            String urlStr = params[0];
            String jsonData = params[1];
            String pid = params[2];

            try {
                URL url = new URL(urlStr);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.getOutputStream().write(jsonData.getBytes("UTF-8"));

                // Read the response
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line).append('\n');
                }
                bufferedReader.close();

                return response.toString();

            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        }

        @Override

        protected void onPostExecute(String result) {
            followup activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            // Handle the result here
            if (result != null) {
                try {
                    Log.e("ss", "respomse" + result);
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.optString("status");
                    String message = jsonObject.optString("message");
                    if ("Success".equals(status)) {
                       Toast.makeText(activity, message+destinationLanguageCode, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(activity, patient_home.class);
                        intent.putExtra("patient_id", activity.id);
                        intent.putExtra("des",destinationLanguageCode);
                        startActivity(intent);
                    } else {

                        Toast.makeText(activity, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Update UI or perform actions for a null result
                // For example, show a Toast with a generic error message
                Toast.makeText(activity, "Error: Unable to process the request", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
