package com.divistant.net;

import com.divistant.konselorku.assesment.AnswerResponse;
import com.divistant.konselorku.assesment.QuestionModel;
import com.divistant.konselorku.auth.ui.login.UserModel;
import com.divistant.konselorku.ui.lapor.LaporanModel;
import com.divistant.util.GeneralResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AssesmentInterface {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET("assesment")
    Call<GeneralResponse<QuestionModel>> get(@Header("Authorization") String token);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("assesment/answer")
    Call<GeneralResponse<UserModel>> answer(@Header("Authorization") String token, @Body RequestBody body);
}
