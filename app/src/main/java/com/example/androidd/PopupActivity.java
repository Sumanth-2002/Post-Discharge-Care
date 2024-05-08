package com.example.androidd;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class PopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        // Retrieve the days difference from the intent
        int daysDifference = getIntent().getIntExtra("daysDifference", 0);

        // Display the days difference in a TextView or any other UI element
       TextView textView = findViewById(R.id.textView);
        textView.setText("You have " + daysDifference + " days left for reporting to the hospital");

        // Add any additional logic or UI elements as needed
    }
}
