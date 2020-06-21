package com.divistant.net;
import com.divistant.konselorku.ui.guru.GuruModel;
import com.divistant.util.GeneralResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface GuruInterface {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET("guru")
    Call<GeneralResponse<GuruModel>> getGuru(@Header("Authorization") String token);

}
