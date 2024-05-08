package com.example.androidd;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class doctor_profile extends AppCompatActivity {

    private CircleImageView imageView;
    ImageView img;
    String did;
    private TextView nameTextView, genderTextView, depaTextView, expTextView, contactTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        Button btn = findViewById(R.id.edit);
        Intent intent = getIntent();
        did = intent.getStringExtra("doctor_id");
        imageView = findViewById(R.id.getProfile);
        img = findViewById(R.id.doc_logout);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceName = getDeviceName();
                logout(deviceName);

            }
        });
        nameTextView = findViewById(R.id.nameTextView);
        genderTextView = findViewById(R.id.GenderTextView);
        depaTextView = findViewById(R.id.dobTextView);
        expTextView = findViewById(R.id.pnameTextView);
        contactTextView = findViewById(R.id.contactTextView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doctor_profile.this, doc_profileUpdate.class);
                intent.putExtra("doctor_id", did);
                startActivity(intent);
            }
        });

        // Replace "YOUR_ID" with the actual ID you want to fetch
        new GetData().execute(did);
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
                        Intent intent = new Intent(doctor_profile.this, types_of_login.class);
                        startActivity(intent);
                        finish(); // Optionally, finish the current activity
                    } else {
                        // Handle other status or show an error message
                        // For example, you can display a Toast
                        Toast.makeText(doctor_profile.this, "Logout failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Handle the case where the result is null (an error occurred)
                // For example, you can display a Toast
                Toast.makeText(doctor_profile.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(doctor_profile.this, doctor_home.class);
        intent.putExtra("doctor_id", did);
        startActivity(intent);
    }

    private class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String url = Api.api + "get_doctorProfile.php";
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
                String name = jsonObject.getString("Name");
                String gender = jsonObject.getString("Gender");
                String department = jsonObject.getString("Department");
                String experience = jsonObject.getString("Experience");
                String contact = jsonObject.getString("Contact_Number");
                String profilePicPath = jsonObject.getString("doc_profile");

                // Update UI
                nameTextView.setText(name);
                genderTextView.setText(gender);
                depaTextView.setText(department);
                expTextView.setText(experience);
                contactTextView.setText(contact);
                String completeImageUrl = Api.api + profilePicPath;
                Picasso.get().load(completeImageUrl).into(imageView);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
