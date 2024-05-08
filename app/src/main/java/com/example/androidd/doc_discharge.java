package com.example.androidd;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidd.ApiClient;
import com.example.androidd.FileUploadService;
import com.example.androidd.FileUtils;
import com.example.androidd.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class doc_discharge extends AppCompatActivity {
    EditText Id, Name, Gender,  Department, Consultant, Address, Date_of_Admission, Date_of_Discharge, Disease, Chief_complaints, History_of_present_illness,past_history,
            antennal_history, natal_history,postnatal_history,Gross_motor, Fine_motor, Language, Social_and_congnitiont, Immunisation_history, Anthropometry, Weightt, Heightt, Heart_rate, Temperature, crt, rr,
            spo2, Head_to_toe_examination, General_examination, Systematic_examination, Treatment_given, Course_in_Hospital, Course_in_Picu, Course_in_ward, Advice_on_discharge, Review;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button addImagesButton;
    private Button saveButton;
    private LinearLayout imageGallery;
    private List<String> imagePaths = new ArrayList<>();
    private FileUploadService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_discharge);

        service = ApiClient.getClient().create(FileUploadService.class);

        imageGallery = findViewById(R.id.imageGallery);
        addImagesButton = findViewById(R.id.addimages);
        saveButton = findViewById(R.id.save);
        Id = findViewById(R.id.Id);
        Name = findViewById(R.id.Name);
        Gender = findViewById(R.id.Gender);
        Department = findViewById(R.id.Department);
        Consultant = findViewById(R.id.Consultant);
        Address = findViewById(R.id.Address);
        Date_of_Admission = findViewById(R.id.Date_of_Admission);
        Date_of_Discharge = findViewById(R.id.Date_of_Discharge);
        Disease = findViewById(R.id.disease);
        Chief_complaints = findViewById(R.id.chief_complaints);
        History_of_present_illness = findViewById(R.id.History_of_present_illness);
        past_history=findViewById(R.id.past_history);
        antennal_history = findViewById(R.id.antennal_history);
        natal_history=findViewById(R.id.natal_history);
        postnatal_history=findViewById(R.id.postnatal_history);
        Gross_motor = findViewById(R.id.gross_motor);
        Fine_motor = findViewById(R.id.fine_motor);
        Language = findViewById(R.id.language);
        Social_and_congnitiont = findViewById(R.id.social_and_congnitiont);
        Immunisation_history = findViewById(R.id.immunisation_history);
        Anthropometry = findViewById(R.id.anthropometry);
        Weightt = findViewById(R.id.weightt);
        Heightt = findViewById(R.id.heightt);
        Heart_rate = findViewById(R.id.heart_rate);
        Temperature = findViewById(R.id.temperature);
        crt = findViewById(R.id.crt);
        rr = findViewById(R.id.rr);
        spo2 = findViewById(R.id.spo2);
        Head_to_toe_examination = findViewById(R.id.head_to_toe_examination);
        General_examination = findViewById(R.id.general_examination);
        Systematic_examination = findViewById(R.id.systematic_examination);
        Treatment_given = findViewById(R.id.treatment_given);
        Course_in_Hospital = findViewById(R.id.course_in_hospital);
        Course_in_Picu = findViewById(R.id.course_in_picu);
        Course_in_ward = findViewById(R.id.course_in_ward);
        Advice_on_discharge = findViewById(R.id.advice_on_discharge);
        Review = findViewById(R.id.review);

        addImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImages();
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    imagePaths.add(data.getClipData().getItemAt(i).getUri().toString());
                }
            } else if (data.getData() != null) {
                imagePaths.add(data.getData().toString());
            }
            displaySelectedImages();
        }
    }

    private void displaySelectedImages() {
        imageGallery.removeAllViews();
        for (String imagePath : imagePaths) {
            // Create an ImageView
            ImageView imageView = new ImageView(this);

            // Set layout parameters for the ImageView
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    dpToPx(100),  // width in pixels
                    dpToPx(100)   // height in pixels
            );
            imageView.setLayoutParams(layoutParams);

            // Set the image to the ImageView
            imageView.setImageURI(Uri.parse(imagePath));

            // Add the ImageView to the imageGallery
            imageGallery.addView(imageView);
        }
    }


    private void uploadImages() {
        if (imagePaths.size() == 0) {
            Toast.makeText(this, "No images selected.", Toast.LENGTH_SHORT).show();
            return;
        }
        String idValue = Id.getText().toString();
        String nameValue = Name.getText().toString();
        String genderValue=Gender.getText().toString();
        String departmentValue = Department.getText().toString();
        String consultantValue = Consultant.getText().toString();
        String addressValue=Address.getText().toString();
        String dateAdmiValue=Date_of_Admission.getText().toString();
        String dateDisValue=Date_of_Discharge.getText().toString();
        String diagnosisValue=Disease.getText().toString();
        String chief_complaintsValue=Chief_complaints.getText().toString();
        String history_Of_present_illnessValue=History_of_present_illness.getText().toString();
        String past_historyValue=past_history.getText().toString();
        String a_historyValue=antennal_history.getText().toString();
        String n_historyValue=natal_history.getText().toString();
        String pn_historyValue=postnatal_history.getText().toString();
        String g_mValue=Gross_motor.getText().toString();
        String f_mValue=Fine_motor.getText().toString();
        String lanValue=Language.getText().toString();
        String socialValue=Social_and_congnitiont.getText().toString();
        String imValue=Immunisation_history.getText().toString();
        String antValue=Anthropometry.getText().toString();
        String weValue=Weightt.getText().toString();
        String heValue=Heightt.getText().toString();
        String heartValue=Heart_rate.getText().toString();
        String temValue=Temperature.getText().toString();
        String crtValue=crt.getText().toString();
        String rrValue=rr.getText().toString();
        String spValue=spo2.getText().toString();
        String headValue=Head_to_toe_examination.getText().toString();
        String genvalue=General_examination.getText().toString();
        String sysValue=Systematic_examination.getText().toString();
        String treatValue=Treatment_given.getText().toString();
        String courseValue=Course_in_Hospital.getText().toString();
        String picuValue=Course_in_Picu.getText().toString();
        String wardValue=Course_in_ward.getText().toString();
        String adValue=Advice_on_discharge.getText().toString();
        String reValue=Review.getText().toString();
        // Extract other values as needed

        // Prepare RequestBody for text values
        RequestBody idBody = RequestBody.create(MediaType.parse("text/plain"), idValue);
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), nameValue);
        RequestBody genderBody = RequestBody.create(MediaType.parse("text/plain"), genderValue);
        RequestBody departmentBody = RequestBody.create(MediaType.parse("text/plain"), departmentValue);
        RequestBody consultantBody = RequestBody.create(MediaType.parse("text/plain"), consultantValue);
        RequestBody addressBody = RequestBody.create(MediaType.parse("text/plain"), addressValue);
        RequestBody dateAdmiBody = RequestBody.create(MediaType.parse("text/plain"), dateAdmiValue);
        RequestBody dateDisBody = RequestBody.create(MediaType.parse("text/plain"), dateDisValue);
        RequestBody diagnosisBody = RequestBody.create(MediaType.parse("text/plain"), diagnosisValue);
        RequestBody chiefBody = RequestBody.create(MediaType.parse("text/plain"), chief_complaintsValue);
        RequestBody hopiBody = RequestBody.create(MediaType.parse("text/plain"),history_Of_present_illnessValue);
        RequestBody pastBody = RequestBody.create(MediaType.parse("text/plain"),past_historyValue);
        RequestBody a_historyBody = RequestBody.create(MediaType.parse("text/plain"),a_historyValue);
        RequestBody n_historyBody = RequestBody.create(MediaType.parse("text/plain"),n_historyValue);
        RequestBody pn_historyBody = RequestBody.create(MediaType.parse("text/plain"),pn_historyValue);
        RequestBody gmBody=RequestBody.create(MediaType.parse("text/plain"),g_mValue);
        RequestBody fmBody=RequestBody.create(MediaType.parse("text/plain"),f_mValue);
        RequestBody lanBody=RequestBody.create(MediaType.parse("text/plain"),lanValue);
        RequestBody socialBody=RequestBody.create(MediaType.parse("text/plain"),socialValue);
        RequestBody imBody=RequestBody.create(MediaType.parse("text/plain"),imValue);
        RequestBody antBody=RequestBody.create(MediaType.parse("text/plain"),antValue);
        RequestBody weBody=RequestBody.create(MediaType.parse("text/plain"),weValue);
        RequestBody heBody=RequestBody.create(MediaType.parse("text/plain"),heValue);
        RequestBody heartBody=RequestBody.create(MediaType.parse("text/plain"),heartValue);
        RequestBody tempBody=RequestBody.create(MediaType.parse("text/plain"),temValue);
        RequestBody crtBody=RequestBody.create(MediaType.parse("text/plain"),crtValue);
        RequestBody rrBody=RequestBody.create(MediaType.parse("text/plain"),rrValue);
        RequestBody spBody=RequestBody.create(MediaType.parse("text/plain"),spValue);
        RequestBody headBody=RequestBody.create(MediaType.parse("text/plain"),headValue);
        RequestBody genBody=RequestBody.create(MediaType.parse("text/plain"),genvalue);
        RequestBody sysBody=RequestBody.create(MediaType.parse("text/plain"),sysValue);
        RequestBody treatBody=RequestBody.create(MediaType.parse("text/plain"),treatValue);
        RequestBody courseBody=RequestBody.create(MediaType.parse("text/plain"),courseValue);
        RequestBody picuBody=RequestBody.create(MediaType.parse("text/plain"),picuValue);
        RequestBody wardBody=RequestBody.create(MediaType.parse("text/plain"),wardValue);
        RequestBody adBody=RequestBody.create(MediaType.parse("text/plain"),adValue);
        RequestBody reBody=RequestBody.create(MediaType.parse("text/plain"),reValue);

        // Create more RequestBody objects for other fields
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (String imagePath : imagePaths) {
            File file = new File(FileUtils.getPath(this, Uri.parse(imagePath)));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/for m-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("images[]", file.getName(), requestFile);
            parts.add(body);
        }
        String fullUrl = Api.api + "upload.php/";
        Call<ResponseBody> call = service.uploadImages(fullUrl,parts, idBody, nameBody,genderBody,departmentBody,consultantBody,addressBody,dateAdmiBody,dateDisBody,diagnosisBody,chiefBody,hopiBody,
                pastBody,a_historyBody,n_historyBody,pn_historyBody,gmBody,fmBody,lanBody,socialBody,imBody,antBody,weBody,heBody,heartBody,tempBody,crtBody,rrBody,spBody,headBody,
                genBody,sysBody,treatBody,courseBody,picuBody,wardBody,adBody,reBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(doc_discharge.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("patient_id", idValue);
                    // Set the result and finish the current activity
                    setResult(doc_discharge.RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(doc_discharge.this, " Failed to insert Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(doc_discharge.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
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
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
