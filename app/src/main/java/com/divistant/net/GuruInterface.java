package com.divistant.net;
import com.divistant.konselorku.ui.guru.GuruDetailModel;
import com.divistant.konselorku.ui.guru.GuruModel;
import com.divistant.util.GeneralResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GuruInterface {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET("guru")
    Call<GeneralResponse<GuruModel>> getGuru(@Header("Authorization") String token);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET("guru/detail")
    Call<GeneralResponse<GuruDetailModel>> getGuruDetail(@Header("Authorization") String token, @Query("guru_id") String guruId);

}
