package com.example.androidd;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class patient_profile extends AppCompatActivity {

    private CircleImageView imageView;
    ImageView img;
    String id;

    private TextView nameTextView, genderTextView, dobTextView, pnameTextView, contactTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);
        Intent intent = getIntent();
        id = intent.getStringExtra("patient_id");
        img = findViewById(R.id.pat_logout);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceName = getDeviceName();
                logout(deviceName);

            }
        });
        imageView = findViewById(R.id.getProfile);
        nameTextView = findViewById(R.id.nameTextView);
        genderTextView = findViewById(R.id.GenderTextView);
        dobTextView = findViewById(R.id.dobTextView);
        pnameTextView = findViewById(R.id.pnameTextView);
        contactTextView = findViewById(R.id.contactTextView);

        // Replace "YOUR_ID" with the actual ID you want to fetch
        new GetData().execute(id);
    }

    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char firstChar = s.charAt(0);
        if (Character.isUpperCase(firstChar)) {
            return s;
        } else {
            return Character.toUpperCase(firstChar) + s.substring(1);
        }
    }

    public void logout(String device) {
        new LogoutAsyncTask().execute(device);
    }

    private class LogoutAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String device = params[0];
            // Replace with your actual API URL
            String apiurl = Api.api + "logout.php";
            try {
                URL url = new URL(apiurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);

                // Create the POST data
                String postData = "device_name=" + device;
                byte[] postDataBytes = postData.getBytes(StandardCharsets.UTF_8);

                // Set up the output stream
                OutputStream os = urlConnection.getOutputStream();
                os.write(postDataBytes);
                os.flush();
                os.close();

                // Get the response from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder responseStringBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    responseStringBuilder.append(line);
                }

                // Close the connections
                reader.close();
                urlConnection.disconnect();

                // Return the server response
                return responseStringBuilder.toString();
            } catch (Exception e) {
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

                    if ("success".equalsIgnoreCase(status)) {
                        // If the response is success, navigate to another page
                        Intent intent = new Intent(patient_profile.this, types_of_login.class);
                        startActivity(intent);
                        finish(); // Optionally, finish the current activity
                    } else {
                        // Handle other status or show an error message
                        // For example, you can display a Toast
                        Toast.makeText(patient_profile.this, "Logout failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Handle the case where the result is null (an error occurred)
                // For example, you can display a Toast
                Toast.makeText(patient_profile.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String url = Api.api + "get_patientProfile.php";
            StringBuilder result = new StringBuilder();

            try {
                URL requestUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Create the POST data
                String postData = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

                // Write the POST data to the connection
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(postData.getBytes());
                outputStream.flush();
                outputStream.close();

                // Get the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);

                // Assuming keys "name", "gender", "dob", "parent_name", "contact" in your JSON response
                String name = jsonObject.getString("Name");
                String gender = jsonObject.getString("Gender");
                String dob = jsonObject.getString("Date_Of_Birth");
                String pname = jsonObject.getString("Parent_Name");
                String contact = jsonObject.getString("Contact_No");
                String profilePicPath = jsonObject.getString("Profile_Pic");

                // Update UI
                nameTextView.setText(name);
                genderTextView.setText(gender);
                dobTextView.setText(dob);
                pnameTextView.setText(pname);
                contactTextView.setText(contact);
                String completeImageUrl = Api.api + profilePicPath;
                Picasso.get().load(completeImageUrl).into(imageView);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}
