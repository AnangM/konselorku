package com.divistant.net;

import com.divistant.konselorku.ui.profil.ProfilModel;
import com.divistant.util.GeneralResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface UserInterface {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET("user/me")
    Call<GeneralResponse<ProfilModel>> get(@Header("Authorization") String token);
}
