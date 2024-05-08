package com.example.androidd;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class patient_home extends AppCompatActivity {
    public static final String CHANNEL_ID = "my_channel_id";
    int notificationId = NotificationHelper.generateNotificationId();

    HorizontalScrollView horizontalScrollViewLayout;
    Button btn1;
    String id;
    Button btn2;
    Button btn3;
    Toolbar toolbar;
    TextView top;
    CircleImageView img;
    private boolean doubleBackToExitPressedOnce = false;
    CircleImageView destinationLanguageChooseBtn;
    private ArrayList<ModelLanguage> languageArrayList;
    private String sourceLanguageCode = "en";
    private String sourceLanguageTitle = "English";
    private String destinationLanguageCode = "en";
    private String destinationLanguageTitle = "English";
    LinearLayout parentLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        top = findViewById(R.id.titletext);
        btn1 = findViewById(R.id.follow);
        btn2 = findViewById(R.id.dis);
        btn3 = findViewById(R.id.med);
        parentLayout = findViewById(R.id.parentLinearLayout);
        horizontalScrollViewLayout = findViewById(R.id.horizontalScrollViewLayout);
        Intent intent = getIntent();
        id = intent.getStringExtra("patient_id");

        img = findViewById(R.id.pat_pro);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(patient_home.this, patient_profile.class);
                intent.putExtra("patient_id", id);
                intent.putExtra("des", destinationLanguageCode);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });
        fetchStringFromPHP();
        fetchReportTimeAndDate();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(patient_home.this, datepicker.class);

                intent.putExtra("des", destinationLanguageCode);
                intent.putExtra("patient_id", id);
                startActivity(intent);
            }
        });
        loadAvailableLanguages();

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(patient_home.this, pdis.class);
                intent.putExtra("patient_id", id);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(patient_home.this, patient_med.class);
                intent.putExtra("patient_id", id);

                intent.putExtra("des", destinationLanguageCode);
                startActivity(intent);
            }
        });
        destinationLanguageCode = intent.getStringExtra("des");
        if (destinationLanguageCode != null) {
            sourceLanguageCode = "en";
            translateUIElements();
        }

        destinationLanguageChooseBtn = findViewById(R.id.destinationLanguageChoose);
        try {
            if (destinationLanguageChooseBtn != null) {
                destinationLanguageChooseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        destinationLanguageChoose();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void translateUIElements() {
        LinearLayout parentLayout = findViewById(R.id.parentLinearLayout);
        if (parentLayout != null) {
            for (int j = 0; j < parentLayout.getChildCount(); j++) {
                View childView = parentLayout.getChildAt(j);
                if (childView instanceof CardView) {
                    CardView cardView = (CardView) childView;
                    for (int k = 0; k < cardView.getChildCount(); k++) {
                        View cardChildView = cardView.getChildAt(k);
                        if (cardChildView instanceof TextView) {
                            TextView timeTextView = (TextView) cardChildView;
                            translateTextView(timeTextView);
                        }
                    }
                }
            }
        } else {
            Log.e("Error", "Parent LinearLayout not found");
        }

        // Translate other UI elements
        translateButton(btn1);
        translateButton(btn2);
        translateButton(btn3);
        translateTextView(top);
        sourceLanguageCode = destinationLanguageCode;
    }

    private void loadAvailableLanguages() {
        languageArrayList = new ArrayList<>();

        // Indian languages ISO 639-1 language codes
        String[] indianLanguages = {"hi", "bn", "te", "mr", "ta", "ur", "gu", "kn", "ml", "pa", "as", "or", "ks", "ne", "sd"};

        for (String languageCode : indianLanguages) {
            String languageTitle = new Locale(languageCode).getDisplayLanguage();
            ModelLanguage modelLanguage = new ModelLanguage(languageCode, languageTitle);
            languageArrayList.add(modelLanguage);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void showNotification(String message) {
        // Create a notification channel (required for Android 8.0 and above)
        createNotificationChannel();

        // Create a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Reminder")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Display the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(0, builder.build());
    }

    private void destinationLanguageChoose() {
        PopupMenu popupMenu = new PopupMenu(this, destinationLanguageChooseBtn);
        for (int i = 0; i < languageArrayList.size(); i++) {
            popupMenu.getMenu().add(Menu.NONE, i, i, languageArrayList.get(i).languageTitle);
        }
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int position = item.getItemId();
                destinationLanguageCode = languageArrayList.get(position).languageCode;
                destinationLanguageTitle = languageArrayList.get(position).languageTitle;
                translateButton(btn1);
                translateButton(btn2);
                translateButton(btn3);
                translateTextView(top);


                for (int j = 0; j < parentLayout.getChildCount(); j++) {
                    View childView = parentLayout.getChildAt(j);
                    if (childView instanceof CardView) {
                        CardView cardView = (CardView) childView;
                        for (int k = 0; k < cardView.getChildCount(); k++) {
                            View cardChildView = cardView.getChildAt(k);
                            if (cardChildView instanceof TextView) {
                                TextView timeTextView = (TextView) cardChildView;
                                Log.e("ad", "timeTextView" + timeTextView.getText().toString());
                                translateTextView(timeTextView);
                            }
                        }
                    }
                }
                sourceLanguageCode = destinationLanguageCode;
                sourceLanguageTitle = destinationLanguageTitle;
                return true;
            }
        });
    }

    private void fetchReportTimeAndDate() {
        String url = Api.api + "get_reportTime.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    // Process each item in the array
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String reportTime = jsonObject.getString("report_time");
                        String reportDate = jsonObject.getString("report_date");

                        long reportDateTimeMillis = DateTimeUtils.convertStringToMillis(reportDate);
                        long timeDifferenceMillis = reportDateTimeMillis - System.currentTimeMillis();
                        long daysDifference = timeDifferenceMillis / (24 * 60 * 60 * 1000);
                        Log.e("s","sdf"+daysDifference);
                        // If the report date is less than or equal to 3 days from today
                        if (daysDifference <= 3 && daysDifference>=0) {
                            showNotification(Integer.toString((int)daysDifference)+"left for the report date");
                        }
                        createAndAddCardView(reportTime, reportDate);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                // Add other parameters if needed
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void translateButton(final Button button) {
        // Replace "Report Time" with your actual report time text
        String reportTimeText = button.getText().toString();
        // Start translation
        languageTranslation le = new languageTranslation(this, sourceLanguageCode, destinationLanguageCode);
        le.startTranslations(reportTimeText, button);
    }

    private void createAndAddCardView(String reportTime, String reportDate) {
        // Fetch the LinearLayout from XML layout
        LinearLayout parentLayout = findViewById(R.id.parentLinearLayout);

        // Create a CardView and add it to the parent LinearLayout
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(16, 16, 16, 16);
        cardView.setLayoutParams(params);

        // Set CardView attributes
        cardView.setCardBackgroundColor(getResources().getColor(R.color.cardBackgroundColor));
        cardView.setRadius(getResources().getDimension(R.dimen.cardCornerRadius));
        cardView.setElevation(getResources().getDimension(R.dimen.cardElevation));
        cardView.setContentPadding(16, 16, 16, 16);

        // Create TextView for report time
        TextView timeTextView = new TextView(this);
        timeTextView.setText("Report Date " + reportTime + " :  " + reportDate);
        timeTextView.setTextColor(getResources().getColor(R.color.cardTextViewColor));
        timeTextView.setTextSize(getResources().getDimension(R.dimen.cardTextSize));
        timeTextView.setTypeface(null, Typeface.BOLD);

        // Add TextViews to the CardView
        cardView.addView(timeTextView);

        // Add the CardView to the parent LinearLayout
        parentLayout.addView(cardView);
    }

    private void translateTextView(final TextView textView) {
        // Replace "Report Time" with your actual report time text
        String reportTimeText = textView.getText().toString();
        // Start translation
        languageTranslation le = new languageTranslation(this, sourceLanguageCode, destinationLanguageCode);
        le.startTranslations(reportTimeText, textView);
    }

    private void fetchStringFromPHP() {
        String url = Api.api + "patient_home.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", id);
            // Add any additional parameters as needed
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            String s = response.getString("Name"); // Replace "your_key" with the actual key in your JSON response
                            top.setText("Hello " + s.substring(0, 1).toUpperCase() + s.substring(1));
                            String a = response.getString("Profile_Pic");
                            Picasso.get().load(Api.api + a).into(img);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity(); // Close the entire application
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000); // Delay for 2 seconds
    }

}

