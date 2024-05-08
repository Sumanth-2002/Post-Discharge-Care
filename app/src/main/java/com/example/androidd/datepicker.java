package com.example.androidd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class datepicker extends AppCompatActivity {
    CalendarView calendarView;
    String id;
    private String sourceLanguageCode;
    private String destinationLanguageCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker);
        calendarView = findViewById(R.id.calendar);
        Intent intent = getIntent();
        id = intent.getStringExtra("patient_id");
        sourceLanguageCode = intent.getStringExtra("src");
        destinationLanguageCode = intent.getStringExtra("des");
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                // Format month and day as two digits
                String formattedMonth = String.format("%02d", (month + 1));
                String formattedDayOfMonth = String.format("%02d", dayOfMonth);

                String selectedDate = year + "-" + formattedMonth + "-" + formattedDayOfMonth;
                Intent followupIntent = new Intent(datepicker.this, followup.class);
                followupIntent.putExtra("patient_id", id);
                followupIntent.putExtra("selected_date", selectedDate);
                followupIntent.putExtra("src",sourceLanguageCode);
                followupIntent.putExtra("des",destinationLanguageCode);
                startActivity(followupIntent);
            }
        });
    }
}
