package com.example.androidd;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class doc_profileUpdate extends AppCompatActivity {
    EditText name, gender, department, experience, contactno;
    String did;
    Button save;
    private boolean isUpdateInProgress = false;
    CircleImageView profile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_profile_update);
        name = findViewById(R.id.nameEdit);
        Intent intent=getIntent();
        did=intent.getStringExtra("doctor_id");
        gender = findViewById(R.id.GenderEdit);
        department = findViewById(R.id.departmentEdit);
        experience = findViewById(R.id.expEdit);
        contactno = findViewById(R.id.contactEdit);
        profile = findViewById(R.id.editProfile);
        save = findViewById(R.id.saveEdit);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isUpdateInProgress) {
                    isUpdateInProgress = true;
                    sendDataToDatabase();
                }
            }
        });

        new GetData().execute(did);
    }

// ... (your existing code)

    private void sendDataToDatabase() {
        showProgressBar();
        String updatedName = name.getText().toString().trim();
        String updatedGender = gender.getText().toString().trim();
        String updatedDepartment = department.getText().toString().trim();
        String updatedExperience = experience.getText().toString().trim();
        String updatedContact = contactno.getText().toString().trim();

        // Convert Bitmap to Base64
        Bitmap profileBitmap = ((BitmapDrawable) profile.getDrawable()).getBitmap();
        String profileBase64 = convertBitmapToBase64(profileBitmap);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Api.api+"update_doctor_profile.php";

        try {
            // Create a JSON object with the data
            JSONObject jsonData = new JSONObject();
            jsonData.put("id", did);
            jsonData.put("name", updatedName);
            jsonData.put("gender", updatedGender);
            jsonData.put("department", updatedDepartment);
            jsonData.put("experience", updatedExperience);
            jsonData.put("contact", updatedContact);
            jsonData.put("profile_pic", profileBase64);

            // Create a JsonObjectRequest with POST method
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonData,
                    response -> {
                        // Handle the response from the server
                        try {
                            String status = response.getString("status");
                            if ("success".equals(status)) {

                                    Toast.makeText(doc_profileUpdate.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
                                  Intent intent =new Intent(doc_profileUpdate.this,doctor_profile.class);
                                  intent.putExtra("doctor_id",did);
                                  startActivity(intent);

                            } else {

                                    Toast.makeText(doc_profileUpdate.this, "Failed to update details", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            hideProgressBar();
                        }
                    },
                    error -> {
                        // Handle errors here
                        runOnUiThread(() -> {
                            Toast.makeText(doc_profileUpdate.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                        hideProgressBar();
                    }
            );

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
            hideProgressBar();
        }
    }

// ... (your existing code)

    private void showProgressBar() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Delay the dismissal of the progress bar for 1 second (1000 milliseconds)
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                },
                1000
        );
    }
    private void hideProgressBar() {
        // Dismiss the progress dialog if it's showing
        ProgressDialog progressDialog = new ProgressDialog(this);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }





    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Camera", "Gallery"}, (dialog, which) -> {
            if (which == 0) {
                dispatchTakePictureIntent();
            } else if (which == 1) {
                pickImageFromGallery();
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageCaptureLauncher.launch(takePictureIntent);
    }

    private void pickImageFromGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imageGalleryLauncher.launch(pickIntent);
    }

    ActivityResultLauncher<Intent> imageCaptureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        profile.setImageBitmap(imageBitmap);
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> imageGalleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData() != null) {
                            try {
                                Uri selectedImageUri = result.getData().getData();
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                        doc_profileUpdate.this.getContentResolver(),
                                        selectedImageUri
                                );
                                profile.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
    );

    // Method to convert Bitmap to Base64
    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String url = Api.api+"get_doctorProfile.php";
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
                String Name = jsonObject.getString("Name");
                String Gender = jsonObject.getString("Gender");
                String Department = jsonObject.getString("Department");
                String Experience = jsonObject.getString("Experience");
                String Contact = jsonObject.getString("Contact_Number");
                String profilePicPath = jsonObject.getString("doc_profile");

                // Update UI
                name.setText(Name);
                gender.setText(Gender);
                department.setText(Department);
                experience.setText(Experience);
                contactno.setText(Contact);
                String completeImageUrl = Api.api + profilePicPath;
                Picasso.get().load(completeImageUrl).into(profile);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
