package com.example.androidd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class addpatient2 extends Activity {
    String pid;
    ImageView addDis, addMed, addQue, addRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpatient2);

        Intent intent = getIntent();
        pid = intent.getStringExtra("patient_id");
        addDis = findViewById(R.id.adddis);
        addMed = findViewById(R.id.addmed);
        addQue = findViewById(R.id.addque);
        addRep = findViewById(R.id.addrep);

        addDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(doc_discharge.class);
            }
        });

        addMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(doc_medicine.class);
            }
        });

        addQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(doctor_questionnaire.class);
            }
        });

        addRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(doc_reportTime.class);
            }
        });

        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display the appropriate message based on the count

                // If count is 4, show "Saved successfully" message and navigate to the new activity
                displayPopupMessage("Saved successfully");

                // Delay for 2 seconds and then navigate to the new activity
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        navigateToNewActivity();
                    }
                }, 2000); // 2000 milliseconds = 2 seconds
            }
        });
    }
    private void displayPopupMessage(String message) {
        // Inflate the popup_message.xml layout
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_message, null);

        // Create a PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);

        // Set the message text in the popup layout
        showToast(message);
    }

    private void navigateToNewActivity() {
        Intent intent = new Intent(this, doctor_home.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void handleImageViewClick(Class<?> destinationClass) {
        Intent intent = new Intent(addpatient2.this, destinationClass);
        intent.putExtra("patient_id", pid);
        startActivity(intent);
    }
}
