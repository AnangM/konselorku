package com.divistant.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.divistant.konselorku.MainActivity;
import com.divistant.konselorku.MyApp;
import com.divistant.net.API;
import com.divistant.net.NotifInterface;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseService extends FirebaseMessagingService {
    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext().getApplicationContext());
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        subscribe(s);
    }

    public void subscribe(String token){
        Map<String, Object> jsonParam = new ArrayMap<>();
        jsonParam.put("token",token);

        RequestBody body = RequestBody.create(okhttp3.MediaType
                        .parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParam))
                        .toString());

        NotifInterface service = API.getClient().create(NotifInterface.class);
        Call<GeneralResponse<GeneralResponseModel>> call = service.subscribe(pref.getString("TOKEN","def"),body);
        call.enqueue(new Callback<GeneralResponse<GeneralResponseModel>>() {
            @Override
            public void onResponse(Call<GeneralResponse<GeneralResponseModel>> call, Response<GeneralResponse<GeneralResponseModel>> response) {
                if(response.code()==201){
                    Toast.makeText(getApplicationContext(), "SUBSCRIBED",Toast.LENGTH_LONG).show();
                }else{
                    GeneralResponse resp = response.body();
                    if(resp != null){
                        Toast.makeText(getApplicationContext(), resp.getMessage(),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), response.code() + "",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse<GeneralResponseModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage() + "",Toast.LENGTH_LONG).show();
                Log.e("MAIN",t.getMessage());
            }
        });
    }
}
