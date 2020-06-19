package com.divistant.konselorku.ui.chat;

import com.divistant.util.GeneralResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChatInterface {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET("chat/room")
    Call<GeneralResponse<ChatRoomModel>> getRooms(@Header("Authorization") String token);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @POST("chat/room/new")
    Call<GeneralResponse<NewChatRoomModel>> addRooms(@Header("Authorization") String token,@Body RequestBody body);
}
