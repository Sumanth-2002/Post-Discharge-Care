package com.example.androidd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class add_patient extends AppCompatActivity {
    String URL = Api.api + "insert_patient.php/";
    private CircleImageView imageButton;
    EditText idEditText, nameEditText, contactNoEditText, dobEditText, heightEditText, weightEditText, parentNameEditText, admittedOnEditText, dischargedOnEditText;
    Spinner genderEditText;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Set onTouchListener to the entire layout or the root view

        setContentView(R.layout.activity_add_patient);
        idEditText = findViewById(R.id.Id);
        nameEditText = findViewById(R.id.Name);
        contactNoEditText = findViewById(R.id.Contact_No);

        dobEditText = findViewById(R.id.dob);
        heightEditText = findViewById(R.id.Height);
        weightEditText = findViewById(R.id.Weight);
        parentNameEditText = findViewById(R.id.Parent_Name);
        admittedOnEditText = findViewById(R.id.Admitted_On);
        dischargedOnEditText = findViewById(R.id.Discharge_On);
        imageButton = findViewById(R.id.Profile_Pic);
        genderEditText = findViewById(R.id.Gender);
        genderEditText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                     @Override
                                                     public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                         // Get the selected item
                                                         gender = parentView.getItemAtPosition(position).toString();

                                                         // You can use the selectedGender as needed
                                                         // For example, you can log it or update other UI elements
                                                     }

                                                     @Override
                                                     public void onNothingSelected(AdapterView<?> parent) {

                                                     }
                                                 });
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showImageDialog();
                    }
                });

        Button button = findViewById(R.id.addpa);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    String pid = idEditText.getText().toString();
                    String name = nameEditText.getText().toString();

                    String contactNo = contactNoEditText.getText().toString();
                    String dateOfBirth = dobEditText.getText().toString();
                    String height = heightEditText.getText().toString();
                    String weight = weightEditText.getText().toString();
                    String parentName = parentNameEditText.getText().toString();
                    String admittedOn = admittedOnEditText.getText().toString();
                    String dischargedOn = dischargedOnEditText.getText().toString();

                    // Convert the Bitmap to Base64
                    Bitmap imageBitmap = ((BitmapDrawable) imageButton.getDrawable()).getBitmap();
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

                    // Send the data to the server
                    sendDataToServer(data);
                }
            }
        });
        findViewById(android.R.id.content).setOnTouchListener((v, event) -> {
            hideSoftKeyboard(v);
            return false;
        });
        setEditTextOnTouchListener(idEditText);
        setEditTextOnTouchListener(nameEditText);
        setEditTextOnTouchListener(contactNoEditText);

        setEditTextOnTouchListener(dobEditText);
        setEditTextOnTouchListener(heightEditText);
        setEditTextOnTouchListener(weightEditText);
        setEditTextOnTouchListener(parentNameEditText);
        setEditTextOnTouchListener(admittedOnEditText);
        setEditTextOnTouchListener(dischargedOnEditText);
    }

    private void setEditTextOnTouchListener(EditText editText) {
        editText.setOnTouchListener((v, event) -> {
            v.requestFocus();
            return false;
        });
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

    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
        Log.e("JO", "response" + response);
        Toast.makeText(add_patient.this, "sf" + response, Toast.LENGTH_SHORT).show();
        if ("Success".equals(response.trim())) {
            Toast.makeText(add_patient.this, "Successfully saved", Toast.LENGTH_SHORT).show();

            String pid = idEditText.getText().toString();
            Intent intent = new Intent(add_patient.this, addpatient2.class);
            intent.putExtra("patient_id", pid);
            startActivity(intent);
            finish();
        } else if ("failed to insert".equals(response.trim())) {
            Toast.makeText(add_patient.this, "Failed to insert", Toast.LENGTH_SHORT).show();
        } else if ("failed to save the image".equals(response.trim())) {
            Toast.makeText(add_patient.this, "Failed to save image", Toast.LENGTH_SHORT).show();
        } else if ("missing parameters".equals(response.trim())) {
            Toast.makeText(add_patient.this, "Missing parameters", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Gson gson = new Gson();
                JsonElement jsonElement = gson.fromJson(response, JsonElement.class);

                if (jsonElement.isJsonObject()) {

                    Toast.makeText(add_patient.this, "Successfully saved data", Toast.LENGTH_SHORT).show();
                    String pid = idEditText.getText().toString();
                    Intent intent = new Intent(add_patient.this, addpatient2.class);
                    intent.putExtra("patient_id", pid);
                    startActivity(intent);
                    finish();


                } else {
                    Log.d("JSON Response", "It's not a JSON Object");
                    Toast.makeText(add_patient.this, "Unable to save data", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("JSON Response", "Error parsing JSON", e);
                Toast.makeText(add_patient.this, "Error parsing Json.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(add_patient.this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(add_patient.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            // Log the details of the error for debugging
            Log.e("Volley Error", "Error: " + error.toString(), error.getCause());
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

    private boolean validateFields() {
        boolean isValid = true;

        String id = idEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String contactNo = contactNoEditText.getText().toString();

        // Validate mobile number for India
        if (!contactNo.matches("^[6-9]\\d{9}$")) {
            contactNoEditText.setError("Invalid mobile number");
            isValid = false;
        }

        // Check if other fields are empty
        if (id.isEmpty() || name.isEmpty() || contactNo.isEmpty()) {
            Toast.makeText(add_patient.this, "All fields are required", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
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
                        imageButton.setImageBitmap(imageBitmap);
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
                                        add_patient.this.getContentResolver(),
                                        selectedImageUri
                                );
                                imageButton.setImageBitmap(bitmap);
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
        Log.e("ad", "base64" + byteArray.toString());
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
