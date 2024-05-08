package com.example.androidd;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface FileUploadService {


    @Multipart
    @POST
    Call<ResponseBody> uploadImages(@Url String url,
            @Part List<MultipartBody.Part> images,
                                    @Part("id") RequestBody Id,
                                    @Part("name") RequestBody name,
                                    @Part("Gender") RequestBody gender,
                                    @Part("Department") RequestBody department,
                                    @Part("Consultant") RequestBody consultant,
                                    @Part("Address") RequestBody address,
                                    @Part("Date_Of_Admission") RequestBody doa,
                                    @Part("Date_Of_Discharge") RequestBody dod,
                                    @Part("Diagnosis") RequestBody diagnosis,
                                    @Part("Chief_Complaints") RequestBody chief,
                                    @Part("History_of_present_illness") RequestBody hopi,
                                    @Part("Past_History") RequestBody past,
                                    @Part("Antennal_history") RequestBody antennal,
                                    @Part("Natal_History") RequestBody natal,
                                    @Part("PostNatal_History") RequestBody postNatal,
                                    @Part("Gross_Motor") RequestBody gm,
                                    @Part("Fine_Motor") RequestBody fm,
                                    @Part("Language") RequestBody language,
                                    @Part("Social_and_Congnitiont") RequestBody sac,
                                    @Part("Immunization_history") RequestBody ih,
                                    @Part("Anthropometry") RequestBody antro,
                                    @Part("Weight") RequestBody weight,
                                    @Part("Height") RequestBody height,
                                    @Part("Heart_rate") RequestBody heartrate,
                                    @Part("Temperature") RequestBody temperature,
                                    @Part("crt") RequestBody crt,
                                    @Part("rr") RequestBody rr,
                                    @Part("spo2") RequestBody spo2,
                                    @Part("Head_to_Toe_Examination") RequestBody headToToeExamination,
                                    @Part("General_Examination") RequestBody GeneralExamination,
                                    @Part("Systematic_Examination") RequestBody SystematicExamination,
                                    @Part("Treatment_Given") RequestBody TreatmentGiven,
                                    @Part("Course_in_Hospital") RequestBody courseInHospital,
                                    @Part("Course_in_Picu") RequestBody courseInPicu,
                                    @Part("Course_in_ward") RequestBody courseInWard,
                                    @Part("Advice_on_Discharge") RequestBody adviceOnDischarge,
                                    @Part("Review") RequestBody review
    );
}
