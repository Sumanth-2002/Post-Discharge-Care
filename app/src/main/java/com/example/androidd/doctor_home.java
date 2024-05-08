package com.example.androidd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class doctor_home extends AppCompatActivity {
    CircleImageView img;
    String did;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    Button btn, bt;

    TextView textView;
    private Timer timer;
    private final int DELAY = 3000;

    private boolean doubleBackToExitPressedOnce = false;

    private List<PatientInfo> dataList;
    private CustomAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Call your reload method here
                reloadPage();
            }
        });

        Intent intent = getIntent();
        did = intent.getStringExtra("doctor_id");
        textView = findViewById(R.id.doctortitile);
        img = findViewById(R.id.doc_pro);
        dataList = new ArrayList<>();
        adapter = new CustomAdapter(dataList);
        recyclerView.setAdapter(adapter);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doctor_home.this, doctor_profile.class);
                intent.putExtra("doctor_id", did);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        btn = findViewById(R.id.follow);
        bt = findViewById(R.id.view);
        new FetchPatientDetailsTask().execute();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doctor_home.this, search_pid.class);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(doctor_home.this, add_patient.class);
                startActivity(intent);
            }
        });
        startAutoScroll();

        // Pause auto-scrolling when user touches the RecyclerView
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, android.view.MotionEvent event) {
                switch (event.getAction()) {
                    case android.view.MotionEvent.ACTION_DOWN:
                        // Stop auto-scrolling when RecyclerView is touched
                        stopAutoScroll();
                        break;
                    case android.view.MotionEvent.ACTION_UP:
                    case android.view.MotionEvent.ACTION_CANCEL:
                        // Resume auto-scrolling when touch is released
                        startAutoScroll();
                        break;
                }
                return false;
            }
        });
        fetchStringFromPHP();
    }



    private void reloadPage() {
        // Restart the activity
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }



    private void startAutoScroll() {
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // Scroll to the next position
                    int nextPos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() + 1;
                    if (nextPos < adapter.getItemCount()) {
                        recyclerView.smoothScrollToPosition(nextPos);
                    } else {
                        recyclerView.smoothScrollToPosition(0); // Start from the beginning
                    }
                }
            }, DELAY, DELAY);
        }
    }

    private void stopAutoScroll() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void fetchStringFromPHP() {
        String url = Api.api + "doctor_home.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", did);
            // Add any additional parameters as needed
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String s = response.getString("Name");
                            String a = response.getString("Profile");
                            textView.setText("Hello Dr." + s.substring(0, 1).toUpperCase() + s.substring(1));

                            Picasso.get().load(Api.api + a).into(img);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            textView.setText("Error parsing JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("Error: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }


    private class FetchPatientDetailsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Show a progress indicator if needed
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(Api.api + "get_patientArrivingToday.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line).append("\n");
                }

                bufferedReader.close();
                inputStream.close();
                urlConnection.disconnect();

                return response.toString();
                // Your existing code for fetching patient details
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            handlePatientDetailsResponse(result);
            // Hide the progress indicator if needed
        }


        private void handlePatientDetailsResponse(String response) {
//            Log.e("jo", "response" + response);
            if (response != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.optString("status", "");

                    if ("Success".equals(status)) {
                        JSONArray patientDataArray = jsonResponse.optJSONArray("data");

                        // Null check for patientDetailsLayout
                        if (patientDataArray == null || patientDataArray.length() == 0) {
                            // If there is no data, display the message in TextView
                            showNoPatientMessage();
                            return;
                        }

                        // Assuming you have a LinearLayout with ID 'patientDetailsLayout'


                        for (int i = 0; i < patientDataArray.length(); i++) {
                            JSONObject patientData = patientDataArray.optJSONObject(i);

                            // Null check for patientData
                            if (patientData == null) {
                                continue;
                            }

                            PatientInfo patientInfo = new PatientInfo(
                                    patientData.optString("id", ""),
                                    patientData.optString("Name", ""),
                                    patientData.optString("Gender", ""),
                                    patientData.optString("Contact_No", ""),
                                    patientData.optString("Profile_Pic", "")
                            );
                            dataList.add(patientInfo);
                        }

                        // Notify the adapter that the data set has changed
                        adapter.notifyDataSetChanged();
                    } else if ("NoData".equals(status)) {
                        showNoPatientMessage();
                        Toast.makeText(doctor_home.this, jsonResponse.optString("message", ""), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(doctor_home.this, "Error fetching patient details", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(doctor_home.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(doctor_home.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showNoPatientMessage() {
        // Display TextView with the message that no patient is arriving today
        TextView arr = findViewById(R.id.pat);
        TextView noPatientTextView = findViewById(R.id.noPatientTextView);
        LinearLayout lin = findViewById(R.id.showScreen);
        arr.setVisibility(View.GONE);
        noPatientTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        lin.setVisibility(View.VISIBLE);

    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private List<PatientInfo> dataList;

        public CustomAdapter(List<PatientInfo> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PatientInfo patient = dataList.get(position);
            // Your existing code for binding data to ViewHolder

            if (holder.idTextView != null) {
                holder.idTextView.setText("ID: " + (patient.getId() != null ? patient.getId() : ""));
            }
            if (holder.nameTextView != null) {
                holder.nameTextView.setText("Name: " + (patient.getName() != null ? patient.getName() : ""));
            }
            if (holder.genderTextView != null) {
                holder.genderTextView.setText("Gender: " + (patient.getGender() != null ? patient.getGender() : ""));
            }
            if (holder.phnoTextView != null) {
                holder.phnoTextView.setText("Contact no: " + (patient.getPhno() != null ? patient.getPhno() : ""));
            }
            if (holder.profileImageView != null && patient.getProfilePhoto() != null
                    && !patient.getProfilePhoto().isEmpty()) {
                String completeImageUrl = Api.api + patient.getProfilePhoto();
                Picasso.get().load(completeImageUrl).into(holder.profileImageView);
            } else {
                holder.profileImageView.setImageResource(R.drawable.person);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedItem = patient.getId() != null ? patient.getId() : "";
                    Intent intent = new Intent(v.getContext(), view_patient.class);
                    intent.putExtra("patient_id", selectedItem);
                    intent.putExtra("doctor_id", did);
                    v.getContext().startActivity(intent);
                }
            });

        }
        // No changes made here


        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView idTextView, nameTextView, genderTextView, phnoTextView;
            CircleImageView profileImageView;

            public ViewHolder(View itemView) {
                super(itemView);
                idTextView = itemView.findViewById(R.id.id);
                nameTextView = itemView.findViewById(R.id.name);
                genderTextView = itemView.findViewById(R.id.gender);
                phnoTextView = itemView.findViewById(R.id.phno);
                profileImageView = itemView.findViewById(R.id.profile);
            }
        }
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


class PatientInfo2 {
    private String id;
    private String name;
    private String gender;
    private String phno;
    private String profilePhoto;

    public PatientInfo2(String id, String name, String gender, String phno, String profilePhoto) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phno = phno;
        this.profilePhoto = profilePhoto;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPhno() {
        return phno;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }
}
