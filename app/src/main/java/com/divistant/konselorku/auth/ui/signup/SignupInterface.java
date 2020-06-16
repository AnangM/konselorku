package com.divistant.konselorku.auth.ui.signup;

import com.divistant.konselorku.auth.ui.login.UserModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SignupInterface {
    @Headers({
            "Content-Type: application/json"
    })
    @POST("user/finish/profile")
    Call<FinishProfileModel> finihsProfile(@Body RequestBody body);
}
