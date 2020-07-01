package com.divistant.net;

import com.divistant.konselorku.ui.lapor.LaporanModel;
import com.divistant.util.GeneralResponse;
import com.divistant.util.ImageModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LaporanInterface {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET("laporan")
    Call<GeneralResponse<LaporanModel>> get(@Header("Authorization") String token);
}
