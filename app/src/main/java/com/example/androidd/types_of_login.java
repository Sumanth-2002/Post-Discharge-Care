package com.example.androidd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class types_of_login extends AppCompatActivity {
    private Button btn, btn1;

    String device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types_of_login);
        Intent intent = getIntent();
        device = intent.getStringExtra("device");
        btn = findViewById(R.id.patient);
        btn1 = findViewById(R.id.doctor);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(types_of_login.this, doctor_login.class);
                intent.putExtra("device",device);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(types_of_login.this, patient_login.class);
                intent.putExtra("device",device);
                startActivity(intent);
            }
        });

    }
}