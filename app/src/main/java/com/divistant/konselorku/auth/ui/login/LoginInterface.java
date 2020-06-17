package com.divistant.konselorku.auth.ui.login;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface LoginInterface {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("user/login")
    Call<UserModel> doLogin(@Body RequestBody body);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET("user/login")
    Call<LogoutModel> doLogout(@Header("Authentication") String token);
}
