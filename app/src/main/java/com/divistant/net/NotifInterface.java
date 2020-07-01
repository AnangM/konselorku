package com.divistant.net;

import com.divistant.util.GeneralResponse;
import com.divistant.util.GeneralResponseModel;
import com.divistant.util.ImageModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotifInterface {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("subscribe")
    Call<GeneralResponse<GeneralResponseModel>> subscribe(@Header("Authorization") String token, @Body RequestBody body);
}
