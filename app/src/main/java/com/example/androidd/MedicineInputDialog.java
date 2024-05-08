package com.example.androidd;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MedicineInputDialog extends Dialog {

    private Context context;

    public MedicineInputDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_medicine_input);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setTextAppearance(context, R.style.DialogTitleStyle);
        final EditText courseNameEditText = findViewById(R.id.courseNameEditText);
        final EditText medicineNameEditText = findViewById(R.id.medicineNameEditText);
        final EditText courseDurationEditText = findViewById(R.id.courseDurationEditText);
        final EditText frequencyEditText = findViewById(R.id.frequencyEditText);
        final EditText guidelinesEditText = findViewById(R.id.guidelinesEditText);

        Button okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = courseNameEditText.getText().toString();
                String medicineName = medicineNameEditText.getText().toString();
                String courseDuration = courseDurationEditText.getText().toString();
                String frequency = frequencyEditText.getText().toString();
                String guidelines = guidelinesEditText.getText().toString();

                ((doc_medicine) context).displayMedicineInformation(
                        courseName, medicineName, courseDuration, frequency, guidelines);

                dismiss();
            }
        });
    }

}
