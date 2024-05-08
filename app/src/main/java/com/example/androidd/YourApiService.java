package com.example.androidd;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface YourApiService {

    @FormUrlEncoded
    @POST("add_report.php") // Replace with your actual endpoint
    Call<JsonObject> sendData(
            @Field("id") int id,
            @Field("dates[]") String[] dates,
            @Field("counts[]") String[] counts
    );
}
