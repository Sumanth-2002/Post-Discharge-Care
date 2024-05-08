package com.example.androidd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class view_images extends AppCompatActivity {
    String PHP_ENDPOINT_URL = Api.api+"view_images.php";
    String SAMPLE_ID;
    LinearLayout linearLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_images);
        Intent intent=getIntent();
        SAMPLE_ID=intent.getStringExtra("patient_id");
        String urlWithId = PHP_ENDPOINT_URL + "?id=" + SAMPLE_ID;
        new LoadImagesTask().execute(urlWithId);
    }

    private class LoadImagesTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            String urlWithId = params[0];
            try {
                URL url = new URL(urlWithId);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();

                return new JSONArray(stringBuilder.toString());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONArray imagePaths) {
            if (imagePaths != null) {
                displayImages(imagePaths);
            }
        }
    }

    private void displayImages(JSONArray imagePaths) {
        Log.e("view_images", "displayImages: "+imagePaths.toString());

        LinearLayout linearLayout = findViewById(R.id.linearlayout); // Assuming your layout is a LinearLayout
        linearLayout.removeAllViews(); // Clear any existing views

        for (int i = 0; i < imagePaths.length(); i++) {
            try {
                JSONObject imagePathObject = imagePaths.getJSONObject(i);
                String imagePath = Api.api + imagePathObject.getString("image");

                // Create a new ImageView
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setAdjustViewBounds(true);

                // Use Picasso or Glide to load the image into the ImageView
                Picasso.get().load(imagePath).into(imageView);

                // Add the ImageView to the layout
                linearLayout.addView(imageView);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
