package com.divistant.net;

import com.divistant.util.GeneralResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;


public interface UploadInterface {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET("upload/image")
    Call<GeneralResponse<String>> uploadImage(@Header("Authorization") String token, @Body RequestBody guruId);

}
