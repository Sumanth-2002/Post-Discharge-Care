package com.example.androidd;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Medicine {
    @POST("insert_medicine.php")
    Call<JsonElement> saveMedicine(@Body JsonObject jsonObject);
}
