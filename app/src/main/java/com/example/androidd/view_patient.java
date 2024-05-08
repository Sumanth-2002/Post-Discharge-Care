package com.example.androidd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class view_patient extends AppCompatActivity {

    private CircleImageView imageView;
    ImageView b11, b12, b13, b14;
    Button save;
    String URL = Api.api + "insert_patient.php/";
    String id, did;
    private EditText Id, Name, ContactNo, Gender, DateOfBirth, Height, Weight, ParentName, AdmittedOn, DischargeOn;
    TextView discharge, medication, report, followUp;
    private static final String FETCH_URL = Api.api + "fetch.php/";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getStringExtra("patient_id");
        did = intent.getStringExtra("doctor_id");
        setContentView(R.layout.activity_view_patient);
        b11 = findViewById(R.id.addFollow);
        b12 = findViewById(R.id.addDischargeSummary);
        b13 = findViewById(R.id.addMedication);
        b14 = findViewById(R.id.addReportTime);
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_patient.this, doc_discharge.class);
                intent.putExtra("patient_id", id);
                startActivity(intent);
            }
        });
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_patient.this, doc_medicine.class);
                intent.putExtra("patient_id", id);
                startActivity(intent);
            }
        });
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_patient.this, doc_reportTime.class);
                intent.putExtra("patient_id", id);
                startActivity(intent);
            }
        });
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_patient.this, doctor_questionnaire.class);
                intent.putExtra("patient_id", id);
                startActivity(intent);
            }
        });
        Id = findViewById(R.id.Id);
        Name = findViewById(R.id.Name);
        ContactNo = findViewById(R.id.Contact_No);
        Gender = findViewById(R.id.Gender);
        DateOfBirth = findViewById(R.id.dob);
        Height = findViewById(R.id.Height);
        Weight = findViewById(R.id.Weight);
        ParentName = findViewById(R.id.Parent_Name);
        AdmittedOn = findViewById(R.id.Admitted_On);
        DischargeOn = findViewById(R.id.Discharge_On);
        imageView = findViewById(R.id.view_profile);
        discharge = findViewById(R.id.viewDischargeSummary);
        medication = findViewById(R.id.viewMedication);
        report = findViewById(R.id.viewReportTime);
        followUp = findViewById(R.id.viewFollow);
        medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_patient.this, patient_med.class);
                intent.putExtra("patient_id", id.toString());
                startActivity(intent);
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_patient.this, view_report.class);
                intent.putExtra("patient_id", id.toString());
                startActivity(intent);

            }
        });
        followUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_patient.this, doc_questionnaireResponse.class);
                intent.putExtra("patient_id", id.toString());
                startActivity(intent);

            }
        });
        discharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view_patient.this, view_discharge.class);
                intent.putExtra("patient_id", id.toString());
                startActivity(intent);
            }
        });
        save = findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploaddata();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
            }
        });


        Id.setText(id);


        // Call AsyncTask to fetch patient details
        new FetchPatientDetailsTask().execute(FETCH_URL, id);
    }
    public void selectDate(View view) {
        final EditText editText = (EditText) view;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Adjust month value to start from 1 instead of 0
                month += 1;

                // Format the date as "yyyy-mm-dd"
                String date = String.format(Locale.getDefault(), "%d-%02d-%02d", year, month, dayOfMonth);
                editText.setText(date);
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    // AsyncTask to fetch patient details
    private class FetchPatientDetailsTask extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... params) {
            String fetchUrl = params[0];
            String patientId = params[1];

            try {
                URL url = new URL(fetchUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Prepare POST data
                String postData = "id=" + URLEncoder.encode(patientId, "UTF-8");

                // Write POST data to the connection
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(postData);
                writer.flush();
                writer.close();
                os.close();

                // Convert InputStream to String
                String jsonString = Utils.convertInputStreamToString(connection.getInputStream());

                // Parse JSON array response
                return new JSONArray(jsonString);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            if (result != null && result.length() > 0) {
                try {
                    // Assuming the first element in the array is the patient details
                    JSONObject patientObject = result.getJSONObject(0);

                    // Extract patient details
                    String name = patientObject.getString("Name");
                    String contactNo = patientObject.getString("Contact_No");
                    String gender = patientObject.getString("Gender");
                    String dateOfBirth = patientObject.getString("Date_Of_Birth");
                    String height = patientObject.getString("Height");
                    String weight = patientObject.getString("Weight");
                    String parentName = patientObject.getString("Parent_Name");
                    String admittedOn = patientObject.getString("Admitted_On");
                    String dischargeOn = patientObject.getString("Discharge_On");
                    String profile = patientObject.getString("Profile_Pic");
//                    Toast.makeText(view_patient.this, "fd" + profile, Toast.LENGTH_SHORT).show();
                    // Update TextViews with patient details
                    Id.setText(id);
                    Name.setText(name);
                    ContactNo.setText(contactNo);
                    Gender.setText(gender);
                    DateOfBirth.setText(dateOfBirth);
                    Height.setText(height);
                    Weight.setText(weight);
                    ParentName.setText(parentName);
                    AdmittedOn.setText(admittedOn);
                    DischargeOn.setText(dischargeOn);

                    Picasso.get().load(Api.api + profile).into(imageView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // Decode Base64 string to Bitmap
    private Bitmap decodeBase64(String base64) {
        byte[] decodedBytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private void uploaddata() {
        String pid = Id.getText().toString();
        String name = Name.getText().toString();
        String gender = Gender.getText().toString();
        String contactNo = ContactNo.getText().toString();
        String dateOfBirth = DateOfBirth.getText().toString();
        String height = Height.getText().toString();
        String weight = Weight.getText().toString();
        String parentName = ParentName.getText().toString();
        String admittedOn = AdmittedOn.getText().toString();
        String dischargedOn = DischargeOn.getText().toString();
        Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        String encodedImage = convertBitmapToBase64(imageBitmap);

        // Prepare the data for the server request
        Map<String, String> data = new HashMap<>();
        data.put("Id", pid);
        data.put("password", pid.substring(pid.length() - 4));
        data.put("Name", name);
        data.put("Gender", gender);
        data.put("Contact_No", contactNo);
        data.put("Date_of_Birth", dateOfBirth);
        data.put("Parent_Name", parentName);
        data.put("Height", height);
        data.put("Weight", weight);
        data.put("Admitted_On", admittedOn);
        data.put("Discharge_On", dischargedOn);
        data.put("Profile_pic", encodedImage);
        sendDataToServer(data);
    }

    private void sendDataToServer(Map<String, String> data) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return data;
            }
        };

        // Customize the retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Initialize the Volley request queue and add the request
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void handleResponse(String response) {
        Log.d("Server Response", response);

        if ("Success".equals(response.trim())) {
            showToast("Successfully saved the data");

            Intent intent = new Intent(view_patient.this, doctor_home.class);
            intent.putExtra("doctor_id", did);
            startActivity(intent);

        } else if ("failed to insert".equals(response.trim())) {
            showToast("Failed to insert data. Please try again.");
        } else if ("failed to save the image".equals(response.trim())) {
            showToast("Failed to save the image. Please try again.");
        } else if ("missing parameters".equals(response.trim())) {
            showToast("Missing parameters. Please fill in all required fields.");
        } else {
            try {
                Gson gson = new Gson();
                JsonElement jsonElement = gson.fromJson(response, JsonElement.class);

                if (jsonElement.isJsonObject()) {
                    Log.d("JSON Response", "It's a JSON Object");
                    // Extract required fields if needed
                    // Example: String status = jsonElement.getAsJsonObject().get("status").getAsString();
                    showToast("Successfully saved the data");
                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(view_patient.this, addpatient2.class);
                        startActivity(intent);
                    }, 2000);
                } else {
                    Log.d("JSON Response", "It's not a JSON Object");
                    showToast("Unable to save data. Unexpected response type.");
                }
            } catch (Exception e) {
                Log.e("JSON Response", "Error parsing JSON", e);
                showToast("Error parsing server response");
            }
        }
    }

    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            showToast("Request timed out. Check your internet connection.");
        } else {
            showToast("Error: " + error.toString().trim());

            // Log the details of the error for debugging
            Log.e("Volley Error", "Error: " + error.toString(), error.getCause());
        }
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
                        imageView.setImageBitmap(imageBitmap);
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
                                        view_patient.this.getContentResolver(),
                                        selectedImageUri
                                );
                                imageView.setImageBitmap(bitmap);
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
}
