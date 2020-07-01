package com.divistant.util;

import android.util.Log;

import com.divistant.konselorku.ui.profil.ProfilModel;
import com.divistant.net.API;
import com.divistant.net.UserInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetProfile {
    private String token;
    private GetProfileResponse resp;


    public interface GetProfileResponse{
        void onSuccess(ProfilModel model);
        void onFailed(String message);
    }

    public GetProfile(String token, GetProfileResponse resp) {
        this.token = token;
        this.resp = resp;
    }

    public void get(){
        UserInterface service = API.getClient().create(UserInterface.class);
        Call<GeneralResponse<ProfilModel>> call = service.get(token);
        call.enqueue(new Callback<GeneralResponse<ProfilModel>>() {
            @Override
            public void onResponse(Call<GeneralResponse<ProfilModel>> call, Response<GeneralResponse<ProfilModel>> response) {
                GeneralResponse<ProfilModel> general = response.body();
                if(response.code() == 200){
                    ProfilModel mprofil = general.getData().get(0);
                    resp.onSuccess(mprofil);

                }else{
                    if(general != null){
                        Log.e("PROFIL",general.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse<ProfilModel>> call, Throwable t) {
                resp.onFailed(t.getMessage());
            }
        });
    }
}
