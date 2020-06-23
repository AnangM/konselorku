package com.divistant.konselorku.ui.lapor;

import com.divistant.util.GeneralResponse;
import com.divistant.util.ImageModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LaporInterface {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("lapor/add")
    Call<GeneralResponse<LaporModel>> laporkan(@Header("Authorization") String token, @Body RequestBody guruId);
}
